/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.successfactors.hermes.impl.hornetq;

import com.successfactors.appconfig.AppConfig;
import EventCenter.hermes.util.SEBLog;

import java.util.Map;

import com.successfactors.logging.api.Logger;

import org.hornetq.api.core.HornetQException;
import org.hornetq.api.core.HornetQExceptionType;
import org.hornetq.api.core.Message;
import org.hornetq.api.core.SimpleString;
import org.hornetq.api.core.client.ClientConsumer;
import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.api.core.client.ClientProducer;
import org.hornetq.api.core.client.ClientRequestor;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.core.client.impl.ClientSessionInternal;
import org.hornetq.api.core.client.ClientSessionFactory;
import org.hornetq.api.core.management.ManagementHelper;

/**
 * Wraps ClientSession object. Provides helper method to add subscriber and
 * publish events replacement for SessionHelper class
 *
 * @author ayarmolenko
 */
public class HQSession{

  private static final Logger LOGGER = SEBLog.getLogger(HQSession.class);
  private static final String MESSAGE_EXPIRATION_PROP = "com.successfactors.hermes.message.expiration";
  private static final String MESSAGE_RATE_PROP = "com.successfactors.hermes.message.rate";
  private static final String MAX_MESSAGE_SIZE_PROP = "com.successfactors.hermes.message.max_size";
  private static final int CONSUMER_WINDOW_DEFAULT = 1048576;
  private static final int CONSUMER_RATE_DEFAULT = 1000000;

  private static final String HORNETQ_MANAGEMENT_ADDRESS = "jms.queue.hornetq.management";
  private static final String HORNETQ_DEFAULT_DLQ = "jms.queue.DLQ";
  private static final String HORNETQ_DEFAULT_EXPIRYQUEUE = "jms.queue.ExpiryQueue";

  private static final String HORNETQ_RESOURCE_SERVER = "core.server";
  private static final String HORNETQ_OPERATION_ADD_ADDRESS = "addAddressSettings";
  
  private ClientSession session;
  

  /**
   * Default constructo
   *
   * @param session to warp
   */
  protected HQSession(ClientSession session) {
    this.session = session;
  }

  /**
   * Create consumer
   * @param address address
   * @param queue queue
   * @param filterExpression filterExpression
   * @return The ClientComsumer to register a listener with,
   * @throws HornetQException HornetQException
   */
  public ClientConsumer createConsumer(String address, String queue,
      String filterExpression) throws HornetQException {
    return createConsumer(address, queue, filterExpression, false);
  }

  /**
   * Adds a subscriber and returns the ClientConsumer which has to be cached and
   * have its MessageHandler set.
   *
   * @param address The topic we are listening on.
   * @param queue Queue to subscribe to
   * @param filterExpression to filter events, See JSR-XXX
   * @param recreate recreate queue
   * @return The ClientComsumer to register a listener with,
   * @throws HornetQException If something goes wrong,
   */
  public ClientConsumer createConsumer(String address, String queue,
      String filterExpression, boolean recreate) throws HornetQException {

    //creates a queue for each subscriber class which is correct and allows for delivery to different queues based 
    //on the address
    //also handles load balancing of the same subscriber class to the same queue since only one wll get the message.

    createQueue(address, queue, filterExpression, true, recreate);
    
    //first will look for queue-specific rate settings in property named MESSAGE_RATE_PROP.<queuename>
    //if none found, check global settings in property named MESSAGE_RATE_PROP
    String strRate = AppConfig.getString(MESSAGE_RATE_PROP + "." + queue);
    if (strRate == null){
      strRate = AppConfig.getString(MESSAGE_RATE_PROP);
    }
    
    ClientConsumer consumer = null;
    int rate = -1;
    if (strRate != null) {
      rate = Integer.valueOf(strRate);
      LOGGER.info("Setting message rate " + rate + " messages per second for queue " + queue);
    }
    consumer = session.createConsumer(queue,
            filterExpression,
            CONSUMER_WINDOW_DEFAULT,
            (rate != -1) ? rate : CONSUMER_RATE_DEFAULT, false);

    LOGGER.info(
            "Created consumer for address " + address
            + ", queue = " + queue
            + ", rate = " + rate
            + ", filter = [" + filterExpression + "]");



    return consumer;
  }

  private void createQueue(String address, String queue, String filter,
      boolean durable) throws HornetQException {
    createQueue(address, queue, filter, durable, false);
  }

  private void createQueue(String address, String queue, String filter,
      boolean durable, boolean recreate) throws HornetQException {
    try {
      
      ClientSession.QueueQuery qq = session.queueQuery(new SimpleString(queue));
      if (!qq.isExists()) {
        session.createQueue(address, queue, filter, durable);
      } else if (recreate) {
        session.deleteQueue(queue);
        session.createQueue(address, queue, filter, durable);
        return;
      } else if (!address.equals(qq.getAddress().toString())) {
        throw new HornetQException("Queue " + queue
            + " is already bound to address " + qq.getAddress());
      }
    } catch (HornetQException e) {
      if (e.getType().equals(HornetQExceptionType.QUEUE_EXISTS)) {
        LOGGER.debug("Queue already registered so skipping queue creation "
            + e.getMessage());
      } else {
        throw e;
      }
    }
  }

  /**
   * Publishes the event
   *
   * @param address - address to publish event to
   * @param message - text message to publish
   * @param extraParameters - collection of parameter to be published as
   * additional event fields and used for filtering.
   * @throws HornetQException in case if something goes wrong
   */
  public void publish(String address, String message, Map<String, Object> extraParameters) throws HornetQException {

    ClientProducer producer = session.createProducer();

    Message m = session.createMessage(true);
    m.getBodyBuffer().writeString(message); 
    
    //Set priority attribute
    Integer priority = (Integer) extraParameters.get(HQConstants.MESSAGE_PRIORITY);
    if (priority != null){
      if (priority > 9 || priority < 0){
        throw new HornetQException("Event priority is out of [0...9] range");
      }
      m.setPriority(priority.byteValue());
    }
    
    //set expiration attribute
    String expiration = AppConfig.getString(MESSAGE_EXPIRATION_PROP);
    if (expiration != null) {
      try {
        m.setExpiration(System.currentTimeMillis() + Long.parseLong(expiration));
      } catch (NumberFormatException ex) {
        LOGGER.warn("Invalid value defined for property(should be numeric): " + MESSAGE_EXPIRATION_PROP);
      }
    }

    //check maximum message size
    int maxMessageSize = Integer.getInteger(MAX_MESSAGE_SIZE_PROP, -1);
    if (maxMessageSize != -1 && message.length() > maxMessageSize) {
      throw new HornetQException(0, "Message size exceeds allowed limit " + maxMessageSize);
    }

    //Add a messageID as message property for debugging and troubleshooting purposes
    //b1408 change: try to re-use EventID as messageID. It was a ranom new UUID before
    Object messageID = extraParameters.get(HQConstants.MESSAGE_ID);
    if (messageID != null) {
      m.putStringProperty(HQConstants.MESSAGE_ID, messageID.toString());
    }
    
    //save filter parameters as message properties
    for (String parameter : extraParameters.keySet()) {
      m.putObjectProperty(parameter, extraParameters.get(parameter));
    }
    if(messageID != null) {
      //To prevent duplicate message between primary & backup server when failover happens
      m.putStringProperty(org.hornetq.api.core.Message.HDR_DUPLICATE_DETECTION_ID, new SimpleString(messageID.toString()));
    } else {
      LOGGER.error("Can't find the unique message id for message : " + message);
    }
    
    //send event
    producer.send(address, m);

    LOGGER.info("Event " + m + " published to address: " + address + " using session " + this);

    producer.close();

  }

  /**
   * rollback the transaction for this session
   * @throws HornetQException 
   */
  public void rollback() throws HornetQException {
    session.rollback(true);
  }

  /**
   * checks if session is closed
   *
   * @return true if session is closed
   */
  public boolean isClosed() {
    return session.isClosed();
  }

  @Override
  public String toString() {
    return (session != null) ? session.toString() : "Null or closed session: " + super.toString();
  }

  void close() throws HornetQException {
    session.close();
  }

  /**
   * Manage topic/address retry configuration
   * 
   * @param addressMatch addressMatch
   * @param redeliveryDelay redeliveryDelay
   * @param redeliveryDelayMultiplier redeliveryDelayMultiplier
   * @param maxRedeliveryDelay maxRedeliveryDelay
   * @param maxDeliveryAttempts maxDeliveryAttempts
   * @throws Exception Exception
   */
  public void manageRetry(String addressMatch, long redeliveryDelay,
      double redeliveryDelayMultiplier, long maxRedeliveryDelay,
      int maxDeliveryAttempts) throws Exception {
    ClientRequestor requestor = new ClientRequestor(session,
        HORNETQ_MANAGEMENT_ADDRESS);
    ClientMessage message = session.createMessage(false);
    ManagementHelper.putOperationInvocation(message, HORNETQ_RESOURCE_SERVER,
        HORNETQ_OPERATION_ADD_ADDRESS, addressMatch, HORNETQ_DEFAULT_DLQ,
        HORNETQ_DEFAULT_EXPIRYQUEUE, -1, false, maxDeliveryAttempts, -1, 10485760,
        5, redeliveryDelay, redeliveryDelayMultiplier, maxRedeliveryDelay, -1,
        true, "DROP");
    ClientMessage reply = requestor.request(message);
    LOGGER.info(reply);
    requestor.close();
  }
  
  public ClientSessionFactory getSessionFactory() {
    return (ClientSessionFactory)((ClientSessionInternal)session).getSessionFactory();
  }
  
  public ClientSession getClientSession() {
    return session;
  }

}
