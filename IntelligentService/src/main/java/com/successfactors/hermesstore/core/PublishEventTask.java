package com.successfactors.hermesstore.core;

import java.util.*;

import com.successfactors.appsec.AppSec;
import com.successfactors.hermes.core.SFEvent;
import com.successfactors.hermes.core.Topic;
import com.successfactors.hermes.engine.ProviderFactory;
import com.successfactors.hermes.engine.ProviderFactoryCreator;
import com.successfactors.hermesstore.bean.EventStatusEnum;
import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.hermesstore.service.RemoveEventListCmd;
import com.successfactors.hermesstore.service.UpdateEventListCmd;
import com.successfactors.hermesstore.util.*;
import com.successfactors.logging.api.*;
import com.successfactors.platform.bean.ParamBean;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceCommandHandler;
import com.successfactors.sca.service.ServiceCommandHandlerFactory;

/**
 * Runnable task to publish events.
 * 
 * If successfully published, the events will be removed from DB, 
 * otherwise will be updated with status and process result.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
public class PublishEventTask implements Runnable {
  
  private final Logger logger = LogManager.getLogger(PublishEventTask.class);
    
  private String executor;
  private ParamBean params;
  private List<SEBEvent> sebEvents;
  private ServiceCommandHandler scaHandler = ServiceCommandHandlerFactory.getSCAHandler();;
  private ProviderFactory pf = ProviderFactoryCreator.getFactoryInstance(ProviderFactoryCreator.FactoryType.server);
  
  public PublishEventTask(String executor, ParamBean params, List<SEBEvent> sebEvents) {
    this.executor = executor;
    this.params = params;
    this.sebEvents = sebEvents;
  }
  
  @Override
  public void run() {    
    if (CollectionUtils.hasElements(sebEvents)) {
      AppSec.setCurrentUser(params);
      publishEvents(sebEvents);
    }
  }
  
  private void publishEvents(List<SEBEvent> sebEvents) {
    Collections.sort(sebEvents);
    List<SEBEvent> successList = new ArrayList<SEBEvent>();
    List<SEBEvent> failedList  = new ArrayList<SEBEvent>();
    for (SEBEvent sebEvent : sebEvents) {
      Date now = new Date();
      SFEvent sfEvent = null;
      try {
        sfEvent = SEBEventStore.extractSFEvent(sebEvent);
        String topic = sfEvent.getMeta().getTopic();
        if (StringUtils.isBlank(topic)) {
          pf.getQueueManager().getQueueInstance(sfEvent).publishEvent(sfEvent);
        } else {
          pf.getQueueManager().getQueueInstance(new Topic(topic)).publishEvent(sfEvent);
        }
        successList.add(sebEvent);
        String message = "Successfully published event from event store to SEB server: eventUUID = {}";
        LogUtils.log(logger, Level.INFO, message, sebEvent.getEventId());
      } catch (Exception e) {
        if (EventStatusEnum.FAILED.equals(sebEvent.getStatus())) {
          sebEvent.setRetryTimes(sebEvent.getRetryTimes() + 1);
        } else {
          sebEvent.setStatus(EventStatusEnum.FAILED);
        }
        sebEvent.setPublishedAt(null);
        sebEvent.setProcessResult(e.toString());
        sebEvent.setLastUpdatedBy(executor);
        sebEvent.setLastUpdatedDate(now);
        failedList.add(sebEvent);
        String message = "Failed to publish event, will retry later: event = {}, error = {}";
        LogUtils.log(logger, Level.ERROR, message, sfEvent, e);
      } 
    }
    if (CollectionUtils.hasElements(successList)) {
      removeEventList(successList);
    }
    if (CollectionUtils.hasElements(failedList)) {
      updateEventList(failedList);
    }
  }
  
  private void removeEventList(List<SEBEvent> sebEvents) {
    try {
      scaHandler.execute(new RemoveEventListCmd(sebEvents));
    } catch (ServiceApplicationException e) {
      String message = "Failed to remove published events: events = {}, error = {}";
      LogUtils.log(logger, Level.ERROR, message, sebEvents, e);
    }
  }
  
  private void updateEventList(List<SEBEvent> sebEvents) {
    try {
      scaHandler.execute(new UpdateEventListCmd(sebEvents));
    } catch (ServiceApplicationException e) {
      String message = "Failed to update unpublished events: events = {}, error = {}";
      LogUtils.log(logger, Level.ERROR, message, sebEvents, e);
    }
  }
  
  
}  