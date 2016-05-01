package com.successfactors.hermes.messaging.jms;

import static com.successfactors.hermes.messaging.jms.MessageDrivenPojo.NONDURABLE;
import static com.successfactors.hermes.messaging.jms.MessageDrivenPojo.QUEUE;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Messaging destination configuration
 * @author yyang
 *
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface MessagingDestConfig {

  /** destination type */
  String destType() default QUEUE;

  /** destination durablility */
  String duribility() default NONDURABLE;

  /** destination / address */
  String destination();
}
