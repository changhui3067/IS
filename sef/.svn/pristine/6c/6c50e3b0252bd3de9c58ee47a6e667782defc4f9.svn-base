/*
 * $Id$
 */
package com.successfactors.sef.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * EventVO
 * 
 */
public class SEFSubscriberConfigurationVO implements
    Comparable<SEFSubscriberConfigurationVO>, Serializable {

  /**
   * Event Type
   */
  private String eventType;

  /**
   * Category ID
   */
  private String categoryID;

  /**
   * Subscriber ID
   */
  private String subscriberID;

  /**
   * Subscriber Name
   */
  private String subscriberName;

  /**
   * Subscriber Impact Area
   */
  // private String subscribingImpactArea;

  /**
   * Subscriber Impact Area List
   */
  private final List<String> subscribingImpactAreas = new ArrayList<>();

  /**
   * Subscriber Description
   */
  private String subscriberDescription;

  /**
   * Enable/Disable status
   */
  private boolean status;

  /**
   * number of days in Advance
   */
  private Integer daysInAdvance;

  /**
   * Re-deliver status
   */
  private Boolean reDelivered;

  /**
   * needs preProcessing
   */
  private Boolean isPreProcessing = false;
  
  /**
   * external link
   */
  private String exnteralLink;
  
  /**
   * external link Title
   */
  private String exnteralLinkTitle;

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
   * @return the categoryID
   */
  public String getCategoryID() {
    return categoryID;
  }

  /**
   * @param categoryID
   *          the categoryID to set
   */
  public void setCategoryID(final String categoryID) {
    this.categoryID = categoryID;
  }

  /**
   * @return the subscriberID
   */
  public String getSubscriberID() {
    return subscriberID;
  }

  /**
   * @param subscriberID
   *          the subscriberID to set
   */
  public void setSubscriberID(final String subscriberID) {
    this.subscriberID = subscriberID;
  }

  /**
   * @return the subscriberDescription
   */
  public String getSubscriberDescription() {
    return subscriberDescription;
  }

  /**
   * @param subscriberDescription
   *          the subscriberDescription to set
   */
  public void setSubscriberDescription(final String subscriberDescription) {
    this.subscriberDescription = subscriberDescription;
  }

  /**
   * @return the status
   */
  public boolean isStatus() {
    return status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(final boolean status) {
    this.status = status;
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
   * @return the subscriberName
   */
  public String getSubscriberName() {
    return subscriberName;
  }

  /**
   * @param subscriberName
   *          the subscriberName to set
   */
  public void setSubscriberName(final String subscriberName) {
    this.subscriberName = subscriberName;
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
   * @return the subscribingImpactAreas
   */
  public List<String> getSubscribingImpactAreas() {
    return subscribingImpactAreas;
  }

  /**
   * @return the isPreProcessing
   */
  public Boolean getIsPreProcessing() {
    return isPreProcessing;
  }

  /**
   * @param isPreProcessing
   *          the isPreProcessing to set
   */
  public void setIsPreProcessing(final Boolean isPreProcessing) {
    this.isPreProcessing = isPreProcessing;
  }

  /**
   * @return the exnteralLink
   */
  public String getExnteralLink() {
    return exnteralLink;
  }

  /**
   * @param exnteralLink the exnteralLink to set
   */
  public void setExnteralLink(String exnteralLink) {
    this.exnteralLink = exnteralLink;
  }

  /**
   * @return the exnteralLinkTitle
   */
  public String getExnteralLinkTitle() {
    return exnteralLinkTitle;
  }

  /**
   * @param exnteralLinkTitle the exnteralLinkTitle to set
   */
  public void setExnteralLinkTitle(String exnteralLinkTitle) {
    this.exnteralLinkTitle = exnteralLinkTitle;
  }

  @Override
  public int hashCode() {
    return SEFSubscriberConfigurationUtil.hashCode(eventType, categoryID,
        subscriberID);
  }

  @Override
  public int compareTo(final SEFSubscriberConfigurationVO o) {
    if (this.getEventType().equalsIgnoreCase(o.getEventType())) {
      return this.getCategoryID().compareTo(o.getCategoryID());
    } else {
      return this.getEventType().compareTo(o.getEventType());
    }
  }

}
