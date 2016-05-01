package com.successfactors.hermes.messaging;

import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Messaging - Spring JMS on HornetQ Configuration
 * @author yyang
 *
 */
@Configuration
@Conditional(MessagingConfiguration.HornetQCondition.class)
@ImportResource(MessagingConfiguration.MESSAGING_CONFIG_FILE_IN_CLASSPATH)
public class MessagingConfiguration {

  /** Messaging configuration file */
  static final String MESSAGING_CONFIG_FILE_IN_CLASSPATH = "classpath:platform/messaging/messaging-context.xml";

  /** Messaging startup param */
  static final String PROP_SPRINGJMS_ON_HORNETQ = "messaging.springjms.hornetq";

  /**
   * Messaging implementation switch condition
   *
   */
  static class HornetQCondition implements ConfigurationCondition {
    @Override
    public boolean matches(ConditionContext context,
        AnnotatedTypeMetadata metadata) {
      return Boolean.valueOf(System.getProperty(PROP_SPRINGJMS_ON_HORNETQ));
    }

    @Override
    public ConfigurationPhase getConfigurationPhase() {
      return ConfigurationPhase.PARSE_CONFIGURATION;
    }
  }
}
