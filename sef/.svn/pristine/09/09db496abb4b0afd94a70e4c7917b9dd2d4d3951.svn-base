/**
 * 
 */
package com.successfactors.sef.execmanager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.successfactors.execmanager.EventLogger;
import com.successfactors.execmanager.EventTypeEnum;
import com.successfactors.category.api.enums.ModuleEnum;
import com.successfactors.execmanager.PayloadTypeEnum;
import com.successfactors.execmanager.ProcessTypeEnum;
import com.successfactors.execmanager.bean.EventBean;
import com.successfactors.execmanager.sef.SefEventAttributeKeyEnum;
import com.successfactors.execmanager.sef.SefEventNameEnum;
import com.successfactors.hermes.bean.BulkStandardEntityEvent;
import com.successfactors.hermes.bean.StandardEntityEvent;
import com.successfactors.hermes.core.SFEvent;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import com.successfactors.sef.metadata.EventMetadata;
import com.successfactors.sef.metadata.EventMetadataProvider;
import com.successfactors.serialization.SerializationException;
import com.successfactors.serialization.json.JSONSerializationUtils;

/**
 * @author I073200
 * 
 *         Facade for Smart Suite Publishers and Subscribers to log checkpoints
 *         with Execution Manager
 * 
 */
public class SefXMLogger {

  /** LOGGER */
  private static final Logger log = LogManager.getLogger();
  
  /**
   * Private Constructor
   */
  private SefXMLogger(){}
  
  /**
   * Used by Smart Suite subscriber to log a checkpoint for a single Smart Suite event. All the checkpoints logged for the same smart suite 
   * event(StandardEntityEvent) will correspond to a single process in Execution Manager. The processId will be internalId of the smart 
   * suite event (StandardEntityEvent) 
   * 
   * @param event the event
   * @param stdEvent the standardEvent contained within the event. If the event is a bulk event then this method should be called separately for every
   *        StandardEntityEvent contained in the bulk event.
   * @param subscriberName The name of the subscriber. Must be a single string.
   * @param messageType The type of checkpoint i.e INFO, WARNING or ERROR
   * @param message the description of checkpoint eg. "Could not process manager change event as the subject userId does not exist in LMS"
   * @param name the name of check point. Must be a single string. It is not madatory and can be null
   * @param messageAttributes Set of key value pairs to provide additional information about the checkpoint eg "managerId: cgrant". It is optional
   * @param payload Any data which is specific to checkpoint and cannot be represented as key-value pair is passed in as payload. The payload can 
   *        contain any additional data related to event. It could be a xml file that was being processed or it could be json representation of 
   *        the object that was being processed or it could even be a binary file. It is optional. 
   * @param payloadType the type of payload. It must be provided if payload is not null.
   */
  private static void logMessage(SFEvent event, StandardEntityEvent stdEvent, String subscriberName, EventTypeEnum messageType, String message, String name, 
      Map<String, String> messageAttributes, byte[] payload, PayloadTypeEnum payloadType) {
    try{
      logEvent(event, stdEvent, messageType, subscriberName + " : " + message, name, getSubsriberEventAttributes(event, subscriberName, messageAttributes), payload, payloadType);
    }
    catch(Exception e){
      log.error("Error while logging message", e); 
    }
  }

  /**
   * Used by Smart Suite subscriber to log the same checkpoint for all the smart suite events received in a bulk event. If the bulk event
   * contains lets's say 5 events then this method will add 5 checkpoints and each checkpoint will correspond to a different process. The 
   * processId will be internalId of the smart suite
   * event (StandardEntityEvent contained within bulk event)
   * 
   * @param event the event
   * @param bulkEvent the BulkStandardEntityEvent contained within the event. 
   * @param subscriberName The name of the subscriber. Must be a single string.
   * @param messageType The type of checkpoint i.e INFO, WARNING or ERROR
   * @param message the description of checkpoint eg. "Could not process manager change event as the subject userId does not exist in LMS"
   * @param name the name of check point. Must be a single string. It is not madatory and can be null
   * @param messageAttributes Set of key value pairs to provide additional information about the checkpoint eg "managerId: cgrant". It is optional
   * @param payload Any data which is specific to checkpoint and cannot be represented as key-value pair is passed in as payload. The payload can 
   *        contain any additional data related to event. It could be a xml file that was being processed or it could be json representation of 
   *        the object that was being processed or it could even be a binary file. It is optional. 
   * @param payloadType the type of payload. It must be provided if payload is not null.
   */
  private static void logMessage(SFEvent event, BulkStandardEntityEvent bulkEvent, String subscriberName, EventTypeEnum messageType, String message, String name, 
      Map<String, String> messageAttributes, byte[] payload, PayloadTypeEnum payloadType) {
    try{
      List<StandardEntityEvent> stdEvents = bulkEvent.getEvents();
      List<EventBean> eventBeans = new ArrayList<EventBean>();
      for(StandardEntityEvent stdEvent: stdEvents){
        EventBean eventBean = createEventBean(event, stdEvent, messageType, subscriberName + " : " + message, name, getSubsriberEventAttributes(event, subscriberName, messageAttributes), payload, payloadType);
        eventBeans.add(eventBean);
      }
      EventLogger.logEvent(eventBeans, true);
    }
    catch(Exception e){
      log.error("Error while logging message", e); 
    }
  }

  /**
   * Used by Smart Suite Publisher to log event raised checkpoint with Execution Manager. If the event is a bulk event that this method
   * will log a separate checkpoint for each of the StandardEntityEvent contained in the bulk event. Each of this checkpoint will correspond
   * to a different Execution Manager process. The processId will be internalId of the smart suite event (StandardEntityEvent contained within bulk event)
   * 
   * @param event the event
   */
  public static void logEventRaised(SFEvent event) {
    try{
      EventMetadata eventMetaData = EventMetadataProvider.getEventMetadata(event.getMeta().getType());
      String eventDesc = "Event raised for entity = " + eventMetaData.getEntity();

      Object body = event.getBody();
      if(body instanceof BulkStandardEntityEvent){
        List<StandardEntityEvent> stdEvents = ((BulkStandardEntityEvent)body).getEvents();
        List<EventBean> eventBeans = new ArrayList<EventBean>();
        for(StandardEntityEvent stdEvent: stdEvents){
          EventBean eventBean = createEventBean(event, stdEvent, EventTypeEnum.INFO, eventDesc, SefEventNameEnum.eventRaised.name(), 
              getPuslisherEventAttributes(stdEvent), convertToJson(stdEvent), PayloadTypeEnum.JSON);
          eventBeans.add(eventBean);
        }
        EventLogger.logEvent(eventBeans, true);
      }
      else if (body instanceof StandardEntityEvent){
        logEvent(event, (StandardEntityEvent)body, EventTypeEnum.INFO, eventDesc, SefEventNameEnum.eventRaised.name(), 
            getPuslisherEventAttributes((StandardEntityEvent)body), convertToJson(body), PayloadTypeEnum.JSON);
      }

    }
    catch(Exception e){
      log.error("Error while logging event raised", e); 
    }
  }
  
  private static byte[] convertToJson(Object object){
    byte[] jsonPayload = null;
    try {
      String json = JSONSerializationUtils.toJSON(object, true);
      jsonPayload = json.getBytes("UTF-8");
    } catch (SerializationException e) {
      log.error("error while converting event to json", e);
    } catch (UnsupportedEncodingException e) {
      log.error("error while converting event to json", e);
    }
    return jsonPayload;
  }

  private static Map<String, String> getSubsriberEventAttributes(SFEvent event, String subscriberName, Map<String, String> messageAttributes) {
    Map<String, String> attributes = new HashMap<String, String>();
    if(messageAttributes != null){
      attributes.putAll(messageAttributes);
    }
    attributes.put(SefEventAttributeKeyEnum.subscriberName.name(),subscriberName);
    return attributes;
  }

  private static Map<String, String> getPuslisherEventAttributes(StandardEntityEvent stdEvent) {
    Map<String, String> eventAttributes = new HashMap<String, String>();
    eventAttributes.put(SefEventAttributeKeyEnum.numEvent.name(), "1");
    eventAttributes.put(SefEventAttributeKeyEnum.effectiveStartDate.name(), stdEvent.getEffectiveStartDate());
    eventAttributes.putAll(stdEvent.getEntityKeys());
    return eventAttributes;
  }

  private static void logEvent(SFEvent event, StandardEntityEvent stdEvent, EventTypeEnum eventType,String eventDesc, String eventName, Map<String, String> eventAttributes, 
      byte[] payload, PayloadTypeEnum payloadType) {
    EventLogger.logEvent(createEventBean(event, stdEvent, eventType, eventDesc, eventName, eventAttributes, payload, payloadType));
  }
  
  
  private static EventBean createEventBean(SFEvent event, StandardEntityEvent stdEvent, EventTypeEnum eventType,String eventDesc, String eventName, Map<String, String> eventAttributes, 
      byte[] payload, PayloadTypeEnum payloadType) {
    EventMetadata eventMetaData = EventMetadataProvider.getEventMetadata(event.getMeta().getType());
    ModuleEnum moduleEnum = null;
    try{
      moduleEnum = ModuleEnum.valueOf(eventMetaData.getSourceArea().name());
    }
    catch(IllegalArgumentException e){
      log.error("Didn't find enum for module name", e);
    }
    return new EventBean(ProcessTypeEnum.SMART_SUITE, moduleEnum, eventMetaData.getType(), stdEvent.getInternalId(), null, null, 
        event.getMeta().getUserId(), event.getMeta().getEventId(), new Date(), eventType, eventDesc, eventName, payload, payloadType, eventAttributes);
  }
  
  
  

  /**
   * Used by Smart Suite subscriber to log event delivered checkpoint with
   * Execution Manager
   * 
   * @param event
   *          the event
   * @param subscriberName
   *          fully qualified class name of subscriber
   * @param subscriberModule
   *          the module to which subscriber belongs to
   */
/*  public void logEventDelivered(SFEvent event, String subscriberName, SourceAreaType subscriberModule) {
    try{
      Map<String, String> eventAttributes = getSubsriberEventAttributes(event, subscriberName, subscriberModule);
      logEvent(event, EventTypeEnum.INFO, "Event delivered", SefEventNameEnum.eventDelivered.name(), eventAttributes, null);
    }
    catch(Exception e){
      log.error("Error while logging event delivered", e); 
    }
  }*/

  /**
   * Used by Smart Suite subscriber to log event delivery failed checkpoint with
   * Execution Manager
   * 
   * @param event
   *          the event
   * @param subscriberName
   *          fully qualified class name of subscriber
   * @param subscriberModule
   *          the module to which subscriber belongs to
   */
/*  public void logEventDeliveryFailed(SFEvent event, String subscriberName, SourceAreaType subscriberModule) {
    try{
    Map<String, String> eventAttributes = getSubsriberEventAttributes(event, subscriberName, subscriberModule);
      logEvent(event, EventTypeEnum.INFO, "Event delivery failed",SefEventNameEnum.eventDeliveryFailed.name(), eventAttributes, null);
    }catch(Exception e){
      log.error("Error while logging event delivery failure", e); 
    }
  }*/

}
