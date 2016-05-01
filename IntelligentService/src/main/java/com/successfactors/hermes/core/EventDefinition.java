package com.successfactors.hermes.core;

import java.io.Serializable;
import java.util.Map;

/**
 * Event definition
 * Providing additional information of event other than topic/address
 * 
 * @author yyang
 *
 */
public interface EventDefinition extends Serializable {

  /**
   * get topic which should be same as the one 
   * used in event publishing/subscribing
   * @return topic/address
   */
  String getTopic();

  /**
   * get event label key, used for 
   * being displayed to end users
   * @return label key
   */
  String getLabelKey();

  /**
   * predefined attributes, especially 
   * used in message filtering
   * @return attributes
   */
  Map<String, String> getAttributes();
}
