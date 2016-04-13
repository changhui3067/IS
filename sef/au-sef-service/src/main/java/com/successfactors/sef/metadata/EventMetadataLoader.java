package com.successfactors.sef.metadata;


import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;

import com.successfactors.platform.di.SFContextConstant;
import com.successfactors.platform.di.SFContextUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Manish Manwani
 * Date: 7/9/15
 * Time: 9:40 AM
 */
@Name("eventMetadataLoader")
@javax.inject.Named("eventMetadataLoader")
@Scope(ScopeType.APPLICATION)
@org.springframework.context.annotation.Scope(SFContextConstant.SCOPE_APPLICATION)
@Startup
public class EventMetadataLoader {
  @Create
  public void init() {
    EventMetadataProvider.getEventTypes();
  }

  /**
   * It's for Seam Create annotation migration
   */
  @javax.annotation.PostConstruct
  public void initPostConstruct() {
    Boolean isSpringEnabled = SFContextUtils.isSpringEnabled();
    if (isSpringEnabled) {
      init();
    }
  }

}
