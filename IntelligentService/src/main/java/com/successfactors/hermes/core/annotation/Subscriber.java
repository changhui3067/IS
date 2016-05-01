package com.successfactors.hermes.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to define a Subscriber of a topic.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Subscriber {

  /**
   * Topic of the Subscriber
   */
  String topic();

  /**
   * Once specified, annotated subscriber
   * will ONLY be activated on servers that
   * configured with corresponding type string
   * 
   * hermes.subscriber.activate=${type}
   * 
   * @return subscriber type
   */
  String type() default "";

  /**
   * Meta data of the subscriber
   */
  MetaData metaData() default @MetaData(module = "", descI18n = "", id = "", impactArea = "", nameI18n = "");

  /**
   * max concurrent consumer count after subscriber registration
   * can be changed further through JMX console
   * 
   * this is configured on a subscriber server basis
   * 
   */
  int maxConcurrentConsumers() default 1;
}
