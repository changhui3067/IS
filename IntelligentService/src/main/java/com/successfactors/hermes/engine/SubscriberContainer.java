package com.successfactors.hermes.engine;

import java.util.concurrent.Executor;

/**
 * Subscriber Container
 * @author yyang
 *
 */
public interface SubscriberContainer {

  /** default concurrent consumer count */
  static final int DEFAULT_CONCURRENT_CONSUMER_COUNT = 1;

  /** default max concurrent consumer count */
  static final int DEFAULT_MAX_CONCURRENT_CONSUMER_COUNT = 1;

  /** default allowed total idle consumer count */
  static final int DEFAULT_IDLE_CONSUMER_COUNT_LIMIT = 1;

  /** default idle invocations limit for single consumer */
  static final int DEFAULT_IDLE_INVOCATION_COUNT_LIMIT = 1;

  /** default message receive timeout */
  static final long DEFAULT_MESSAGE_RECEIVE_TIMEOUT = 30000;

  // Container management API
  void start();

  void stop();

  boolean isRunning();

  // Message processing callbacks
  /**
   * Pre processing callback
   * @param invoker invoker
   */
  void preInvokerProcessing(SubscriberInvoker invoker);

  /**
   * Determine whether an invoker should continue 
   * receiving events or being revoked
   * 
   * @param invoker invoker
   * @return true / false
   */
  boolean shouldProceed(SubscriberInvoker invoker);

  /**
   * Message received callback
   * @param invoker invoker
   */
  void onMessageReceived(SubscriberInvoker invoker);

  /**
   * Message not received callback
   * @param invoker invoker
   */
  void noMessageReceived(SubscriberInvoker invoker);

  /**
   * Post processing callback
   * @param invoker invoker
   */
  void postInvokerProcssing(SubscriberInvoker invoker);

  // Message receiving, scaling, throttling, dispatching policy management API 
  Executor getInvokerExecutor();

  /**
   * Thread pool implementation
   * <p>Default is a {@link org.springframework.core.task.SimpleAsyncTaskExecutor},
   * starting up a number of new threads, according to the specified number
   * of concurrent consumers.
   * 
   * <p>Specify an alternative {@code Executor} for integration with an existing
   * thread pool. 
   * 
   * @see #setConcurrentConsumers
   * @see org.springframework.core.task.SimpleAsyncTaskExecutor
   * 
   * @param invokerExecutor invokerExecutor
   */
  void setInvokerExecutor(Executor invokerExecutor);

  int getScheduledInvokerCount();

  int getActiveInvokerCount();

  int getConcurrentConsumers();

  /**
   * Specify the number of concurrent consumers to create. Default is 1.
   * <p>Specifying a higher value for this setting will increase the standard
   * level of scheduled concurrent consumers at runtime: This is effectively
   * the minimum number of concurrent consumers which will be scheduled
   * at any given time. This is a static setting; for dynamic scaling,
   * consider specifying the "maxConcurrentConsumers" setting instead.
   * 
   * <p><b>This setting can be modified at runtime, for example through JMX.</b>
   * 
   * Be cautious making the change to subscribers listen to wild carded topics!!!
   * 
   * @see #setMaxConcurrentConsumers
   */
  void setConcurrentConsumers(int concurrentConsumers);

  int getMaxConcurrentConsumers();

  /**
   * Specify the maximum number of concurrent consumers to create. Default is 1.
   * <p>If this setting is higher than "concurrentConsumers", the container
   * will dynamically schedule new consumers at runtime, provided that enough
   * incoming messages are encountered. Once the load goes down again, the number of
   * consumers will be reduced to the standard level ("concurrentConsumers") again.
   * 
   * <p><b>This setting can be modified at runtime, for example through JMX.</b>
   * @see #setConcurrentConsumers
   * 
   * Be cautious making the change to subscribers listen to wild carded topics!!!
   * 
   * @param maxConcurrentConsumers maxConcurrentConsumers
   */
  void setMaxConcurrentConsumers(int maxConcurrentConsumers);

  int getIdleConsumerLimit();

  /**
   * Specify the limit for the number of consumers that are allowed to be idle
   * at any given time.
   * <p>This limit is used for determining if a new invoker should be created. 
   * Increasing the limit causes invokers to be created more aggressively. 
   * This can be useful to ramp up the number of invokers faster.
   * <p>The default is 1, only scheduling a new invoker (which is likely to
   * be idle initially) if none of the existing invokers is currently idle.
   * 
   * @param idleConsumerLimit idleConsumerLimit
   */
  void setIdleConsumerLimit(int idleConsumerLimit);

  int getIdleInvocationLimit();

  /**
   * Specify the limit for idle executions of a consumer invoker, not having
   * received any message within its execution. If this limit is reached,
   * the invoker will shut down and leave receiving to other executing invokers.
   * 
   * <p>The default is 1, closing idle resources early once a task didn't
   * receive a message. This applies to dynamic scheduling only; see the
   * {@link #setMaxConcurrentConsumers "maxConcurrentConsumers"} setting.
   * The minimum number of consumers
   * (see {@link #setConcurrentConsumers "concurrentConsumers"})
   * will be kept around until shutdown in any case.
   * 
   * <p><b>This setting can be modified at runtime, for example through JMX.</b>
   * 
   * @param idleInvocationLimit idleInvocationLimit
   */
  void setIdleInvocationLimit(int idleInvocationLimit);

  long getReceiveTimeout();

  /**
   * Specify the message receive timeout.
   * While receiveTimeout < 0, the receive operation will be blocked 
   * until the there is incoming message in the queue
   * While recieveTimeout >= 0, the receive operation will be blocked
   * for the specified the time period, if not receiving any message, 
   * it will return with null
   * 
   * default value is @see {@value #DEFAULT_MESSAGE_RECEIVE_TIMEOUT}
   *  
   * @param receiveTimeout receiveTimeout
   */
  void setReceiveTimeout(long receiveTimeout);
}
