package com.successfactors.hermes.core;


import java.security.Principal;

import com.successfactors.db.CompanyDatabaseContext;

import com.successfactors.hermes.engine.ServiceEventBusEngine;
import com.successfactors.platform.di.SFContextConstant;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import javax.inject.Inject;


/**
 * Seam factory wrapper class.
 * 
 * @author ddiodati
 *
 */
@Name("ServiceEventBusFactory")
@javax.inject.Named("ServiceEventBusFactory")
@Scope(ScopeType.STATELESS)
@org.springframework.context.annotation.Scope(SFContextConstant.SCOPE_EVENT)
public class ServiceEventBusFactory {

  @Inject
  @In
  private Principal params;

  @Inject
  @In
  private CompanyDatabaseContext company;

  /**
   * Returns an instance of the service event bus. Can be used directly for older code
   * but the preferred was is to just use seam and inject in the serviceEventBus object.
   * 
   * @return instance of the service event bus.
  */
  @Factory(value="serviceEventBus", scope=ScopeType.EVENT, autoCreate=true)
  @org.springframework.context.annotation.Scope(SFContextConstant.SCOPE_EVENT)
  @org.springframework.context.annotation.Bean(name = "serviceEventBus")
  public ServiceEventBus getInstance()  {
    return new ServiceEventBusEngine(company.getCompanyId(), params.getName());
  }

}
