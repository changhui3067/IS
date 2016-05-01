package com.successfactors.hermes.impl.hornetq;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.hornetq.api.core.HornetQException;
import org.hornetq.api.core.client.ClientMessage;

import com.successfactors.hermes.engine.SubscriberContainer;
import com.successfactors.hermes.engine.SubscriberInvoker;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;

/**
 * Subscriber Invoker Implementation
 * 
 * @author yyang
 *
 */
public class SubscriberInvokerImpl implements SubscriberInvoker {

  /** LOGGER */
  private static final Logger LOGGER = LogManager.getLogger(SubscriberInvokerImpl.class);

  /** Invoker ID */
  private UUID id;

  /** Indicate whether invoker is idle or not during current thread processing */
  private volatile boolean idle = true;

  /** Invoker total idle count */
  private volatile int idleInvocationCount = 0;

  /** Subscriber container reference */
  private SubscriberContainer container;

  /** start milliseconds of current round of invocation */
  private long start;

  /**
   * Subscriber instance, the instance will be cached as long as 
   * the invoker is still active (means the session and consumer
   * will be cached as well from HornetQ perspective). But will be
   * @see #cleanup if being removed from container or encountering
   * fatal error
   * 
   */
  private HQSubscriber subscriber;

  /**
   * Constructor of invoker
   * @param container container
   * @param address address
   * @param queue queue
   * @param subscriberName subscriberName
   * @param filter filter
   */
  public SubscriberInvokerImpl(SubscriberContainer container, String address, String queue,
      String subscriberName, String filter) {
    this.container = container;
    this.id = UUID.randomUUID();
    this.subscriber = new HQPullingSubscriber(address, queue, subscriberName, filter);
  }

  @Override
  public void run() {
    container.preInvokerProcessing(this);
    do {
      boolean messageReceived = false;
      start = System.currentTimeMillis();
      try {
        messageReceived = doInvokeSubscriber();
      } catch (Throwable ex) {
        LOGGER.error("Unexpected exception when invoker subscriber.", ex);
        cleanup();
        sleepBeforeNextInvocation();
      } finally {
        if (!messageReceived) {
          idleInvocationCount++;
        } else {
          idleInvocationCount = 0;
        }
      }
    } while (container.shouldProceed(this));
    container.postInvokerProcssing(this);
  }

  /**
   * Make current thread sleep for a while whenever meeting
   * with error (for EX: failed to connect to HornetQ)
   */
  private void sleepBeforeNextInvocation() {
    try {
      TimeUnit.SECONDS.sleep(SLEEP_PERIOD_ON_FATAL_ERROR);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private boolean doInvokeSubscriber() throws HornetQException {
    ClientMessage message = subscriber.receive(container.getReceiveTimeout());
    if (message != null) {
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace(String.format("On message received of %s", this));
      }
      container.onMessageReceived(this);
      subscriber.onMessage(message);
      
      // Indicate that a message has been received.
      return true;
    } else {
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace(String.format("No message received of %s", this));
      }
      container.noMessageReceived(this);
      
      // Indicate that no message has been received.
      return false;
    }
  }

  @Override
  public void cleanup() {
    if (subscriber != null) {
      try {
        subscriber.closeConsumer();
      } catch (HornetQException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
  }

  @Override
  public boolean isIdle() {
    return idle;
  }

  @Override
  public void setIdle(boolean idle) {
    this.idle = idle;
  }

  @Override
  public int getIdleInvocationCount() {
    return idleInvocationCount;
  }

  @Override
  public UUID getId() {
    return id;
  }

  /**
   * getter of start
   * @return the start
   */
  public long getStart() {
    return start;
  }

  @Override
  public String toString() {
    return String.format("Subscriber Inovker ID[%s], idle: %s, timeElapsed: %s, %s", 
        id, isIdle(), System.currentTimeMillis() - getStart(), subscriber);
  }
}
