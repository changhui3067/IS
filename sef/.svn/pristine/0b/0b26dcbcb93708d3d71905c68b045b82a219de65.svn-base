package com.successfactors.sef.metadata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class EventMetadata {

  public enum FieldDataType {

    /** field data type */
    INTEGER, STRING, DATE, DATETIMEOFFSET, LONG, BOOLEAN ;

    /**
     * Convert {@link FieldDataType} to json string
     * 
     * @return json string
     */
    @JsonValue
    public String toJsonString() {
      return this.name().toLowerCase();
    }

    /**
     * Convert json string to {@link FieldDataType}
     * 
     * @param str
     *          json string
     * @return instance of {@link FieldDataType}
     */
    @JsonCreator
    public static FieldDataType fromJsonString(String str) {
      return FieldDataType.valueOf(str.toUpperCase());
    }
  }

  public enum SourceAreaType {

    /** source area type */
    MODULE_ECT, MODULE_GM, MODULE_LMS, MODULE_CALIBRATION, MODULE_TFT, MODULE_HP, MODULE_ANALYTICS, MODULE_OB, MODULE_SM, MODULE_JAM, MODULE_PMTFT, MODULE_COMP, MODULE_VP, MODULE_EP, MODULE_RCM, MODULE_ORG,MODULE_CDP, MODULE_QC

  }

  public static class EntityMetadata {

    private String name;
    private FieldDataType type;
    private String description;
    private String descriptionMessageKey;

    /**
     * Get name
     * 
     * @return the name
     */
    public String getName() {
      return name;
    }

    /**
     * Set name
     * 
     * @param name
     *          the name to set
     */
    public void setName(String name) {
      this.name = name;
    }

    /**
     * Get type
     * 
     * @return the type
     */
    public FieldDataType getType() {
      return type;
    }

    /**
     * Set type
     * 
     * @param type
     *          the type to set
     */
    public void setType(FieldDataType type) {
      this.type = type;
    }

    /**
     * Get description
     * 
     * @return the description
     */
    public String getDescription() {
      return description;
    }

    /**
     * Set description
     * 
     * @param description
     *          the description to set
     */
    public void setDescription(String description) {
      this.description = description;
    }

    /**
     * Get description message key
     * 
     * @return the descriptionMessageKey
     */
    public String getDescriptionMessageKey() {
      return descriptionMessageKey;
    }

    /**
     * Set description message key
     * 
     * @param descriptionMessageKey
     *          the descriptionMessageKey to set
     */
    public void setDescriptionMessageKey(String descriptionMessageKey) {
      this.descriptionMessageKey = descriptionMessageKey;
    }
  }

  public static class ParamMetadata {

    private String name;
    private FieldDataType type;
    private String description;
    private String descriptionMessageKey;
    private boolean hasValueAlways;

    /**
     * Get name
     * 
     * @return the name
     */
    public String getName() {
      return name;
    }

    /**
     * Set name
     * 
     * @param name
     *          the name to set
     */
    public void setName(String name) {
      this.name = name;
    }

    /**
     * Get type
     * 
     * @return the type
     */
    public FieldDataType getType() {
      return type;
    }

    /**
     * Set type
     * 
     * @param type
     *          the type to set
     */
    public void setType(FieldDataType type) {
      this.type = type;
    }

    /**
     * Get description
     * 
     * @return the description
     */
    public String getDescription() {
      return description;
    }

    /**
     * Set description
     * 
     * @param description
     *          the description to set
     */
    public void setDescription(String description) {
      this.description = description;
    }

    /**
     * Get description message key
     * 
     * @return the descriptionMessageKey
     */
    public String getDescriptionMessageKey() {
      return descriptionMessageKey;
    }

    /**
     * Set description message key
     * 
     * @param descriptionMessageKey
     *          the descriptionMessageKey to set
     */
    public void setDescriptionMessageKey(String descriptionMessageKey) {
      this.descriptionMessageKey = descriptionMessageKey;
    }

    /**
     * Get has values always
     * 
     * @return the hasValueAlways
     */
    public boolean isHasValueAlways() {
      return hasValueAlways;
    }

    /**
     * Set has values always
     * 
     * @param hasValueAlways
     *          the hasValueAlways to set
     */
    public void setHasValueAlways(boolean hasValueAlways) {
      this.hasValueAlways = hasValueAlways;
    }
  }

  private String type;
  private String localizedTypeKey;
  private String descriptionMessageKey;
  private String entity;
  private SourceAreaType sourceArea;
  private String topic;
  private String ruleCode;
  private String featureEnum;

  private boolean effectiveDated;
  private boolean externallyAllowed;
  private boolean smartSuiteEvent;
  private boolean bulkEventType;
  private int maxEventsGrouped = 50;

  private String[] filterParameters = new String[0];
  private EntityMetadata[] entityKeys = new EntityMetadata[0];
  private ParamMetadata[] params = new ParamMetadata[0];

  /**
   * Get type
   * 
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Set type
   * 
   * @param type
   *          the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Get localized type key
   * 
   * @return the localizedTypeKey
   */
  public String getLocalizedTypeKey() {
    return localizedTypeKey;
  }

  /**
   * Set localized type key
   * 
   * @param localizedTypeKey
   *          the localizedTypeKey to set
   */
  public void setLocalizedTypeKey(String localizedTypeKey) {
    this.localizedTypeKey = localizedTypeKey;
  }

  /**
   * Get description message key
   * 
   * @return the descriptionMessageKey
   */
  public String getDescriptionMessageKey() {
    return descriptionMessageKey;
  }

  /**value not one of declared 
   * Set description message key
   * 
   * @param descriptionMessageKey
   *          the descriptionMessageKey to set
   */
  public void setDescriptionMessageKey(String descriptionMessageKey) {
    this.descriptionMessageKey = descriptionMessageKey;
  }

  /**
   * Get entity
   * 
   * @return the entity
   */
  public String getEntity() {
    return entity;
  }

  /**
   * Set entity
   * 
   * @param entity
   *          the entity to set
   */
  public void setEntity(String entity) {
    this.entity = entity;
  }

  /**
   * Get source area
   * 
   * @return the sourceArea
   */
  public SourceAreaType getSourceArea() {
    return sourceArea;
  }

  /**
   * Set source area
   * 
   * @param sourceArea
   *          the sourceArea to set
   */
  public void setSourceArea(SourceAreaType sourceArea) {
    this.sourceArea = sourceArea;
  }

  /**
   * Get topic
   * 
   * @return the topic
   */
  public String getTopic() {
    return topic;
  }

  /**
   * Set topic
   * 
   * @param topic
   *          the topic to set
   */
  public void setTopic(String topic) {
    this.topic = topic;
  }

  /**
   * Get rule code
   * 
   * @return the ruleCode
   */
  public String getRuleCode() {
    return ruleCode;
  }

  /**
   * Set rule code
   * 
   * @param ruleCode
   *          the ruleCode to set
   */
  public void setRuleCode(String ruleCode) {
    this.ruleCode = ruleCode;
  }

  /**
   * Get feature enum
   * 
   * @return the featureEnum
   */
  public String getFeatureEnum() {
    return featureEnum;
  }

  /**
   * Set feature enum
   * 
   * @param featureEnum
   *          the featureEnum to set
   */
  public void setFeatureEnum(String featureEnum) {
    this.featureEnum = featureEnum;
  }

  /**
   * Is effective dated
   * 
   * @return the effectiveDated
   */
  public boolean isEffectiveDated() {
    return effectiveDated;
  }

  /**
   * Set effective dated
   * 
   * @param effectiveDated
   *          the effectiveDated to set
   */
  public void setEffectiveDated(boolean effectiveDated) {
    this.effectiveDated = effectiveDated;
  }

  /**
   * Is externally allowed
   * 
   * @return the externallyAllowed
   */
  public boolean isExternallyAllowed() {
    return externallyAllowed;
  }

  /**
   * Set externally allowed
   * 
   * @param externallyAllowed
   *          the externallyAllowed to set
   */
  public void setExternallyAllowed(boolean externallyAllowed) {
    this.externallyAllowed = externallyAllowed;
  }

  /**
   * Is smart suite event
   * 
   * @return the smartSuiteEvent
   */
  public boolean isSmartSuiteEvent() {
    return smartSuiteEvent;
  }

  /**
   * Set smart suite event
   * 
   * @param smartSuiteEvent
   *          the smartSuiteEvent to set
   */
  public void setSmartSuiteEvent(boolean smartSuiteEvent) {
    this.smartSuiteEvent = smartSuiteEvent;
  }

  /**
   * Is bulk event type
   * 
   * @return the bulkEventType
   */
  public boolean isBulkEventType() {
    return bulkEventType;
  }

  /**
   * Set bulk event type
   * 
   * @param bulkEventType
   *          the bulkEventType to set
   */
  public void setBulkEventType(boolean bulkEventType) {
    this.bulkEventType = bulkEventType;
  }

  /**
   * Get max events grouped
   * 
   * @return the maxEventsGrouped
   */
  public int getMaxEventsGrouped() {
    return maxEventsGrouped;
  }

  /**
   * Set max events grouped
   * 
   * @param maxEventsGrouped
   *          the maxEventsGrouped to set
   */
  public void setMaxEventsGrouped(int maxEventsGrouped) {
    this.maxEventsGrouped = maxEventsGrouped;
  }

  /**
   * Get filter parameters
   * 
   * @return the filterParameters
   */
  public String[] getFilterParameters() {
    return filterParameters;
  }

  /**
   * Set filter parameters
   * 
   * @param filterParameters
   *          the filterParameters to set
   */
  public void setFilterParameters(String[] filterParameters) {
    this.filterParameters = filterParameters;
  }

  /**
   * Get entity keys
   * 
   * @return the entityKeys
   */
  public EntityMetadata[] getEntityKeys() {
    return entityKeys;
  }

  /**
   * Set entity keys
   * 
   * @param entityKeys
   *          the entityKeys to set
   */
  public void setEntityKeys(EntityMetadata[] entityKeys) {
    this.entityKeys = entityKeys;
  }

  /**
   * Get params
   * 
   * @return the params
   */
  public ParamMetadata[] getParams() {
    return params;
  }

  /**
   * Set params
   * 
   * @param params
   *          the params to set
   */
  public void setParams(ParamMetadata[] params) {
    this.params = params;
  }
}
