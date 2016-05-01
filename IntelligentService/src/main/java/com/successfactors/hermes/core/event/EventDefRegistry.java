package com.successfactors.hermes.core.event;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import EventCenter.hermes.core.EventDefinition;
import EventCenter.hermes.util.SEBLog;
import EventCenter.logging.api.Logger;

/**
 * Event definition registry
 * @author yyang
 *
 */
public class EventDefRegistry {

  /** LOGGER */
  private static final Logger LOGGER = SEBLog.getLogger(EventDefRegistry.class);

  /** INSTANCE */
  private static final EventDefRegistry INSTANCE = new EventDefRegistry();

  /** event definitions */
  private Map<String, EventDefinition> definitions = new LinkedHashMap<String, EventDefinition>();

  /**
   * get instance
   * @return instance
   */
  public static EventDefRegistry getInstance() {
    return INSTANCE;
  }

  /**
   * register event def
   * @param eventDef eventDef
   */
  public void resiger(EventDefinition eventDef) {
    if (hasDefinition(eventDef.getTopic())) {
      LOGGER.warn(String.format(
          "[%s] has already been registered, skip registering!", eventDef.getTopic()));
    }
    definitions.put(eventDef.getTopic(), eventDef);
  }

  /**
   * has definition for specific topic
   * @param topic topic
   * @return true / false
   */
  public boolean hasDefinition(String topic) {
    return definitions.containsKey(topic);
  }

  /** reset */
  public void reset() {
    definitions.clear();
  }

  /**
   * get definition by topic
   * @param topic topic
   * @return event definition
   */
  public EventDefinition getDefinition(String topic) {
    return definitions.get(topic);
  }

  /**
   * get definitions
   * @return event definitions
   */
  public List<EventDefinition> getDefinitions() {
    return new ArrayList<EventDefinition>(definitions.values());
  }

  /** invisible constructor */
  private EventDefRegistry() {
  }
}
