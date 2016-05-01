package com.successfactors.hermes.impl.hornetq;

import com.successfactors.hermes.engine.QOSQueueManager;
import com.successfactors.hermes.engine.SubscriptionManager;
import com.successfactors.hermes.engine.ProviderFactory;
import com.successfactors.hermes.engine.ProviderFactoryCreator.FactoryType;


/**
 * Provides underlining implementation of managers.
 * @author ddiodati
 *
 */
public class HQProviderFactoryImpl implements ProviderFactory {
  
  /** TODO dynamically look up impl in property file.
   */
  
  private FactoryType type = FactoryType.server;
  
  /**
   * Create an instance of the provider.
   * @param type The factory type.
   */
  public HQProviderFactoryImpl(FactoryType type) {
    this.type = type;
    
    
    qm = new HQQOSQueueManagerImpl();
    sm = new HQSubscriptionManager(type);
    
  }
  
 
  private QOSQueueManager qm = null;
  private SubscriptionManager sm = null;



  /**
   * 
   * {@inheritDoc}.
   */
  public SubscriptionManager getSubscriptionManager() {
    if(type == FactoryType.server) {
      return sm;
    }
    
    return null;
  }
  
  /**
   * 
   * {@inheritDoc}.
   */
  public QOSQueueManager getQueueManager() {
    if(type == FactoryType.server) {
      return qm;
    }
    
    return null;
  }
  
}
