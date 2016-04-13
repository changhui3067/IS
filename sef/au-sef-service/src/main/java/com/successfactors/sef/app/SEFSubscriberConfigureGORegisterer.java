package com.successfactors.sef.app;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.successfactors.genericobject.bean.model.impl.AbstractMDFEntityRegistry;
import com.successfactors.legacy.bean.provisioning.FeatureEnum;
import com.successfactors.platform.bean.ParamBean;
import com.successfactors.platform.di.SFContextConstant;
import com.successfactors.sef.bean.genericobject.SEFSubscriberConfiguration;
/**
 * This class registers Subscriber Configuration MDF Entities.
 **/

@Name("sefSubscriberConfigureGORegisterer")
@javax.inject.Named("sefSubscriberConfigureGORegisterer")
@Scope(ScopeType.EVENT)
@org.springframework.context.annotation.Scope(SFContextConstant.SCOPE_EVENT)
public class SEFSubscriberConfigureGORegisterer extends
    AbstractMDFEntityRegistry {

  /** params */
  @Inject
  @In
  protected ParamBean params;

  /** MDF Entities */
  private static final Class[] MDF_ENTITIES = { 
    SEFSubscriberConfiguration.class
    };

  /** {@inheritDoc} */
  @Override
  public List<Class> getEntitiesToBeRegistered() {
    return Arrays.asList(MDF_ENTITIES);
  }

  /** {@inheritDoc} */
  @Override
  public boolean isFeatureEnabled() {
    // return true;
    return params.isFeatureExist(FeatureEnum.ENABLE_SMART_SUITE);
  }
}
