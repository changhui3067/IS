package com.successfactors.hermesstore.service.scheduledjob;

import static com.successfactors.jobscheduler.ScheduledJobAttr.AttrType.*;

import com.successfactors.jobscheduler.*;

/** 
 * Job type definition of {@link PublishSEBEventJob} job.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
@ScheduledJobAttr({SINGLE_EXECUTION})
public class PublishSEBEventJobType extends ScheduledJobTypeSupport {

  public static final String JOB_TYPE_NAME = PublishSEBEventJobType.class.getSimpleName();
  
  // file: /V4/au-V4-service/src/msgsrc/sfmessages/__DEFAULT/sfmessages-PROVISIONING.properties.utf8
  private static final String JOB_TYPE_LABEL_KEY = "PROVISIONING_SEB_EVENT_STORE_PUBLISH_JOB"; 
  
  @Override
  public String getJobTypeName() {
    return JOB_TYPE_NAME;
  }

  @Override
  public String getJobTypeLabelKey() {
    return JOB_TYPE_LABEL_KEY;
  }

  @Override
  public Class<? extends IScheduledJob> getJobClass() {
    return PublishSEBEventJob.class;
  }
  
}
