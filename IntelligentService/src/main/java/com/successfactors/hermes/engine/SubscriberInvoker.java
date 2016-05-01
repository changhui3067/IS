package com.successfactors.hermes.engine;

import java.util.UUID;

/**
 * Subscriber Invoker
 * Asynchronous runnable task to be executed for event receiving and processing
 * 
 * Being responsible for maintaining the execution template of event receiving
 * and event distribution to hermes subscriber for processing.
 * 
 * Being able to collect and provide execution statistics to container.
 * 
 * @author yyang
 *
 */
public interface SubscriberInvoker extends Runnable {

  /** sleep period on fatal */
  static final int SLEEP_PERIOD_ON_FATAL_ERROR = 10;

  /**
   * Return the unique identifier of underlying invoker
   * @return UUID
   */
  UUID getId();

  /**
   * Check whether event has been received or not during
   * current thread processing, idle = true while received
   * otherwise idle = false
   * @return true / false
   */
  boolean isIdle();

  /**
   * Set idle status, normally being invoked by the container
   * @see {@link com.successfactors.hermes.engine.SubscriberContainer#onMessageReceived(SubscriberInvoker)}
   * @see {@link com.successfactors.hermes.engine.SubscriberContainer#noMessageReceived(SubscriberInvoker)}
   * @param idle idle
   */
  void setIdle(boolean idle);

  /**
   * Used in the scaling strategy by the container
   * @return the total idle invocation count
   */
  int getIdleInvocationCount();

  /**
   * cleanup the cached resources
   */
  void cleanup();

}
