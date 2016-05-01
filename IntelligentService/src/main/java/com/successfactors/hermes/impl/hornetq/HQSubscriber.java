/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.successfactors.hermes.impl.hornetq;

import EventCenter.hermes.core.SFEvent;
import EventCenter.hermes.core.subscriber.SubscriberRegistry;
import com.successfactors.hermes.util.DuplicateMessageGuarantee;
import com.successfactors.hermes.util.SEBLog;
import com.successfactors.hermes.util.SEBLogUtils;
import com.successfactors.serialization.SerializationException;
import com.successfactors.serialization.json.JSONSerializationUtils;
import com.successfactors.logging.api.Logger;

import org.hornetq.api.core.HornetQException;
import org.hornetq.api.core.HornetQExceptionType;
import org.hornetq.api.core.client.ClientConsumer;
import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.api.core.client.MessageHandler;

/**
 * Implements MessageHandler i
 *
 * @author ayarmolenko
 */
public abstract class HQSubscriber implements MessageHandler {

  private static final Logger log = SEBLog.getLogger(HQSubscriber.class);
  private String address;
  private String queue;
  private String subscriberName;
  private String filter;
  private HQSession session;
  private ClientConsumer consumer;
  private boolean reAttachSession = false;

  /**
   * Constructor
   *
   * @param address address to subscribe
   * @param queue name of the queue for given subscriber
   * @param subscriberName subscriber name, resolved to subscriber instance via SubscribersRegistry
   * @param filter filter string
   */
  protected HQSubscriber(String address, String queue, String subscriberName, String filter) {
    this.address = address;
    this.queue = queue;
    this.subscriberName = subscriberName;
    this.filter = filter;
  }

  public String getQueue() {
    return queue;
  }

  /**
   * address getter
   *
   * @return address
   */
  public String getAddress() {
    return address;
  }

  /**
   * subscriberName getter
   *
   * @return subscriber Name
   */
  public String getSubscriberName() {
    return subscriberName;
  }

  /**
   * performs subscriber startup
   *
   * @throws HornetQException
   */
  public abstract void start() throws HornetQException;

  /**
   * performs subscriber shutdown
   *
   * @throws HornetQException
   */
  public abstract void stop() throws HornetQException;


  /**
   * create consumer
   * @throws HornetQException HornetQException
   */
  protected void createConsumer() throws HornetQException {
    createConsumer(false);
  }

  /**
   * creates and initializes consumer
   *
   * @throws HornetQException
   */
  protected void createConsumer(boolean recreateQueue) throws HornetQException {
    closeConsumer();
    try {
      session = HQSessionPool.getInstance().getSession(this);
      if (session == null) {
        throw new HornetQException(HornetQExceptionType.INTERNAL_ERROR, "No session available in the pool");
      }

      consumer = session.createConsumer(address, queue, filter, recreateQueue);
      if (consumer == null) {
        throw new HornetQException(HornetQExceptionType.INTERNAL_ERROR, "Failed to create consumer");
      }
    } finally {
      //to avoid session leak
      if (consumer == null) {
        closeConsumer();
      }
    }

  }

  /**
   * stops the consumer
   *
   * @throws HornetQException
   */
  protected void closeConsumer() throws HornetQException {
    log.debug("Closing consumer : " + consumer + session, new Exception("trace"));
    if (consumer != null) {
      consumer.close();
      consumer = null;
    }
    if (session != null) {
      HQSessionPool.getInstance().releaseSession(session);
      session = null;
    }
  }

  /**
   * getter for session
   *
   * @return session
   */
  protected HQSession getSession() {
    return session;
  }

  /**
   * getter for consumer
   *
   * @return consumer
   */
  protected ClientConsumer getConsumer() {
    return consumer;
  }

  @Override
  public void onMessage(ClientMessage msg) {

    String messageID = msg.getStringProperty(HQConstants.MESSAGE_ID);
    if(DuplicateMessageGuarantee.isDuplicate(messageID, subscriberName)) {
      log.warn("Duplicate message detected :" + messageID);
      acknowledgeWithDuplicateDetection(msg, messageID, true);
      return;
    }
    String message = msg.getBodyBuffer().readString();

    SFEvent evt = null;
    Exception serializationError = null;
    try {
      evt = JSONSerializationUtils.toObject(message, SFEvent.class, true);
    } catch (Exception ex) {
      log.error("Failed to deserialize object for PtoP subscriber at address " + address + ":" + ex.getMessage(), ex);
      log.error("The message which can't be descrialized : " + message);
      serializationError = ex;
    }
    
    if(evt != null) {
      SEBLogUtils.insertConversionParameters(SEBLogUtils.getFormatedParam(evt.getMeta()));
      log.info(String.format("Event [topic=%s, id=%s] received by subscriber %s", 
          evt.getMeta().getTopic(), messageID, subscriberName));
      long startTime = System.currentTimeMillis();
  
      boolean received = SubscriberRegistry
              .getInstance()
              .executeSubscriber(subscriberName, evt, serializationError, log);
  
      try {
        if (received && serializationError == null) {
          acknowledgeWithDuplicateDetection(msg, messageID, false);
          log.info(String.format("Event [topic=%s, id=%s] handled by subscriber %s, process time[%sms]", 
              evt.getMeta().getTopic(), messageID, subscriberName, System.currentTimeMillis() - startTime));
        } else if (serializationError != null) {
          log.error("Serialization error on event " + messageID, serializationError);
        } else {
          log.warn("Subscriber " + subscriberName + " failed to handle event " + messageID
                  + ", delivery count = " + msg.getDeliveryCount());
          session.rollback(); //reject message and schedule for re-delivery
        }
      } catch (HornetQException e) {
        log.error("Failed to rollback message[" + messageID + "] : " + e.getMessage(), e);
      } finally {
        SEBLogUtils.removeConversionParameters();
      }
    //Do not retry when met serialization error.
    } else {
      try {
        log.warn("Event received but failed to process.");
        msg.acknowledge();
      } catch (HornetQException e) {
        log.error("Failed to acknowledge message : " + e.getMessage(), e);
      }
    }
  }

  private void acknowledgeWithDuplicateDetection(ClientMessage msg,
      String messageID, boolean removeDuplicate) {
    try {
      msg.acknowledge();
      if(removeDuplicate) {
        DuplicateMessageGuarantee.removeDuplicate(messageID, subscriberName);
      }
    } catch (HornetQException e) {
      log.error("Failed to acknowledge message[" + messageID + "] : " + e.getMessage(), e);
      DuplicateMessageGuarantee.putDuplicateMessageToZookeeper(messageID, subscriberName);
    }
  }

  /**
   * Receive message from HornetQ
   * @param receiveTimeout receiveTimeout
   * @return client message
   * @throws HornetQException HornetQException
   */
  public ClientMessage receive(long receiveTimeout) throws HornetQException {
    if(reAttachSession) {
      log.info("Reattach session for the subscriber.");
      closeConsumer();
      reAttachSession = false;
    }
    HQSession session = getSession();
    if (session == null || session.isClosed()) {
      //session is not yet created or was already 
      //closed due to connection failure
      //reload
      createConsumer();
    }
    return receiveTimeout < 0 ? getConsumer().receive() : getConsumer().receive(receiveTimeout);
  }

  @Override
  public String toString() {
    return String.format("Subscriber[address=%s, queue=%s, filter=%s, session=%s]", 
        address, queue, filter, session);
  }
  
  public void reAttachSession() {
    reAttachSession = true;
  }
}
