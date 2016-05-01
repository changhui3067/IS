package com.successfactors.hermes.bean;

import java.util.LinkedHashMap;
import java.util.Map;

import EventCenter.hermes.core.EventDefinition;

/**
 * Event definition bean
 * used for JSON marshalling
 * @author yyang
 *
 */
public class EventDefinitionBean implements EventDefinition {

  /** topic */
  private String topic;

  /** label key */
  private String labelKey;

  /** attributes */
  private Map<String, String> attributes = new LinkedHashMap<String, String>();

  /** default contructor */
  public EventDefinitionBean() {
  }

  /**
   * get topic
   * @return the topic
   */
  public String getTopic() {
    return topic;
  }

  /**
   * set topic
   * @param topic the topic to set
   */
  public void setTopic(String topic) {
    this.topic = topic;
  }

  /**
   * get label key
   * @return the labelKey
   */
  public String getLabelKey() {
    return labelKey;
  }

  /**
   * set label key
   * @param labelKey the labelKey to set
   */
  public void setLabelKey(String labelKey) {
    this.labelKey = labelKey;
  }

  /**
   * get attributes
   * @return the attributes
   */
  public Map<String, String> getAttributes() {
    return attributes;
  }

  /**
   * set attributes
   * @param attributes the attributes to set
   */
  public void setAttributes(Map<String, String> attributes) {
    this.attributes = attributes;
  }

}
