package com.successfactors.hermes.engine;


import java.util.Map;

import EventCenter.hermes.core.SFEvent;
import EventCenter.hermes.core.Topic;
import EventCenter.hermes.core.annotation.RetryConfig;

/**
 * The Quality of Service manager handles returning the right queue for a given topic or event.
 * Topic is a publish/subscribe queue and event would be for a point to point queue.
 * @author ddiodati
 *
 */
public interface QOSQueueManager {
 

  /**
   * Returns a publish/subscribe queue.
   * @param t topic to lookup.
   * @return the queue for publishing.
   */
  PublishQueue getQueueInstance(Topic t);
  
  /**
   * Returns a point to point queue.
   * @param e The event with the ptpname.
   * @return The queue for publishing.
   */
  PublishQueue getQueueInstance(SFEvent e);

  /**
   * Manage address/topic retry configurations
   * 
   * @param retryConfigurations retryConfigurations
   */
  void manageRetryConfigurations(Map<String, RetryConfig> retryConfigurations);
}
