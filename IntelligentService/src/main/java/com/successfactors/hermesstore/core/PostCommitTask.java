package com.successfactors.hermesstore.core;

import java.util.List;

import com.successfactors.appsec.AppSec;
import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.hermesstore.service.GetEventsByTransactionIdCmd;
import com.successfactors.hermesstore.util.LogUtils;
import com.successfactors.logging.api.*;
import com.successfactors.platform.bean.ParamBean;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceCommandHandler;
import com.successfactors.sca.service.ServiceCommandHandlerFactory;

/**
 * Runnable task to do post-commit publish.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
public class PostCommitTask implements Runnable {
  
  private final Logger logger = LogManager.getLogger(PostCommitTask.class);

  private ParamBean params;
  private String transactionId;
  private ServiceCommandHandler scaHandler = ServiceCommandHandlerFactory.getSCAHandler();
  
  public PostCommitTask(ParamBean params, String transactionId) {
    this.params = params;
    this.transactionId = transactionId;
  }
  
  @Override
  public void run() {
    List<SEBEvent> sebEvents = retrieveEvents();
    SEBEventStore.getPool().execute(new PublishEventTask(params.getUserId(), params, sebEvents));
    SEBEventStore.removeSynchronization(transactionId);
  }

  private List<SEBEvent> retrieveEvents() {
    AppSec.setCurrentUser(params);
    List<SEBEvent> sebEvents = null;
    try {
      sebEvents = scaHandler.execute(new GetEventsByTransactionIdCmd(transactionId));
    } catch (ServiceApplicationException e) {
      String message = "Failed to get events from event store: transactionId = {}, error = {}";
      LogUtils.log(logger, Level.ERROR, message, transactionId, e);
    }
    return sebEvents;
  }
  
}
