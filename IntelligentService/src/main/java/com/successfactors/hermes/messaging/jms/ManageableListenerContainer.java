package com.successfactors.hermes.messaging.jms;

import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Manageable listener container
 * @author yyang
 *
 */
@ManagedResource(description = "Message listener container")
public class ManageableListenerContainer extends
    DefaultMessageListenerContainer {

  @ManagedAttribute(description = "Consumer keeps alive milliseconds")
  public long getKeepAliveMillis() {
    return getIdleTaskExecutionLimit();
  }

  @ManagedAttribute(description = "Minimum concurrent consumer size")
  public int getMinConsumerSize() {
    return getConcurrentConsumers();
  }

  @ManagedAttribute(description = "Maximum concurrent consumer size")
  public int getMaxConsumerSize() {
    return getMaxConcurrentConsumers();
  }

  @ManagedAttribute(description = "Active consumer size")
  public int getActiveConsumerSize() {
    return getActiveConsumerCount();
  }

  @ManagedOperation(description = "Set maximum concurrent consumer size")
  @ManagedOperationParameters({ @ManagedOperationParameter(
      name = "maxConsumerSize", description = "New max concurrent consumer size") })
  public void setMaxConsumerSize(int maxConsumerSize) {
    setMaxConcurrentConsumers(maxConsumerSize);
  }
}
