/*
 * $Id$
 */
package com.successfactors.sef.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.seam.annotations.In;

import com.successfactors.legacy.bean.provisioning.FeatureEnum;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import com.successfactors.platform.bean.ParamBean;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceCommandHandler;
import com.successfactors.sca.ServiceQueryImpl;
import com.successfactors.sca.config.Service;
import com.successfactors.sef.bean.SEFEntityEventsUtil;
import com.successfactors.sef.bean.genericobject.SEFEntityEventsMap;
import com.successfactors.sef.bean.genericobject.SEFEvent;
import com.successfactors.sef.service.GetEntityEventsMap;
import com.successfactors.sef.util.SEFLazySelect;

/**
 * GetSubscriber List
 * 
 */
@Service
public class GetEntityEventsMapImpl
    implements
    ServiceQueryImpl<List<String>, GetEntityEventsMap> {

  /** LOGGER */
  private static final Logger LOGGER = LogManager
      .getLogger(GetEntityEventsMapImpl.class);

  /** SCA Handler */
  @Inject
  @In
  private ServiceCommandHandler scaHandler;

  @Inject
  @In
  private ParamBean params;

  /** {@inheritDoc} */

  @Override
  public List<String> execute(GetEntityEventsMap cmd)
      throws ServiceApplicationException {
   
    return retrieveEntityEvents(cmd.getEntityName(), cmd.getEntityKeys(), cmd.getEffectiveDate());
  }
  
  private List<String> retrieveEntityEvents(
      final String entityName, final Map<String, String> entityKeyValues, String effectiveDate) {

    final List<String> eventList = new ArrayList<>();
    final SEFLazySelect lazySelect = new SEFLazySelect();
    lazySelect.from(SEFEntityEventsMap.class);

   
      // single Query, only query one item
    Long code = SEFEntityEventsUtil.hashCode(entityName, entityKeyValues, effectiveDate);
    lazySelect.where("code").eq(code);
    

    final List<SEFEntityEventsMap> entityEvents = lazySelect
        .getAll();
    
    if (entityEvents.isEmpty()) {
      return eventList;
    } else if (entityEvents.size() > 1) {
      LOGGER.error("More than one entity are found for " + entityName );
      for (Map.Entry<String, String> entry :  entityKeyValues.entrySet()) {
        LOGGER.error("key : " + entry.getKey() + " value : " + entry.getValue());
      }
    }
    SEFEntityEventsMap entityEventsMap = entityEvents.get(0);
    List<SEFEvent> events = entityEventsMap.getEvents();
  
    for (SEFEvent event : events) {
     eventList.add(event.getEventType());
    }
   
    return eventList;
  }
  
  private boolean isFeatureEnabled(String[] featureEnums) {

    for (String featureEnum : featureEnums) {
     try{
      if (!featureEnum.isEmpty() && !params.isFeatureExist(FeatureEnum.valueOf(featureEnum))) {
          LOGGER.debug(featureEnum + " feature is disabled ");   
        return false;
      }
     }catch (IllegalArgumentException e) {
       LOGGER.error(e.getMessage());
       return false;
     }
    }
    return true;
  }
  
  
  private boolean isFeatureEnabled(FeatureEnum[] featureEnums) {

    for (FeatureEnum featureEnum : featureEnums) {
     try{
      if (!params.isFeatureExist(featureEnum)) {
        LOGGER.debug(featureEnum.getMessageKey() + " feature is disabled "); 
        return false;
      }
     }catch (IllegalArgumentException e) {
       LOGGER.error(e.getMessage());
       return false;
     }
    }
    return true;
  }


  

}
