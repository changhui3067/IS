package com.successfactors.hermes.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import EventCenter.hermes.bean.EventDefinitionBean;
import EventCenter.hermes.core.annotation.EventAttribute;
import EventCenter.hermes.core.annotation.EventDefinition;
import EventCenter.hermes.core.annotation.RetryConfig;
import EventCenter.hermes.core.event.EventDefRegistry;
import com.successfactors.hermes.engine.ProviderFactoryCreator;
import com.successfactors.hermes.engine.QOSQueueManager;
import com.successfactors.hermes.engine.ProviderFactoryCreator.FactoryType;
import EventCenter.hermes.util.ReflectionUtil;
import EventCenter.hermes.util.SEBLog;
import com.successfactors.logging.api.Logger;

/**
 * Event definition provider registry
 * @author yyang
 *
 */
public class EventDefRegistryProvider implements RegistryProvider {

  /** LOGGER */
  private static final Logger LOGGER = SEBLog.getLogger(EventDefRegistryProvider.class);

  /** event definition registry */
  private EventDefRegistry registry;

  /** retry configurations */
  private Map<String, RetryConfig> retryConfigurations;

  @Override
  public void startInitialization() {
    registry = EventDefRegistry.getInstance();
    retryConfigurations = new HashMap<String, RetryConfig>();
  }

  @Override
  public void endInitialization() {
    QOSQueueManager queueManager =
        ProviderFactoryCreator.getFactoryInstance(FactoryType.server).getQueueManager();
    queueManager.manageRetryConfigurations(retryConfigurations);
  }

  @Override
  public void register(Class clazz) {
    EventDefinition eventDef = ReflectionUtil.getSpecifiedAnnotation(
        clazz, EventDefinition.class);
    if (eventDef != null) {
      EventDefinitionBean generalEventDef = new EventDefinitionBean();
      generalEventDef.setTopic(eventDef.topic());
      generalEventDef.setLabelKey(eventDef.labelKey());
      if (eventDef.attributes() != null) {
        Map<String, String> eventAttrs = new LinkedHashMap<String, String>();
        for (EventAttribute eventAttr : eventDef.attributes()) {
          eventAttrs.put(eventAttr.name(), eventAttr.value());
        }
        generalEventDef.setAttributes(eventAttrs);
      }
      registry.resiger(generalEventDef);
      
      if (!eventDef.retryConfig().useGlobalConf()) {
        retryConfigurations.put(eventDef.topic(), eventDef.retryConfig());
      }
    }
  }

}
