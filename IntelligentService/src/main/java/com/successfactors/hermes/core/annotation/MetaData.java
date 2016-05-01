package com.successfactors.hermes.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The meta data of the subscriber.
 * @author wkliu
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface MetaData {

  /**
   * The identity of the subscriber.
   * @return identity
   */
  String id();

  /**
   * The name of the subscriber. 
   * @return i18n key
   */
  String nameI18n();

  /**
   * The description of the subscriber.
   * @return i18n key
   */
  String descI18n();

  /**
   * The impact area of the subscriber.
   * @return
   */
  String[] impactArea();

  /**
   * The module of the subscriber.
   * @return String
   */
  String module();

  /**
   * Indicate whether the subscriber is a smart suite subscriber.
   * @return boolean
   */
  boolean isSmartSub() default false;

  /**
   * Indicate whether the subscriber can be disabled.
   * @return boolean
   */
  boolean canDisable() default true;

  /**
   * The required feature for the subscriber.
   * @return the required feature array
   */
  String[] featureEnableCheckFlag() default "";
}
