package com.successfactors.sef.ui.controller;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.successfactors.ajaxservice.annotation.AjaxMethod;
import com.successfactors.ajaxservice.annotation.AjaxService;
import com.successfactors.ajaxservice.ui.controller.util.AjaxServiceException;
import com.successfactors.execmanager.bean.SefEventStatsBean;
import com.successfactors.execmanager.service.GetSefEventDeliveredStats;
import com.successfactors.execmanager.service.GetSefEventGraphStats;
import com.successfactors.execmanager.vo.SefEventStatsVO;
import com.successfactors.execmanager.vo.SefEventTimeStatusVO;
import com.successfactors.execmanager.vo.TimeRangeEnum;
import com.successfactors.legacy.bean.PermissionListBean;
import com.successfactors.legacy.util.exception.application.AccessDeniedException;
import com.successfactors.legacy.util.permission.PermissionUtils;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import com.successfactors.platform.bean.ParamBean;
import com.successfactors.platform.util.Messages;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceCommandHandler;
import com.successfactors.sef.metadata.EventMetadata;
import com.successfactors.sef.metadata.EventMetadataProvider;
import com.successfactors.platform.di.SFContextConstant;
import javax.inject.Inject;

/**
 * @author I073200
 * 
 *         Controller for providing Smart Suite event statistics logged with XM
 */
@AjaxService
@Name("sefXMController")
@javax.inject.Named("sefXMController")
@Scope(ScopeType.EVENT)
@org.springframework.context.annotation.Scope(SFContextConstant.SCOPE_EVENT)
public class SefXMController {

    /**
     * Logger.
     */
    private static final transient Logger log = LogManager.getLogger();

    /** Service handler. */
    @Inject
    @In
    private ServiceCommandHandler scaHandler;
    
    @Inject
    @In(create = true)
    private ParamBean params;
    
    /** Permission list of login user. */
    @Inject
    @In(value= PermissionUtils.PERMISSION_LIST_BEAN)
    private PermissionListBean permListBean;

    @Inject
    @In
    private Messages v10msgs;

    /**
     * @param timeRange
     * @return List of SefEventStatsVO
     * @throws AjaxServiceException
     */
    @AjaxMethod
    public List<SefEventStatsVO> getEventStats(final TimeRangeEnum timeRange)
            throws AjaxServiceException {

    GetSefEventDeliveredStats eventStats = new GetSefEventDeliveredStats();
    eventStats.setTimeRange(timeRange);
    SefEventStatsBean bean = null;
    try {
        if(!permListBean.hasPermission("smartsuite_admin", "event_dashboard_smartsuite_admin", 1)) {
            throw new AccessDeniedException("No Permission");
        }
        bean = scaHandler.execute(eventStats);
    } catch (ServiceApplicationException ex) {
        log.error("Following Exception occured " + ex);
    }
    catch(AccessDeniedException e) {
        //log.error("No Permissions");
       throw new AjaxServiceException("You don't have the permission to access Event Center.");
    }
    for (SefEventStatsVO vo : bean.getEventStats()) {
        EventMetadata metaData = EventMetadataProvider.getEventMetadata(vo
                .getEventType());
        vo.setEventName(v10msgs.getString(metaData.getLocalizedTypeKey()));
    }

    return bean.getEventStats();
    }
    
    /**
     * @param processName
     * @param timeRange
     * @return the list of SefEventTimeStatusVO
     * @throws AjaxServiceException
     */
    @AjaxMethod
    public List<SefEventTimeStatusVO> getEventGraphStats(String eventName, TimeRangeEnum timeRange) 
             throws AjaxServiceException {
     GetSefEventGraphStats stats = new GetSefEventGraphStats();
     List<SefEventTimeStatusVO> eventGraphStatVOs;
     stats.setTimeRange(timeRange);
     stats.setEventName(eventName);
     try {
          if(!permListBean.hasPermission("smartsuite_admin", "event_dashboard_smartsuite_admin", 1)) {
             throw new AccessDeniedException("No Permission");
          }
          eventGraphStatVOs = scaHandler.execute(stats);
     } catch(ServiceApplicationException e) {
         log.error("Error while retreiving process graph", e);
         throw new AjaxServiceException("System error");
     } catch(AccessDeniedException e) {
         throw new AjaxServiceException("You don't have the permission to access Event Center.");
    }
    return eventGraphStatVOs;
   }
}
