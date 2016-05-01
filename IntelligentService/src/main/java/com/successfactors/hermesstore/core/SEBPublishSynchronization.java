package com.successfactors.hermesstore.core;

import java.util.*;

import javax.transaction.Status;
import javax.transaction.Synchronization;

import com.successfactors.appsec.AppSec;
import com.successfactors.hermes.core.SEBApplicationException;
import com.successfactors.hermes.core.SFEvent;
import com.successfactors.hermesstore.bean.EventStatusEnum;
import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.hermesstore.service.AddEventListCmd;
import com.successfactors.hermesstore.util.LogUtils;
import com.successfactors.logging.api.*;
import com.successfactors.platform.bean.ParamBean;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceCommandHandler;
import com.successfactors.sca.service.ServiceCommandHandlerFactory;

/**
 * Synchronization implementation which is registered to original transaction.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
public class SEBPublishSynchronization implements Synchronization {
  
  private static final Logger logger = LogManager.getLogger(SEBPublishSynchronization.class);

  private final String transactionId;
  private ServiceCommandHandler scaHandler = ServiceCommandHandlerFactory.getSCAHandler();  
  private final List<SFEvent> eventQueue = Collections.synchronizedList(new ArrayList<SFEvent>());

  public SEBPublishSynchronization(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void addEvent(SFEvent sfEvent) {
    LogUtils.log(logger, Level.INFO, "Event added: eventUUID = {}", sfEvent.getMeta().getEventId());
    eventQueue.add(sfEvent);
  }

  @Override
  public void beforeCompletion() {
    List<SEBEvent> sebEvents = new ArrayList<SEBEvent>();
    Collections.sort(eventQueue);
    for (int i = 0; i < eventQueue.size(); i++) {
      SFEvent sfEvent = eventQueue.get(i);
      SEBEvent sebEvent = null;
      try {
        sebEvent = SEBEventStore.convert2SEBEvent(sfEvent);
      } catch (SEBApplicationException e) {
        String message = "Error occurrd when converting SFEvent to SEBEvent: SFEvent = {}, error = {}";
        LogUtils.log(logger, Level.ERROR, message, sfEvent, e);
        continue;
      }
      sebEvent.setPublishOrder(i + 1);
      sebEvent.setTransactionId(transactionId);
      sebEvent.setStatus(EventStatusEnum.INITIAL);
      sebEvents.add(sebEvent);
    }
    try {
      scaHandler.execute(new AddEventListCmd(sebEvents));
      String message = "Successfully saved events: events = {}";
      LogUtils.log(logger, Level.INFO, message, sebEvents);
    } catch (ServiceApplicationException e) {
      String message = "Failed to save events: events = {}, error = {}";
      LogUtils.log(logger, Level.ERROR, message, sebEvents, e);
    } finally {
      eventQueue.clear();
    }
  }

  @Override
  public void afterCompletion(int status) {
    if (Status.STATUS_COMMITTED == status) {
      ParamBean params = (ParamBean) AppSec.getCurrentUser();
      SEBEventStore.getPool().execute(new PostCommitTask(params, transactionId));
    }
  }

}