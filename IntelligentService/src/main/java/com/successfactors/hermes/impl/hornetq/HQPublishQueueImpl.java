package com.successfactors.hermes.impl.hornetq;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.hornetq.api.core.HornetQException;
import org.hornetq.api.core.HornetQExceptionType;


import com.successfactors.hermes.core.HermesException;
import com.successfactors.hermes.core.SEBApplicationException;
import com.successfactors.hermes.core.SFEvent;
import com.successfactors.hermes.core.Topic;
import com.successfactors.hermes.engine.PublishQueue;
import com.successfactors.hermes.util.SEBLog;
import com.successfactors.logging.api.Logger;
import com.successfactors.serialization.SerializationException;
import com.successfactors.serialization.json.JSONSerializationUtils;
import org.springframework.util.StringUtils;

/**
 * Implementation of the publish queue.
 * @author ddiodati
 *
 */
public class HQPublishQueueImpl implements PublishQueue {
  private static final Logger log = SEBLog.getLogger(HQPublishQueueImpl.class);
  private Topic topic = null;

  
  /**
   * Creates a implementation based on a topic.
   * @param topic The topic name.
   */
  public HQPublishQueueImpl( Topic topic) {
    this.topic = topic;
  }
  
  /**
   * 
   * {@inheritDoc}.
   */
  public UUID publishEvent(SFEvent evt) throws SerializationException, HermesException{
    
    if (evt == null ||
        evt.getBody() == null || evt.getMeta() == null ) {
      throw new SEBApplicationException("Invalid event, meta or body is null");
    }
    
    if(topic !=null) {
      evt.getMeta().setTopic(topic.toString());
    }
    
    String id = evt.getMeta().getEventId();
    String ptpName = evt.getMeta().getPtpName();
   
    if (topic != null && !StringUtils.isEmpty(ptpName) ||
        topic == null && StringUtils.isEmpty(ptpName)) {
      throw new SEBApplicationException("Topic or ptpname is required(Not both at the same time).");
    }
    
    UUID uuid = null;
    if (StringUtils.isEmpty(id)) {
      uuid = UUID.randomUUID();
    } else {
      uuid = UUID.fromString(id);
    }
    try {
      
      evt.getMeta().setEventId(uuid.toString());
      evt.getMeta().setPublishedAt(System.currentTimeMillis());
      String json = JSONSerializationUtils.toJSON(evt,true);
      
      String address = (topic != null ) ? topic.toString() : HQConstants.PTP_PREFIX + ptpName;
      
      //set filtering values as additional properties
      Map<String, Object> extraParameters = (evt.getMeta().getFilterParameters() != null) ? 
              new HashMap<String, Object>(evt.getMeta().getFilterParameters()) : 
              new HashMap<String, Object>();

      //those values are duplicated in message properties for logging and filtering purposes
      extraParameters.put(HQConstants.COMPANY_NAME, evt.getMeta().getCompanyId());
      extraParameters.put(HQConstants.USER_ID, evt.getMeta().getUserId());
      extraParameters.put(HQConstants.MESSAGE_PRIORITY, new Integer(evt.getMeta().getPriority()));
      extraParameters.put(HQConstants.MESSAGE_ID, uuid.toString());
    
      HQSession session = HQSessionPool.getInstance().getSession();
      long t0 = System.currentTimeMillis();
      try {        
        session.publish(address, json, extraParameters);
      } catch (HornetQException ex) {
        if (ex.getType().equals(HornetQExceptionType.CONNECTION_TIMEDOUT)) {
          //fix for stale connection
          session.close();
          log.error("Connection timed out: " + ex.getMessage()
                 + ", .publish() blocked for " + (System.currentTimeMillis() - t0));
          throw new HermesException(ex.getMessage(), ex);
        }
      } finally {
        log.info(String.format("Event published id=%s, address=%s", uuid.toString(), address));
        HQSessionPool.getInstance().releaseSession(session);
      }
      
    
    } catch (HornetQException e ) {
      throw new HermesException(e.getMessage(),e);
    }
    // add event to hornetq, and register here with some kid of status.
    return uuid;
  }

  private static final int CONNECTION_TIMEDOUT = 003;

}
