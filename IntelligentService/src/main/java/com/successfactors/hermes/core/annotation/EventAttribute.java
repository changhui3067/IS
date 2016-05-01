package com.successfactors.hermes.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Event attribute
 * Providing name:value pair attribute of 
 * certain event definition
 * 
 * @author yyang
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface EventAttribute {

  /**
   * attribute name
   * @return name
   */
  String name();

  /**
   * attribute value
   * @return value
   */
  String value();
}
