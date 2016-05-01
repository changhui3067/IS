package com.successfactors.hermes.impl.hornetq;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.successfactors.hermes.engine.SubscriberInvoker;

/**
 * Manageable Subscriber Container
 * 
 * @author yyang
 *
 */
@ManagedResource(description = "Subscriber container")
public class ManageableSubscriberContainer extends SubscriberContainerImpl {

  /**
   * Constructor of container
   * @param address address
   * @param queue queue
   * @param subscriberName subscriberName
   * @param filter filter
   */
  public ManageableSubscriberContainer(String address, String queue,
      String subscriberName, String filter) {
    super(address, queue, subscriberName, filter);
  }

  @ManagedAttribute(description = "Consumer idle invocation limit")
  public int getConsumerIdleInvocationLimit() {
    return getIdleInvocationLimit();
  }

  @ManagedAttribute(description = "Minimum concurrent consumers")
  public int getMinConsumerCount() {
    return getConcurrentConsumers();
  }

  @ManagedAttribute(description = "Maximum concurrent consumers")
  public int getMaxConsumerCount() {
    return getMaxConcurrentConsumers();
  }

  @ManagedAttribute(description = "Active consumers")
  public int getActiveConsumerCount() {
    return getActiveInvokerCount();
  }

  @ManagedAttribute(description = "Message receive timeout")
  public long getMessageReceiveTimeout() {
    return getReceiveTimeout();
  }

  @ManagedOperation(description = "Set maximum concurrent consumers")
  @ManagedOperationParameters({ @ManagedOperationParameter(
      name = "maxConsumers", description = "New max concurrent consumers") })
  public void setMaxConsumerCount(int maxConsumers) {
    setMaxConcurrentConsumers(maxConsumers);
  }

  @ManagedOperation(description = "Set message receive timeout")
  @ManagedOperationParameters({ @ManagedOperationParameter(
      name = "receiveTimeout", description = "Message receive timeout") })
  public void setMessageReceiveTimeout(long receiveTimeout) {
    setReceiveTimeout(receiveTimeout);
  }

  @ManagedOperation(description = "List active consumer status")
  public String listActiveConsumerStatus() {
    StringBuilder statusBuilder = new StringBuilder();
    for (SubscriberInvoker invoker : getScheduledInvokers()) {
      statusBuilder.append(invoker).append("\n");
    }
    return statusBuilder.toString();
  }

}
