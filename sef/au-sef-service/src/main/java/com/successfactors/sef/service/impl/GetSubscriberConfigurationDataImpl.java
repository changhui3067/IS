/*
 * $Id$
 */
package com.successfactors.sef.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jboss.seam.annotations.In;

import com.successfactors.category.api.enums.ModuleEnum;
import com.successfactors.hermes.bean.SubscriberMetaDataBean;
import com.successfactors.hermes.metadata.SubscriberMetaDataManager;
import com.successfactors.legacy.bean.provisioning.FeatureEnum;
import com.successfactors.legacy.service.GetSysConfig;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import com.successfactors.platform.bean.ParamBean;
import com.successfactors.platform.bean.SystemBean;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceCommandHandler;
import com.successfactors.sca.ServiceQuery;
import com.successfactors.sca.ServiceQueryImpl;
import com.successfactors.sca.config.Service;
import com.successfactors.sef.service.GetSubscriberConfigurationData;
import com.successfactors.sef.service.SEFExternalLink;
import com.successfactors.sef.bean.SEFSubscriberConfigurationUtil;
import com.successfactors.sef.bean.SEFSubscriberConfigurationVO;
import com.successfactors.sef.bean.genericobject.SEFSubscriberConfiguration;
import com.successfactors.sef.metadata.EventMetadataProvider;
import com.successfactors.sef.metadata.SubscriberMetadata;
import com.successfactors.sef.metadata.SubscriberMetadataProvider;
import com.successfactors.sef.util.SEFLazySelect;
import com.successfactors.sfutil.util.ReflectionUtils;
import com.successfactors.xi.util.SeamEnvUtils;

import javax.inject.Inject;

/**
 * GetSubscriber List
 * 
 */
@Service
public class GetSubscriberConfigurationDataImpl
    implements
    ServiceQueryImpl<List<SEFSubscriberConfigurationVO>, GetSubscriberConfigurationData> {

  /** LOGGER */
  private static final Logger LOGGER = LogManager
      .getLogger(GetSubscriberConfigurationDataImpl.class);

  /** SCA Handler */
  @Inject
  @In
  private ServiceCommandHandler scaHandler;

  @Inject
  @In
  private ParamBean params;

  /** {@inheritDoc} */

  @Override
  public List<SEFSubscriberConfigurationVO> execute(
      final GetSubscriberConfigurationData cmd)
      throws ServiceApplicationException {

    final List<SEFSubscriberConfigurationVO> listVO = new ArrayList<>();
   
    boolean singleQuery = (cmd.getSingleQuery() != null && cmd.getSingleQuery() == Boolean.TRUE) ? true
        : false;
    
  
    String eventType = cmd.getEventTopic() == null ? cmd.getEventType()
        : getEventType(cmd.getEventTopic());
    
    if(cmd.getEventType()!=null) {
      eventType = cmd.getEventType();
    }
    
    Integer hashCode = null;
    if (singleQuery) {
      hashCode = SEFSubscriberConfigurationUtil.hashCode(eventType,
          cmd.getCategoryID(), cmd.getSubscriberID());
    }
    
    listVO.addAll(retrieveSubscriberConfigurations(eventType, cmd.getCategoryID(), cmd.getSubscriberID(), singleQuery, hashCode));
    
    Collections.sort(listVO);
    return listVO;
  }

  private boolean isSEFPOCFeature(String pocKeyType) {
    if (pocKeyType == null || pocKeyType.isEmpty()) {
      return false;
    }
    GetSysConfig getSysConfig = new GetSysConfig(
        pocKeyType, pocKeyType);
    try {
      SystemBean sysConfig = scaHandler.execute(getSysConfig);
      return !(sysConfig == null || sysConfig.getSysLong() <= 0); 
    } catch (ServiceApplicationException e) {
      LOGGER.error(e.getMessage(), e);
      return false;
    }
  }
  
  private List<SEFSubscriberConfigurationVO> retrieveSubscriberConfigurations(
      String eventType, String categoryId, String subscriberId,
      boolean singleQuery, Integer hashCode) {    
    
    final List<SEFSubscriberConfigurationVO> listVO = new ArrayList<>();
   
    
    // Generate Subscriber configurations from HardCoded Json file
    final Map<Integer, SEFSubscriberConfigurationVO> mapVOFromDB = retrieveSubscriberConfigurationFromDB(
        eventType, categoryId, subscriberId, singleQuery);
  
    listVO.addAll(retrieveStaticSubscriberConfigurations(eventType, categoryId, subscriberId, singleQuery, hashCode, mapVOFromDB));
    listVO.addAll(retrieveDynamicSubscriberConfigurations(eventType, categoryId, subscriberId, singleQuery, hashCode, mapVOFromDB));
    
    return listVO;
    
  }
  
  private List<SEFSubscriberConfigurationVO> retrieveDynamicSubscriberConfigurations(String eventType, String categoryId, String subscriberId,
      boolean singleQuery, Integer hashCode, Map<Integer, SEFSubscriberConfigurationVO> mapVOFromDB) {
    
    final List<SEFSubscriberConfigurationVO> listVO = new ArrayList<>();
    // Generate SubscriberConfiguration from Dymnatic Subscribers.
    Map<String, Set<String>> eventModuleMap = new HashMap<>();
    final Map<Integer, SEFSubscriberConfigurationVO> mapVOFromSEB = getSubscriberMetaData();

    for (Map.Entry<Integer, SEFSubscriberConfigurationVO> entry : mapVOFromSEB
        .entrySet()) {
      SEFSubscriberConfigurationVO subVO = entry.getValue();
     
      if (eventType != null
          && !eventType.equalsIgnoreCase(subVO.getEventType())) {
        continue;
      }
      if (categoryId != null
          && !categoryId.equalsIgnoreCase(subVO.getCategoryID())) {
        continue;
      }
      
      Set<String> modules = eventModuleMap.get(subVO.getEventType());
      if (modules == null) {
        modules = new HashSet<String>();
      }
      modules.add(subVO.getCategoryID().toUpperCase());
      eventModuleMap.put(subVO.getEventType(), modules);

      if (singleQuery && !hashCode.equals(entry.getKey())) {
        continue;
      }
     
      SEFSubscriberConfigurationVO dbVO = mapVOFromDB.get(entry.getKey());
      if (dbVO != null) {
        subVO.setStatus(dbVO.isStatus());
        subVO.setReDelivered(dbVO.getReDelivered());
        subVO.setDaysInAdvance(dbVO.getDaysInAdvance());
      }
      listVO.add(subVO);
    }
    listVO.addAll(createModuleConfigurationVO(eventModuleMap, false, eventType, categoryId,  singleQuery,
        hashCode,  mapVOFromDB));
    return listVO;
  }
  
  private List<SEFSubscriberConfigurationVO> retrieveStaticSubscriberConfigurations(String eventType, String categoryId, String subscriberId,
      boolean singleQuery, Integer hashCode, Map<Integer, SEFSubscriberConfigurationVO> mapVOFromDB) {
    final List<SEFSubscriberConfigurationVO> listVO = new ArrayList<>();
    final List<String> eventTypes = Arrays.asList(SubscriberMetadataProvider
        .getEventTypes());
    
    // Generate Subscriber configurations from HardCoded Json file
    
    for (final String type : eventTypes) {

      final SubscriberMetadata subscriberMetaData = SubscriberMetadataProvider
          .getSubscriberMetadata(type);

      for (final SubscriberMetadata.Subscriber meta : subscriberMetaData.getSubscribers()) {
        
        if (!meta.getPocFeature().isEmpty() && !isSEFPOCFeature(meta.getPocFeature())) {
          continue;
        }
        
        if (eventType != null
            && !eventType.equalsIgnoreCase(type)) {
          continue;
        }
        
        if (categoryId != null
            && !categoryId.equalsIgnoreCase(meta.getModule())) {
          continue;
        }
        
        Integer curCode = SEFSubscriberConfigurationUtil.hashCode(type,
            meta.getModule(), null);
        
        if(singleQuery && !curCode.equals(hashCode)) {
          continue;
        }
        
        ModuleEnum mEnum = ModuleEnum.valueOf(meta.getModule());
        FeatureEnum[] featureEnums = mEnum.getFeatureEnums();
        
        if (featureEnums!=null && !isFeatureEnabled(featureEnums)) {
          LOGGER.info("Features are not enabled for "+meta.getSubscriberDescription());
          continue;
        }
        
        SEFSubscriberConfigurationVO subscriberVO = new SEFSubscriberConfigurationVO();
        subscriberVO.setEventType(type);
        subscriberVO.setCategoryID(meta.getModule()); 
        subscriberVO.setIsPreProcessing(true);
        subscriberVO.setSubscriberDescription(meta.getSubscriberDescription());
        
        if (!meta.getExternalLink().isEmpty()) {
          subscriberVO.setExnteralLink(parseExternalLink(meta.getExternalLink(), type));
          if (!meta.getExternalLinkTitle().isEmpty()) {
            subscriberVO.setExnteralLinkTitle(meta.getExternalLinkTitle());
          } 
        } 
        
        SubscriberMetadata.Subscriber.ImpactArea[] impactAreas = meta.getSubscribingImpactArea();
        for(int i=0; i<impactAreas.length; i++){
          subscriberVO.getSubscribingImpactAreas().add(impactAreas[i].getImpactArea());
        }
        
        
        SEFSubscriberConfigurationVO dbVO = mapVOFromDB.get(curCode);
        if (dbVO != null) {
          subscriberVO.setStatus(dbVO.isStatus());
          subscriberVO.setReDelivered(dbVO.getReDelivered());
          subscriberVO.setDaysInAdvance(dbVO.getDaysInAdvance());
        }
        else {
          // default value
          subscriberVO.setStatus(meta.getStatus());
          subscriberVO.setReDelivered(meta.getReDelivered());
          subscriberVO.setDaysInAdvance(meta.getDaysInAdvance()==null?null:meta.getDaysInAdvance().intValue());
        }
        
        listVO.add(subscriberVO);

      }
    }
    return listVO;
  }
  
  private String parseExternalLink(String linkString, String eventType) {
    String result = null;

    String[] keyValues = linkString.split(":");

    switch (keyValues[0].trim().toUpperCase()) {
    case "URL":
      result = keyValues[1];
      break;
    case "API":
      try {
        Object instance = ReflectionUtils.newInstance(Class
            .forName(keyValues[1].trim()));
        SEFExternalLink linkAPI = (SEFExternalLink) instance;
        if (linkAPI != null) {
          result = linkAPI.getExternalURL(eventType);
        }
      } catch (InstantiationException | ClassNotFoundException
          | NoSuchMethodException | InvocationTargetException e1) {
        LOGGER.error("Error of retrieving external link " + keyValues[1]);
      }
      break;    
    case "SEAM":
      SEFExternalLink linkAPI = (SEFExternalLink) SeamEnvUtils.getInstance(
          keyValues[1].trim(), true);
      if (linkAPI != null) {
        result = linkAPI.getExternalURL(eventType);
      }
      
      break;
    case "SCA": // do SCA call
     
      try {
        ServiceQuery<String>  getLinkQuery = (ServiceQuery<String>)Class.forName(keyValues[1].trim()).newInstance();
        result = scaHandler.execute(getLinkQuery);
        
      } catch (InstantiationException | IllegalAccessException
          | ClassNotFoundException | ServiceApplicationException e) {
        LOGGER.error("Error of retrieving external link ");
      }
      break;
    }

    return result;

  }
  
  private List<SEFSubscriberConfigurationVO> createModuleConfigurationVO(
      Map<String, Set<String>> moduleMap, Boolean isPreProcessing,
      String eventType, String categoryId, Boolean singleQuery,
      Integer hashCode, Map<Integer, SEFSubscriberConfigurationVO> mapVOFromDB) {
    
    List<SEFSubscriberConfigurationVO> result = new ArrayList<SEFSubscriberConfigurationVO>();

    for (Map.Entry<String, Set<String>> entry : moduleMap.entrySet()) {
      for (String cateogry : entry.getValue()) {
        String type = entry.getKey();
        Integer curCode = SEFSubscriberConfigurationUtil.hashCode(type,
            cateogry, null);
        if (singleQuery && !curCode.equals(hashCode)) {
          continue;
        }

        if (eventType != null && !eventType.equalsIgnoreCase(type)) {
          continue;
        }
        if (categoryId != null && !categoryId.equalsIgnoreCase(cateogry)) {
          continue;
        }

        SEFSubscriberConfigurationVO subscriberConfiguration = new SEFSubscriberConfigurationVO();

        subscriberConfiguration.setEventType(type);
        subscriberConfiguration.setCategoryID(cateogry);
        subscriberConfiguration.setIsPreProcessing(isPreProcessing);
        
        SEFSubscriberConfigurationVO dbVO = mapVOFromDB.get(curCode);
        if (dbVO != null) {
          subscriberConfiguration.setStatus(dbVO.isStatus());
          subscriberConfiguration.setReDelivered(dbVO.getReDelivered());
          subscriberConfiguration.setDaysInAdvance(dbVO.getDaysInAdvance());
        } else {
          // default value
          subscriberConfiguration.setStatus(true);
          subscriberConfiguration.setDaysInAdvance(null);
        }
        result.add(subscriberConfiguration);
      }
    }
    return result;

  }
  
  private Map<Integer, SEFSubscriberConfigurationVO> retrieveSubscriberConfigurationFromDB(
      final String eventType, final String categoryId,
      final String subscriberId, boolean singleQuery) {

    final Map<Integer, SEFSubscriberConfigurationVO> mapVOFromDB = new HashMap<>();
    final SEFLazySelect lazySelect = new SEFLazySelect();
    lazySelect.from(SEFSubscriberConfiguration.class);

    if (singleQuery) {
      // single Query, only query one item
      Integer code = SEFSubscriberConfigurationUtil.hashCode(eventType,
          categoryId, subscriberId);
      lazySelect.where("code").eq(code);
    } else {
      // query for multiple items
      if (eventType != null) {
        lazySelect.where("eventType").eq(eventType);
        
        if (categoryId != null) {
          lazySelect.and("catetoryID").eq(categoryId);
        }
        if (subscriberId != null) {
          lazySelect.and("subscriberID").eq(subscriberId);
        }
      }
    }

    final List<SEFSubscriberConfiguration> listConfiguration = lazySelect
        .getAll();

    for (final SEFSubscriberConfiguration sc : listConfiguration) {
      final SEFSubscriberConfigurationVO subscriberConfigurationVO = new SEFSubscriberConfigurationVO();
      subscriberConfigurationVO.setEventType(sc.getEventType());
      subscriberConfigurationVO.setCategoryID(sc.getCatetoryID());
      subscriberConfigurationVO.setSubscriberID(sc.getSubscriberID());
      subscriberConfigurationVO.setStatus(sc.isEnabled());
      subscriberConfigurationVO.setDaysInAdvance(sc.getDaysInAdvance()==null? null:sc.getDaysInAdvance().intValue());
      subscriberConfigurationVO.setReDelivered(sc.getReDelivered());

      mapVOFromDB.put(subscriberConfigurationVO.hashCode(),
          subscriberConfigurationVO);
    }
    return mapVOFromDB;
  }

  private String getEventType(final String topic) {

    final List<String> eventTypes = Arrays.asList(EventMetadataProvider
        .getEventTypes());
    for (final String type : eventTypes) {
      if (EventMetadataProvider.getEventMetadata(type).getTopic()
          .equalsIgnoreCase(topic)) {
        return type;
      }
    }
    return null;

  }

  private Map<Integer, SEFSubscriberConfigurationVO> getSubscriberMetaData() {

    Map<Integer, SEFSubscriberConfigurationVO> subscriberMetaData = new HashMap<>();

    Map<String, List<SubscriberMetaDataBean>> map = SubscriberMetaDataManager
        .getAllSubscribers();

    for (Entry<String, List<SubscriberMetaDataBean>> entry : map.entrySet()) {

      List<SubscriberMetaDataBean> subscriberMetaDataBeans = entry.getValue();
      String eventTopic = entry.getKey();
      for (SubscriberMetaDataBean bean : subscriberMetaDataBeans) {
        if (isFeatureEnabled(bean.getFeatureEnabledCheckFlag())
            && bean.isSmartSub()) {

          final SEFSubscriberConfigurationVO subscriberConfigurationVO = new SEFSubscriberConfigurationVO();
          String eventType = getEventType(eventTopic);
          ModuleEnum moduleEnum = bean.getModule();
          String catetoryId = moduleEnum.getName();
          String subscriberId = bean.getId();

          if (eventType == null || eventType.isEmpty() || catetoryId == null
              || catetoryId.isEmpty() || subscriberId == null
              || subscriberId.isEmpty()) {
            LOGGER.warn(bean.getId()+" has wrong subscriber meta data:  || event type:"
                + eventType + " || event topic: "+ eventTopic  + " || cateogry: " + catetoryId + " ||subscriber: "
                + subscriberId);
            continue;
          }
          subscriberConfigurationVO.setEventType(getEventType(eventTopic));
          subscriberConfigurationVO.setCategoryID(bean.getModule().getName());
          subscriberConfigurationVO.setSubscriberID(bean.getId());
          subscriberConfigurationVO.setSubscriberName(bean.getNameI18n());
          subscriberConfigurationVO
              .setSubscriberDescription(bean.getDescI18n());
          subscriberConfigurationVO.getSubscribingImpactAreas().addAll(
              bean.getImpactArea());
          subscriberMetaData.put(subscriberConfigurationVO.hashCode(),
              subscriberConfigurationVO);
          subscriberConfigurationVO.setStatus(true);
        } else if (!bean.isSmartSub()) {
          LOGGER.debug(bean.getId() + " is not a smart subscriber ");

        }

      }

    }

    return subscriberMetaData;

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
