/*
 * $Id$
 */
package com.successfactors.sef.service;

import com.successfactors.sca.ServiceCommand;

/**
 * SetSubscriberConfigurationData
 * 
 */
public class SetSubscriberConfigurationData implements ServiceCommand<String> {

  // private SEFSubscriberConfiguration sefSubscriberConfiguration;

  private String eventType;

  private String categoryId;

  private String subscriberId;

  private boolean enabled;

  private Integer daysInAdvance;

  private Boolean reDelivered;

  /**
   * @return the eventType
   */
  public String getEventType() {
    return eventType;
  }

  /**
   * @param eventType
   *          the eventType to set
   */
  public void setEventType(final String eventType) {
    this.eventType = eventType;
  }

  /**
   * @return the categoryId
   */
  public String getCategoryId() {
    return categoryId;
  }

  /**
   * @param categoryId
   *          the categoryId to set
   */
  public void setCategoryId(final String categoryId) {
    this.categoryId = categoryId;
  }

  /**
   * daysInAdvance
   * 
   * @return the subscriberId
   */
  public String getSubscriberId() {
    return subscriberId;
  }

  /**
   * @param subscriberId
   *          the subscriberId to set
   */
  public void setSubscriberId(final String subscriberId) {
    this.subscriberId = subscriberId;
  }

  /**
   * @return the enabled
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * @param enabled
   *          the enabled to set
   */
  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * @return the daysInAdvance
   */
  public Integer getDaysInAdvance() {
    return daysInAdvance;
  }

  /**
   * @param daysInAdvance
   *          the daysInAdvance to set
   */
  public void setDaysInAdvance(final Integer daysInAdvance) {
    this.daysInAdvance = daysInAdvance;
  }

  /**
   * @return the reDelivered
   */
  public Boolean getReDelivered() {
    return reDelivered;
  }

  /**
   * @param reDelivered
   *          the reDelivered to set
   */
  public void setReDelivered(final Boolean reDelivered) {
    this.reDelivered = reDelivered;
  }

  /**
   * Constructor
   */
  public SetSubscriberConfigurationData() {

  }

}
