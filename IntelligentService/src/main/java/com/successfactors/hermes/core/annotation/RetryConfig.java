package com.successfactors.hermes.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Retry configuration
 * this will override global retry configuration
 * it only supports dedicated topic
 * wildcard topic is not supported
 * @author yyang
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface RetryConfig {

  /**
   * indicate whether to use global conf or not
   * @return
   */
  boolean useGlobalConf() default false;

  /**
   * redeliveryDelay, default is 10 seconds
   * @return redeliveryDelay
   */
  long redeliveryDelay() default 10000L;

  /**
   * redeliveryDelayMultiplier, default is 1x
   * @return redeliveryDelayMultiplier
   */
  double redeliveryDelayMultiplier() default 1.0D;

  /**
   * maxRedeliveryDelay, default is 60 seconds
   * @return maxRedeliveryDelay
   */
  long maxRedeliveryDelay() default 60000L;

  /**
   * maxDeliveryAttempts, default is 3 deliveries in total
   * @return maxDeliveryAttempts
   */
  int maxDeliveryAttempts() default 3;
}
