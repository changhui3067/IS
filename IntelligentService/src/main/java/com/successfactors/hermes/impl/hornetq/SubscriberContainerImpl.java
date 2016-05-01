package com.successfactors.hermes.impl.hornetq;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.core.task.SimpleAsyncTaskExecutor;

import com.successfactors.hermes.engine.SubscriberContainer;
import com.successfactors.hermes.engine.SubscriberInvoker;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;

/**
 * Subscriber Container Implementation
 * @author yyang
 *
 */
public class SubscriberContainerImpl implements SubscriberContainer {

  /** LOGGER */
  private static final Logger LOGGER = LogManager.getLogger(SubscriberContainerImpl.class);

  /** Container lock to control resource access and lifecycle */
  private final ReentrantLock containerLock = new ReentrantLock();

  /** Invoker executor */
  private Executor invokerExecutor;

  /** concurrent consumer count */
  private int concurrentConsumers = DEFAULT_CONCURRENT_CONSUMER_COUNT;

  /** Maximum concurrent consumer count */
  private int maxConcurrentConsumers = DEFAULT_MAX_CONCURRENT_CONSUMER_COUNT;

  /** Idle consumer count limit */
  private int idleConsumerLimit = DEFAULT_IDLE_CONSUMER_COUNT_LIMIT;

  /** Idle invocation limit */
  private int idleInvocationLimit = DEFAULT_IDLE_INVOCATION_COUNT_LIMIT;

  /** receive time out */
  private long receiveTimeout = DEFAULT_MESSAGE_RECEIVE_TIMEOUT;

  /** Scheduled invokers */
  private final Set<SubscriberInvoker> scheduledInvokers = new HashSet<SubscriberInvoker>();

  /** Active invoker count */
  private int activeInvokerCount = 0;

  // Subscriber related properties, better to change to subscriber meta
  /** bounded address/topic */
  private String address;

  /** bounded queue */
  private String queue;

  /** subscriber name */
  private String subscriberName;

  /** filter expression */
  private String filter;

  /**
   * Constructor of container
   * @param address address
   * @param queue queue
   * @param subscriberName subscriberName
   * @param filter filter
   */
  public SubscriberContainerImpl(String address, String queue,
      String subscriberName, String filter) {
    this.address = address;
    this.queue = queue;
    this.subscriberName = subscriberName;
    this.filter = filter;
  }

  @Override
  public void start() {
    if (getInvokerExecutor() == null) {
      invokerExecutor = new SimpleAsyncTaskExecutor(toString());
    }
    containerLock.lock();
    try {
      for (int i = 0; i < concurrentConsumers; i++) {
        scheduleNewInvoker();
      }
    } finally {
      containerLock.unlock();
    }
  }

  @Override
  public void stop() {
  }

  @Override
  public boolean isRunning() {
    return true;
  }

  private void scheduleNewInvoker() {
    SubscriberInvoker invoker = new SubscriberInvokerImpl(this, 
        address, queue, subscriberName, filter);
    LOGGER.info(String.format("Schedule new %s", invoker));
    getInvokerExecutor().execute(invoker);
    scheduledInvokers.add(invoker);
  }

  private void scheduleNewInvokerIfNeeded() {
    containerLock.lock();
    try {
      if (scheduledInvokers.size() < maxConcurrentConsumers &&
          getIdleInvokerCount() < idleConsumerLimit) {
        scheduleNewInvoker();
      }
    } finally {
      containerLock.unlock();
    }
  }

  private void removeInvoker(SubscriberInvoker invoker) {
    containerLock.lock();
    try {
      LOGGER.info(String.format("Removed of %s", invoker));
      // shut down invoker completely
      invoker.cleanup();
      scheduledInvokers.remove(invoker);
    } finally {
      containerLock.unlock();
    }
  }

  private boolean shouldRescheduleInvoker(int idleInvocationCount) {
    boolean superfluous = (idleInvocationCount >= idleInvocationLimit && getIdleInvokerCount() > 1);
    return (scheduledInvokers.size() <= (superfluous ? concurrentConsumers : maxConcurrentConsumers));
  }

  @Override
  public void preInvokerProcessing(SubscriberInvoker invoker) {
    increaseActiveInvoker();
  }

  @Override
  public boolean shouldProceed(SubscriberInvoker invoker) {
    containerLock.lock();
    boolean shouldProceed = true;
    try {
      if (!shouldRescheduleInvoker(invoker.getIdleInvocationCount())) {
        shouldProceed = false;
      }
      return shouldProceed;
    } finally {
      containerLock.unlock();
    }
  }

  @Override
  public void onMessageReceived(SubscriberInvoker invoker) {
    invoker.setIdle(false);
    scheduleNewInvokerIfNeeded();
  }

  @Override
  public void noMessageReceived(SubscriberInvoker invoker) {
    invoker.setIdle(true);
  }

  @Override
  public void postInvokerProcssing(SubscriberInvoker invoker) {
    decreaseActiveInvoker();
    removeInvoker(invoker);
  }

  @Override
  public Executor getInvokerExecutor() {
    return invokerExecutor;
  }

  @Override
  public void setInvokerExecutor(Executor invokerExecutor) {
    this.invokerExecutor = invokerExecutor;
  }

  @Override
  public int getScheduledInvokerCount() {
    containerLock.lock();
    try {
      return scheduledInvokers.size();
    } finally {
      containerLock.unlock();
    }
  }

  /**
   * getter of scheduledInvokers
   * @return the scheduledInvokers
   */
  public Set<SubscriberInvoker> getScheduledInvokers() {
    return scheduledInvokers;
  }

  @Override
  public int getActiveInvokerCount() {
    containerLock.lock();
    try {
      return activeInvokerCount;
    } finally {
      containerLock.unlock();
    }
  }

  private int getIdleInvokerCount() {
    int count = 0;
    for (SubscriberInvoker invoker : scheduledInvokers) {
      if (invoker.isIdle()) {
        count++;
      }
    }
    return count;
  }

  private void increaseActiveInvoker() {
    containerLock.lock();
    try {
      activeInvokerCount++;
    } finally {
      containerLock.unlock();
    }
  }

  private void decreaseActiveInvoker() {
    containerLock.lock();
    try {
      activeInvokerCount--;
    } finally {
      containerLock.unlock();
    }
  }

  @Override
  public int getConcurrentConsumers() {
    containerLock.lock();
    try {
      return concurrentConsumers;
    } finally {
      containerLock.unlock();
    }
  }

  @Override
  public void setConcurrentConsumers(int concurrentConsumers) {
    containerLock.lock();
    try {
      this.concurrentConsumers = concurrentConsumers;
      if (this.maxConcurrentConsumers < concurrentConsumers) {
        this.maxConcurrentConsumers = concurrentConsumers;
      }
    } finally {
      containerLock.unlock();
    }
  }

  @Override
  public int getMaxConcurrentConsumers() {
    containerLock.lock();
    try {
      return maxConcurrentConsumers;
    } finally {
      containerLock.unlock();
    }
  }

  @Override
  public void setMaxConcurrentConsumers(int maxConcurrentConsumers) {
    containerLock.lock();
    try {
      this.maxConcurrentConsumers = (maxConcurrentConsumers > this.concurrentConsumers ? 
          maxConcurrentConsumers : this.concurrentConsumers);
    } finally {
      containerLock.unlock();
    }
  }

  @Override
  public int getIdleConsumerLimit() {
    containerLock.lock();
    try {
      return idleConsumerLimit;
    } finally {
      containerLock.unlock();
    }
  }

  @Override
  public void setIdleConsumerLimit(int idleConsumerLimit) {
    containerLock.lock();
    try {
      this.idleConsumerLimit = idleConsumerLimit;
    } finally {
      containerLock.unlock();
    }
  }

  @Override
  public int getIdleInvocationLimit() {
    containerLock.lock();
    try {
      return idleInvocationLimit;
    } finally {
      containerLock.unlock();
    }
  }

  @Override
  public void setIdleInvocationLimit(int idleInvocationLimit) {
    containerLock.lock();
    try {
      this.idleInvocationLimit = idleInvocationLimit;
    } finally {
      containerLock.unlock();
    }
  }

  @Override
  public long getReceiveTimeout() {
    return receiveTimeout;
  }

  @Override
  public void setReceiveTimeout(long receiveTimeout) {
    this.receiveTimeout = receiveTimeout;
  }

  @Override
  public String toString() {
    return String.format("Subscriber Container[queue_%s]", queue);
  }

}