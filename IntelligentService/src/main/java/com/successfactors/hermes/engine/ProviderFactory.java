package com.successfactors.hermes.engine;


/**
 * Provides underlining implementation of managers.
 * @author ddiodati
 *
 */
public interface ProviderFactory {
  
  /**
   * Returns the subscription manager implementation.
   * @return Subscriptionmanager
   */
  SubscriptionManager getSubscriptionManager();
  
  /**
   * Returns the Quality of Service Queue manager.
   * @return QOSQueueManager.
   */
  QOSQueueManager getQueueManager();
  
}
