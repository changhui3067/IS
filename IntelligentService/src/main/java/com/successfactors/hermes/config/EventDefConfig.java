package com.successfactors.hermes.config;

import java.util.Iterator;
import java.util.Set;

import EventCenter.hermes.core.annotation.EventDefinition;
import EventCenter.hermes.util.SEBLog;
import com.successfactors.logging.api.Logger;
import com.successfactors.xi.util.resource.AnnotationIndex;

/**
 * Config all event definitions, adding it to eventdef registry
 * @author yyang
 *
 */
public class EventDefConfig {

  /** LOGGER */
  private static final Logger LOGGER = SEBLog.getLogger(EventDefConfig.class);

  /** registry provider */
  private RegistryProvider registryProvider;

  /**
   * constructor
   * @param registryProvider registryProvider
   */
  public EventDefConfig(RegistryProvider registryProvider) {
    this.registryProvider = registryProvider;
  }

  /** loading event definitions */
  public void configure() {
    Set<Class<Object>> annotatedClasses = AnnotationIndex.ALL
        .findClassesWithAnnotation(EventDefinition.class, Thread
            .currentThread().getContextClassLoader());
    registryProvider.startInitialization();
    Iterator<Class<Object>> annotatedIte = annotatedClasses.iterator();
    while (annotatedIte.hasNext()) {
      Class<Object> annotatedClass = annotatedIte.next();
      registryProvider.register(annotatedClass);
    }
    registryProvider.endInitialization();
  }
}
