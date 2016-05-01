package com.successfactors.hermesstore.service.scheduledjob;

import static com.successfactors.jobscheduler.ScheduledJobAttr.AttrType.*;

import java.util.List;

import com.successfactors.hermesstore.bean.EventStatusEnum;
import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.hermesstore.core.PublishEventTask;
import com.successfactors.hermesstore.core.SEBEventStore;
import com.successfactors.hermesstore.service.GetEventsByStatusCmd;
import com.successfactors.hermesstore.util.LogUtils;
import com.successfactors.jobscheduler.ScheduledJobAttr;
import com.successfactors.jobscheduler.ScheduledJobExecutionException;
import com.successfactors.jobscheduler.jobimpl.SimpleJob;
import com.successfactors.logging.api.*;
import com.successfactors.sca.ServiceApplicationException;

/**
 * Scheduled job to publish unpublised events from event store to SEB server (HornetQ).
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
@ScheduledJobAttr({PROVISIONING_SUPPORT, SINGLE_EXECUTION})
public class PublishSEBEventJob extends SimpleJob {
  
  private static final Logger logger = LogManager.getLogger(PublishSEBEventJob.class);
  private static final String JOB_NAME = PublishSEBEventJob.class.getSimpleName();

  @Override
  public void executeJob() throws ScheduledJobExecutionException {
    List<SEBEvent> sebEvents = getUnpublishedEvents();
    SEBEventStore.getPool().execute(new PublishEventTask(JOB_NAME, params, sebEvents));    
  }

  private List<SEBEvent> getUnpublishedEvents() throws ScheduledJobExecutionException {
    try {
      return getJobContextBean().getScaHandler().execute(
        new GetEventsByStatusCmd(SEBEventStore.getFetchSize(), EventStatusEnum.INITIAL, EventStatusEnum.FAILED));
    } catch (ServiceApplicationException e) {
      String message = "Failed to get unpublished events: error = {}";
      LogUtils.log(logger, Level.ERROR, message, e);
      throw new ScheduledJobExecutionException("Failed to get unpublished events", e);
    }
  }
  
}
