package com.successfactors.hermes.messaging;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Messaging client configuration
 * @author yyang
 *
 */
@Configuration
@Conditional(MessagingConfiguration.HornetQCondition.class)
@ImportResource(MessagingClientConfiguration.MESSAGING_CLIENT_CONFIG_FILE_IN_CLASSPATH)
public class MessagingClientConfiguration {

  /** Messaging client configuration file */
  static final String MESSAGING_CLIENT_CONFIG_FILE_IN_CLASSPATH 
    = "classpath:platform/messaging/messaging-client-context.xml";
}
