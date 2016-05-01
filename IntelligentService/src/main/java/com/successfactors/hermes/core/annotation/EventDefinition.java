package com.successfactors.hermes.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Event definition
 * Providing additional information of event other than topic/address
 * 
 * @author yyang
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface EventDefinition {

  /**
   * topic name which should be same as the one 
   * used in event publishing/subscribing
   * @return topic/address
   */
  String topic();

  /**
   * event label key, used for 
   * being displayed to end users
   * @return label key
   */
  String labelKey();

  /**
   * predefined attributes, especially 
   * used in message filtering
   * @return attributes
   */
  EventAttribute[] attributes();

  /**
   * topic specific retry configuration
   * @return retryConfig
   */
  RetryConfig retryConfig() default @RetryConfig(useGlobalConf = true);
}
