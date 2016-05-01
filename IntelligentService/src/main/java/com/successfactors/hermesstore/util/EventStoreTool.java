package com.successfactors.hermesstore.util;

import static com.successfactors.hermesstore.core.SEBEventStore.*;
import static com.successfactors.hermesstore.util.Constants.*;

import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import com.successfactors.appsec.AppSec;
import com.successfactors.dynamicgroups.util.SearchBeanBase.OP_STR;
import com.successfactors.hermesstore.core.SEBEventStore;
import com.successfactors.hermesstore.service.scheduledjob.PublishSEBEventJobType;
import com.successfactors.jobscheduler.JobSchedulerException;
import com.successfactors.jobscheduler.bean.*;
import com.successfactors.jobscheduler.bean.JobRequestSearchBean.REQUEST_SEARCHABLE_FIELD;
import com.successfactors.jobscheduler.service.JobScheduleFacade;
import com.successfactors.legacy.bean.eo.RequestDefinition;
import com.successfactors.legacy.bean.provisioning.GlobalSysConfigBean;
import com.successfactors.legacy.service.globalsysconfig.GetGlobalSysConfigByKeyAndTypeWithoutCompany;
import com.successfactors.legacy.service.globalsysconfig.UpdateGlobalSysConfigByKeyAndType;
import com.successfactors.logging.api.*;
import com.successfactors.platform.bean.CompanyBean;
import com.successfactors.platform.bean.ParamBean;
import com.successfactors.platform.util.EJBClientUtil;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.service.handler.ejb.LocalContextSCAHandler;

/**
 * Event store client tool.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
public class EventStoreTool {
  
  private static final Logger logger = LogManager.getLogger(EventStoreTool.class);

  private static final String DEFAULT_SERVER = "default";
  private static final String DEFAULT_SEB_JNDI_FILE = "jndiclient.xml";
  
  private static final int ACTION_NUM_DEFULT_DO_NOTHING     = 0;
  private static final int ACTION_NUM_ENABLE_COMPANY        = 1;
  private static final int ACTION_NUM_RESET_ENABLED_LIST    = 2;
  private static final int ACTION_NUM_DISABLE_COMPANY       = 3; 
  private static final int ACTION_NUM_DISABLE_ALL           = 4;
  private static final int ACTION_NUM_CANCEL_PUBLISH_JOBS   = 5;
  private static final int ACTION_NUM_SCHEDULE_PUBLISH_JOBS = 6;
  
  private static final String INPUT_PARAMETER_NAME_SERVER       = "-w";
  private static final String INPUT_PARAMETER_NAME_JOB_INTERVAL = "-jobInterval";
  
  private static final String ACTION_NAME_ENABLE_COMPANY        = "-enable";
  private static final String ACTION_NAME_RESET_ENABLED_LIST    = "-reset";
  private static final String ACTION_NAME_DISABLE_COMPANY       = "-disable"; 
  private static final String ACTION_NAME_DISABLE_ALL           = "-disableAll";
  private static final String ACTION_NAME_CANCEL_PUBLISH_JOBS   = "-cancelPublishJobs";
  private static final String ACTION_NAME_SCHEDULE_PUBLISH_JOBS = "-schedulePublishJobs";
  private static final String ACTION_NAME_DEFULT_DO_NOTHING     = EMPTY_STRING;
  
  private static final String DEFAULT_JOB_INTERVAL = "20";
  private static final String DEFAULT_CRON_EXPR    = "0 0/" + DEFAULT_JOB_INTERVAL + " * * * ?"; // for every 20mins
  private static final String DEFAULT_USER_ID      = "v4admin";
  
  private static final int SLEEPING_TIME_MILLIS = 30 * 1000; // 30 seconds
  
  private static void showUsage() {
    StringBuilder sb = new StringBuilder("Usage: ");
    sb.append(EventStoreTool.class.getName())
      .append(ESCAPE_BREAK)
      .append("[").append(INPUT_PARAMETER_NAME_SERVER).append(" server <jndi server config name from jndiclient.xml>]").append(ESCAPE_BREAK)
      .append("[").append(ACTION_NAME_ENABLE_COMPANY).append(" <companyId>,<company2>,...] (enable event store feature for these companies)").append(ESCAPE_BREAK)
      .append("[").append(ACTION_NAME_RESET_ENABLED_LIST).append(" <companyId>,<company2>,...] (reset the enabled company list with specified companies)").append(ESCAPE_BREAK)
      .append("[").append(ACTION_NAME_DISABLE_COMPANY).append(" <companyId>,<company2>,...] (disable event store feature for these companies)").append(ESCAPE_BREAK)
      .append("[").append(ACTION_NAME_DISABLE_ALL).append("] (disable all existing enabled companies)").append(ESCAPE_BREAK)
      .append("[").append(ACTION_NAME_CANCEL_PUBLISH_JOBS).append("] (cancel all publish jobs)").append(ESCAPE_BREAK)
      .append("[").append(ACTION_NAME_SCHEDULE_PUBLISH_JOBS).append("] (schedule publish jobs for enabled companies)").append(ESCAPE_BREAK)
      .append("[").append(INPUT_PARAMETER_NAME_JOB_INTERVAL).append(" <interval>] (interval to execute the publish job, the unit is minute)").append(ESCAPE_BREAK);
    LogUtils.log(logger, Level.INFO, sb.toString());
  }
  
  /**
   * main method for EventStoreTool
   * 
   * @param args 
   *          Input arguments.
   * @throws Exception
   *          If something goes wrong.
   */
  public static void main(String[] args) throws Exception {
        
    String server          = DEFAULT_SERVER;
    String jndiClient      = DEFAULT_SEB_JNDI_FILE;
    String jobInterval     = EMPTY_STRING;
    String resetCompanyIDs = EMPTY_STRING;
    String cronExpression  = DEFAULT_CRON_EXPR;
    
    Set<String> currentCompanySet = new HashSet<String>();
    Set<String> enableCompanySet  = new HashSet<String>();
    Set<String> disableCompanySet = new HashSet<String>();
    Set<String> resetCompanySet   = new HashSet<String>();
    
    Set<Integer> actionSet = new HashSet<Integer>();
        
    for (int i = 0; i < args.length; i++) {
      if (INPUT_PARAMETER_NAME_SERVER.equals(args[i])) {
        if (i + 1 < args.length) {
          server = args[i + 1];
        }
        i++;
      } else if (ACTION_NAME_ENABLE_COMPANY.equals(args[i])) {
        if (args.length < i + 1) {
          showUsageAndExist();
        } else {
          StringTokenizer st = new StringTokenizer(args[i + 1], COMMA);
          while (st.hasMoreTokens()) {
            enableCompanySet.add(st.nextToken());
          }
        }
        if (CollectionUtils.hasNoElements(enableCompanySet)) {
          showUsageAndExist();
        }
        actionSet.add(ACTION_NUM_ENABLE_COMPANY);
        i++;
      } else if (ACTION_NAME_RESET_ENABLED_LIST.equals(args[i])) {
        if (args.length < i + 1) {
          showUsageAndExist();
        } else {
          resetCompanyIDs = args[i + 1];
          StringTokenizer st = new StringTokenizer(resetCompanyIDs, COMMA);
          while (st.hasMoreTokens()) {
            resetCompanySet.add(st.nextToken());
          }
        }
        if (CollectionUtils.hasNoElements(resetCompanySet)) {
          showUsageAndExist();
        }
        actionSet.add(ACTION_NUM_RESET_ENABLED_LIST);
        i++;
      } else if (ACTION_NAME_DISABLE_COMPANY.equals(args[i])) {
        if (args.length < i + 1) {
          showUsageAndExist();
        } else {
          StringTokenizer st = new StringTokenizer(args[i + 1], COMMA);
          while (st.hasMoreTokens()) {
            disableCompanySet.add(st.nextToken());
          }
        }
        if (CollectionUtils.hasNoElements(disableCompanySet)) {
          showUsageAndExist();
        } 
        actionSet.add(ACTION_NUM_DISABLE_COMPANY);
        i++;
      } else if (ACTION_NAME_DISABLE_ALL.equals(args[i])) {
        actionSet.add(ACTION_NUM_DISABLE_ALL);
      } else if (ACTION_NAME_CANCEL_PUBLISH_JOBS.equals(args[i])) {
        actionSet.add(ACTION_NUM_CANCEL_PUBLISH_JOBS);
      } else if (ACTION_NAME_SCHEDULE_PUBLISH_JOBS.equals(args[i])) {
        actionSet.add(ACTION_NUM_SCHEDULE_PUBLISH_JOBS);
      } else if (INPUT_PARAMETER_NAME_JOB_INTERVAL.endsWith(args[i])) {
        if (args.length < i + 1) {
          showUsageAndExist();
        }
        jobInterval = args[i + 1];
        cronExpression = cronExpression.replaceAll(DEFAULT_JOB_INTERVAL, jobInterval);
        i++;
      }
    }
    
    validateActions(actionSet);
    
    LogUtils.log(logger, Level.INFO, "Server: {}, config file: {}", server, jndiClient);
    
    String currentCompanyIDs = EMPTY_STRING;
    LocalContextSCAHandler scaHandler = LocalContextSCAHandler.create();
    ParamBean params = ParamBean.getProcessJobParambean();
    params.setUserId(DEFAULT_USER_ID);
    AppSec.setCurrentUser(params);
    GlobalSysConfigBean sysBean = scaHandler
      .execute(new GetGlobalSysConfigByKeyAndTypeWithoutCompany(SEBEventStore.GLOBAL_SYS_CONFIG_KEY, SEBEventStore.GLOBAL_SYS_CONFIG_TYPE));
    if (sysBean != null) {
      currentCompanyIDs = sysBean.getSysString();
      if (StringUtils.isNotBlank(currentCompanyIDs)) {
        StringTokenizer st = new StringTokenizer(currentCompanyIDs, COMMA);
        while (st.hasMoreTokens()) {
          currentCompanySet.add(st.nextToken());
        }
      }
    }
    
    LogUtils.log(logger, Level.INFO, "Current enabled companies: {}", currentCompanyIDs);
    
    if (actionSet.contains(ACTION_NUM_RESET_ENABLED_LIST)) {
      for (String companyId : resetCompanySet) {
        if (!validateCompany(companyId)) {
          showUsageAndExist();
        }
      }
      cancelPublishJob(currentCompanySet);
      Thread.sleep(SLEEPING_TIME_MILLIS);
      updateCompanyList(sysBean, resetCompanyIDs);
      submitPublishJob(resetCompanySet, cronExpression);
    } else if (actionSet.contains(ACTION_NUM_DISABLE_ALL)) {
      cancelPublishJob(currentCompanySet);
      updateCompanyList(sysBean, EMPTY_STRING);
    } else if (actionSet.contains(ACTION_NUM_CANCEL_PUBLISH_JOBS)) {
      cancelPublishJob(currentCompanySet);
    } else if (actionSet.contains(ACTION_NUM_SCHEDULE_PUBLISH_JOBS)) {
      cancelPublishJob(currentCompanySet);
      Thread.sleep(SLEEPING_TIME_MILLIS);
      submitPublishJob(currentCompanySet, cronExpression);
    } else {
      Set<String> needEnabledCompanySet  = new HashSet<String>();
      Set<String> needDisabledCompanySet = new HashSet<String>();
      
      boolean hasChanged = false;
      
      if (actionSet.contains(ACTION_NUM_ENABLE_COMPANY)) {
        for (String companyId : enableCompanySet) {
          if (!validateCompany(companyId)) {
            showUsageAndExist();
          }
        }
        for (String companyId : enableCompanySet) {
          if (!currentCompanySet.contains(companyId)) {
            hasChanged = true;
            currentCompanySet.add(companyId);
            needEnabledCompanySet.add(companyId);
          } 
        }  
        LogUtils.log(logger, Level.INFO, "Companies to enable: {}", needEnabledCompanySet);
        currentCompanyIDs = formatCompanyIDs(currentCompanySet);    
        submitPublishJob(needEnabledCompanySet, cronExpression);
      }
      
      if (actionSet.contains(ACTION_NUM_DISABLE_COMPANY)) {
        for (String companyId : disableCompanySet) {
          if (!validateCompany(companyId)) {
            showUsageAndExist();
          }
        }
        for (String companyId : disableCompanySet) {
          if (currentCompanySet.contains(companyId)) {
            hasChanged = true;
            currentCompanySet.remove(companyId);
            needDisabledCompanySet.add(companyId);
          }
        }
        LogUtils.log(logger, Level.INFO, "Companies to disable: {}", needDisabledCompanySet);
        currentCompanyIDs = formatCompanyIDs(currentCompanySet);    
        cancelPublishJob(needDisabledCompanySet);
      }
      
      if (hasChanged) {
        updateCompanyList(sysBean, currentCompanyIDs);
      }
    }
    
  }
  
  private static void showUsageAndExist() {
    showUsage();
    System.exit(1);
  }
  
  private static void validateActions(Set<Integer> actionSet) {
    if (actionSet.size() == 0) {
      showUsageAndExist();
    }
    if (actionSet.contains(ACTION_NUM_RESET_ENABLED_LIST) && actionSet.size() > 1) {
      String message = "\"" + ACTION_NAME_RESET_ENABLED_LIST + "\" should be used alone.";
      LogUtils.log(logger, Level.INFO, message);
      showUsageAndExist();
    }
    if (actionSet.contains(ACTION_NUM_DISABLE_ALL) && actionSet.size() > 1) {
      String message = "\"" + ACTION_NAME_DISABLE_ALL + "\" should be used alone.";
      LogUtils.log(logger, Level.INFO, message);
      showUsageAndExist();
    }
    if (actionSet.contains(ACTION_NUM_CANCEL_PUBLISH_JOBS) && actionSet.size() > 1) {
      String message = "\"" + ACTION_NAME_CANCEL_PUBLISH_JOBS + "\" should be used alone.";
      LogUtils.log(logger, Level.INFO, message);
      showUsageAndExist();
    }
    if (actionSet.contains(ACTION_NUM_SCHEDULE_PUBLISH_JOBS) && actionSet.size() > 1) {
      String message = "\"" + ACTION_NAME_SCHEDULE_PUBLISH_JOBS + "\" should be used alone.";
      LogUtils.log(logger, Level.INFO, message);
      showUsageAndExist();
    }
  }
  
  private static boolean validateCompany(String companyId) throws RemoteException, NamingException, CreateException {
    boolean valid = true;
    String message = EMPTY_STRING;
    CompanyBean companyBean = EJBClientUtil.getCompanyBean(companyId);
    if (companyBean == null) {
      valid = false;
      message = "Company <{}> was not found.";
    } else {
      if ((companyBean.getCompanyStatus() & CompanyBean.STATUS_DATABASE_CREATED) == 0) {
        valid = false;
        message = "Database tables were not yet created for company <{}>.";
      }
      if ((companyBean.getCompanyStatus() & CompanyBean.STATUS_DEACTIVATED) != 0) {
        valid = false;
        message = "Company <{}> was deactivated.";
      }
    }
    if (!valid) {
      LogUtils.log(logger, Level.ERROR, message, companyId);
    }
    return valid;
  }
  
  private static String formatCompanyIDs(Set<String> companySet) {
    StringBuilder sb = new StringBuilder();
    Object[] companyIDs = companySet.toArray();
    for (int i = 0; i < companyIDs.length; i++) {
      if (i != 0) {
        sb.append(COMMA);
      }
      sb.append(companyIDs[i].toString());
    }
    return sb.toString();
  }
  
  private static void updateCompanyList(GlobalSysConfigBean sysBean, String companyIDs) throws ServiceApplicationException {
    GlobalSysConfigBean globalSysConfigBean = null;
    LocalContextSCAHandler scaHandler = LocalContextSCAHandler.create();
    if (sysBean != null) {
      sysBean.setSysString(companyIDs);
      globalSysConfigBean = sysBean;
    } else {
      globalSysConfigBean = new GlobalSysConfigBean();
      globalSysConfigBean.setSysKey(GLOBAL_SYS_CONFIG_KEY);
      globalSysConfigBean.setSysType(GLOBAL_SYS_CONFIG_TYPE);
      globalSysConfigBean.setSysString(companyIDs);
    }
    scaHandler.execute(new UpdateGlobalSysConfigByKeyAndType(globalSysConfigBean));
    LogUtils.log(logger, Level.INFO, "Successfully updated enabled company list: {}", companyIDs);
  }
  
  private static void submitPublishJob(Set<String> companySet, String cronExpression) 
    throws RemoteException, NamingException, CreateException, JobSchedulerException, InterruptedException {
    cancelPublishJob(companySet);
    Thread.sleep(SLEEPING_TIME_MILLIS);
    for (String companyId : companySet) {
      CompanyBean companyBean = EJBClientUtil.getCompanyBean(companyId);
      ParamBean params = ParamBean.getProcessJobParambean();
      AppSec.setCurrentUser(params);
      params.populateParamBean(companyBean);
      SchedulingConfig sConfig = new SchedulingConfig(SchedulingTypeEnum.RECURRING, new Date(), null, cronExpression);
      RequestDefinition rDef = new RequestDefinition();
      rDef.setSchedulingConfig(sConfig);
      rDef.setRequestSpec();
      JobScheduleRequestEO requestEO = new JobScheduleRequestEO();
      requestEO.setJobName(PublishSEBEventJobType.JOB_TYPE_NAME + UNDER_LINE + new Date());
      requestEO.setJobType(new PublishSEBEventJobType());
      requestEO.setCreatorId(params.getUserId());
      requestEO.setStatus(JobRequestStatusEnum.NEW);
      requestEO.setRequestDef(rDef);
      JobScheduleFacade.scheduleAndRunJob(requestEO);
      LogUtils.log(logger, Level.INFO, "Successfully scheduled publish job for: {}", companyId);
    }
  }
  
  private static void cancelPublishJob(Set<String> companySet) throws RemoteException, NamingException, CreateException, JobSchedulerException {
    for (String companyId : companySet) {
      CompanyBean companyBean = EJBClientUtil.getCompanyBean(companyId);
      ParamBean params = ParamBean.getProcessJobParambean();
      AppSec.setCurrentUser(params);
      params.populateParamBean(companyBean);
      JobRequestSearchBean searchBean = new JobRequestSearchBean();
      searchBean.setSearchField(REQUEST_SEARCHABLE_FIELD.JOB_TYPE.getFieldName(), OP_STR.EQUAL, PublishSEBEventJobType.JOB_TYPE_NAME);
      List<JobScheduleRequestEO> requestEOList = JobScheduleFacade.findJobRequestByCriteria(searchBean);
      if (CollectionUtils.hasElements(requestEOList)) {
        for (JobScheduleRequestEO requestEO : requestEOList) {
          JobRequestStatusEnum status = requestEO.getStatus();
          if (!JobRequestStatusEnum.CANCELLED.equals(status)) {
            JobScheduleFacade.cancelJob(requestEO.getRequestId());
            LogUtils.log(logger, Level.INFO, "Successfully cancelled publish job for: {}", companyId);
          }
        }
      }
    }
  }
  
}
