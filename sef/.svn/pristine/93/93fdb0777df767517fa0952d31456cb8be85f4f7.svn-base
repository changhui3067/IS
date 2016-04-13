/*
 * $Id$
 */
package com.successfactors.sef.service.impl;

import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import org.jboss.seam.annotations.In;

import com.successfactors.legacy.bean.provisioning.FeatureEnum;
import com.successfactors.legacy.service.ejb.provisioning.Provisioning;
import com.successfactors.legacy.service.provisioning.ChangeFeatureLicense;
import com.successfactors.legacy.service.provisioning.GetValidDBPoolIds;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import com.successfactors.platform.bean.CompanyBean;
import com.successfactors.platform.bean.ParamBean;
import com.successfactors.pmcommon.bean.FeatureSet;
import com.successfactors.provisioningconfigframework.ProvisionConfigException;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceCommandHandler;
import com.successfactors.sca.ServiceCommandImpl;
import com.successfactors.sca.config.Service;
import com.successfactors.sef.service.SetPreFeatureForIntelligentService;

/**
 * SetPreFeatureForIntelligentServiceImpl
 * 
 */
@Service
public class SetPreFeatureForIntelligentServiceImpl implements
    ServiceCommandImpl<Void, SetPreFeatureForIntelligentService> {

  /** LOGGER */
  private static final Logger LOGGER = LogManager
      .getLogger(SetPreFeatureForIntelligentServiceImpl.class);

  /** SCA Handler */
  @Inject
  @In
  private ServiceCommandHandler scaHandler;

  @Inject
  @In
  private ParamBean params;
  
  @Override
  public Void execute(SetPreFeatureForIntelligentService cmd)
      throws ServiceApplicationException {
    
    boolean isBPEEnabled = params.getCompanyBean().isFeatureEnabledForCompany(FeatureEnum.ENABLE_BPE_SERVICE);
    boolean isMDFEnabled = params.getCompanyBean().isFeatureEnabledForCompany(FeatureEnum.GENERIC_OBJECTS);
    boolean isIntelligentServiceEnabled = params.getCompanyBean().isFeatureEnabledForCompany(FeatureEnum.ENABLE_SMART_SUITE);
    
    boolean saveIsNeeded = false;
    FeatureSet featureSet = params.getCompanyBean().getFeatureSet();
    if(isIntelligentServiceEnabled && !isBPEEnabled) {
      featureSet.add(FeatureEnum.ENABLE_BPE_SERVICE);
      saveIsNeeded = true;
      LOGGER.info("Enable BPE since intelligent service has been enabled. Company Id  : " + params.getCompanyBean().getCompanyId());
    } 
    
    if(isIntelligentServiceEnabled && !isMDFEnabled) {
      featureSet.add(FeatureEnum.ENABLE_BPE_SERVICE);
      saveIsNeeded = true;
      LOGGER.info("Enable MDF since intelligent service has been enabled. Company Id  : " + params.getCompanyBean().getCompanyId());
    } 
    if (saveIsNeeded) {
      try {
        saveFeatureEnumToDB();
      } catch (ProvisionConfigException e) {
        LOGGER
            .info("ProvisionConfigException from saving feature enum to DB :  "
                + e.getMessage());
      }
    }
    return null;
  }
  
  
  private boolean saveFeatureEnumToDB() throws ProvisionConfigException{
    
    LOGGER.info("saveFeatureEnumDB() enter");
    
    boolean result = false;
    
    CompanyBean companyBean = params.getCompanyBean();
    FeatureSet featureSet = companyBean.getFeatureSet();
    
     try {
       companyBean.setFeatureEncrypted(companyBean.generateFeatureKey());
       
       // For each database pool, update the status for the company.
       String poolIds[] = scaHandler.execute(new GetValidDBPoolIds());
       
       for (int i = 0; i < poolIds.length; i++) {
         int status = scaHandler.execute(
                 new ChangeFeatureLicense(companyBean.getCompanyId(), featureSet,
                     companyBean.getFeatureEncrypted(), poolIds[i]));
         if (status != Provisioning.PROVISIONING_OK) {
           LOGGER.error("ProvisioiningEJB returns not ok while saving feature set for company "
               + params.getCompanyName() + " to DB pool " + poolIds[i]);
           throw new ProvisionConfigException("ProvisioiningEJB returns not ok while saving feature set for company "
               + params.getCompanyName() + " to DB pool " + poolIds[i]);
         }
       }

     }catch (NoSuchAlgorithmException e) {
       LOGGER.error("Fail to generate new feature key for company " + params.getCompanyName(), e);
       throw new ProvisionConfigException("Fail to generate new feature key for company " 
       + params.getCompanyName(), e);
     } catch (ServiceApplicationException e) {
       LOGGER.error("Fail to save feature set for company " + params.getCompanyName(), e);
       throw new ProvisionConfigException("Fail to save feature set for company " + params.getCompanyName());
     }
   
     result = true;
     
     return result;
 }
}
