package com.successfactors.hermes.messaging;

import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.successfactors.platform.di.SFContextConstant;

/**
 * Management Configuration
 * @author yyang
 *
 */
@Configuration
@Conditional(ManagementConfiguration.JMXCondition.class)
@ImportResource(ManagementConfiguration.MANAGEMENT_CONFIG_FILE_IN_CLASSPATH)
@org.springframework.context.annotation.Scope(SFContextConstant.SCOPE_EVENT)
public class ManagementConfiguration {

  /** Management configuration file */
  static final String MANAGEMENT_CONFIG_FILE_IN_CLASSPATH = "classpath:platform/management/management-context.xml";

  /** Management startup param */
  static final String PROP_SPRING_JMX = "management.springjmx";

  /**
   * Management switch condition
   *
   */
  static class JMXCondition implements ConfigurationCondition {

    @Override
    public boolean matches(ConditionContext context,
        AnnotatedTypeMetadata metadata) {
      return true;
    }

    @Override
    public ConfigurationPhase getConfigurationPhase() {
      return ConfigurationPhase.PARSE_CONFIGURATION;
    }
  }
}
