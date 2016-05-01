/*
 * $Id$
 */
package com.successfactors.hermes.util;

import com.successfactors.logging.api.Logger;
import com.successfactors.logging.api.MDC;
import com.sf.sfv4.util.StringUtils;
import EventCenter.hermes.core.Meta;

/**
 * LogUtils contains utility functions for Log4J
 * 
 * @author pqin
 * Success Factors
 */
public class SEBLogUtils {
  
  private static final Logger log = SEBLog.getLogger(SEBLogUtils.class);

  /**
   * These are the parameters declared in log4j that are used by it to display log messages.
   */
  public static final String PARAMS = "params";
  public static final String REQUEST_NO = "requestNo";
  public static final String IP_ADDRESS = "ipAddress";
  
  /**
   * invisible constructor
   */
  private SEBLogUtils() {
    
  }

  /**
   * The parambean is uesd by the logger to display the company id, userid, etc...
   * In most cases, this parameter bean is already set into the MDC for display by the BoilerPlateServlet.
   * For the cases where the thread is not executing the BoilerPlateServlet (such as JMS events), the
   * event itself has to set the parambean.
   * In addition, when the EJB method is being called directly from a client, since the client is
   * bypassing the BoilerPlateServlet, the param bean is not set and thus no parambean info is
   * included in the log messages making it very difficult to troublehshoot log messages that have
   * no parambean info.
   * This method below checks to see if parambean is set into the MDC, and if it's not, it does it and
   * return true to indicate that it set the parambean into the MDC. It also sets the the
   * request Id based on whether there is an existing request id or not.
   *
   * @param meta meta
   * @return whether it set the parambean into the MDC object.
   */
  public static boolean insertConversionParameters(String param)
  {
    if (!StringUtils.isBlank(param)) {
      MDC.put(PARAMS, param);
      return true;
    }
    return false;
  }

  /**
   * clean up parameters
   */
  public static void removeConversionParameters()
  {
    MDC.remove(PARAMS);
  }
  
  /**
   * get ParamBean string
   * @return formated ParamBean
   */
  public static String getFormatedParam(Meta meta) {
    String params = "[companyId=" + meta.getCompanyId() + ", userId=" + meta.getUserId() 
        + (meta.getProxyId() != null ? ", proxyId=" + meta.getProxyId() : "") + "] ";
    return params;
  }

}
