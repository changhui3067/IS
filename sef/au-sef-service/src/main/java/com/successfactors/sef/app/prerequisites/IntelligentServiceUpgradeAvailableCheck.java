package com.successfactors.sef.app.prerequisites;

import javax.inject.Inject;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.successfactors.db.util.SQLUtil;
import com.successfactors.legacy.bean.provisioning.FeatureEnum;
import com.successfactors.platform.bean.ParamBean;
import com.successfactors.platform.di.SFContextConstant;
import com.successfactors.upgradecenter.core.UpgradeAvailableCheck;

@Name("intelligentServiceUpgradeAvailableCheck")
@javax.inject.Named("intelligentServiceUpgradeAvailableCheck")
@Scope(ScopeType.EVENT)
@org.springframework.context.annotation.Scope(SFContextConstant.SCOPE_EVENT)
public class IntelligentServiceUpgradeAvailableCheck implements UpgradeAvailableCheck {

  /** Service handler. */
   @Inject
   @In
   protected ParamBean params;
  
  @Override
  public boolean isUpgraded() {
    return params.isFeatureExist(FeatureEnum.ENABLE_SMART_SUITE);
  }


  @Override
  public boolean checkPreReqFeatures() {
    return SQLUtil.isOracle();      
  }

}
