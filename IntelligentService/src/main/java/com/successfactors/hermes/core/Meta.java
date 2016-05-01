package com.successfactors.hermes.core;

import static com.successfactors.hermesstore.util.Constants.*;

import java.io.Serializable;
import java.util.*;

import com.successfactors.hermesstore.util.DateUtils;

/**
 * <code>Meta</code> encapsulates the metadata for an event.
 * 
 * @author araza
 */

public class Meta implements Serializable {

  private static final long serialVersionUID = 4927638206211020949L;

  private int priority;
  private String proxyId;
  private String serverName;
  private String topic;
  private String ptpName;

  private String companyId;
  private String eventId;

  private String type;
  private String sourceArea;
  private String effectiveStartDate;
  private long publishedAt;
  private String publishedBy;
  private boolean externalAllowed;

  private Map<String, Object> filterParameters = new LinkedHashMap<String, Object>();

  /**
   * Getter priority.
   * @return the priority
   */
  public int getPriority() {
    return priority;
  }

  /**
   * Sets the event priority. Value should be in [0...9] range.
   * @param priority the priority to set
   */
  public void setPriority(int priority) {
    this.priority = priority;
  }

  /**
   * Getter userId.
   * @return the userId
   */
  public String getUserId() {
    return getPublishedBy();
  }

  /**
   * Setter userId.
   * @param userId the userId to set
   */
  public void setUserId(String userId) {
    setPublishedBy(userId);
  }

  /**
   * Getter proxyId.
   * @return the proxyId
   */
  public String getProxyId() {
    return proxyId;
  }

  /**
   * Setter proxyId.
   * @param proxyId the proxyId to set
   */
  public void setProxyId(String proxyId) {
    this.proxyId = proxyId;
  }

  /**
   * Getter companyId.
   * @return the companyId
   */
  public String getCompanyId() {
    return companyId;
  }

  /**
   * Setter companyId.
   * @param companyId the companyId to set
   */
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  /**
   * Getter timeStamp.
   * @return the timeStamp
   */
  public long getTimeStamp() {
    return getPublishedAt();
  }

  /**
   * Setter timeStamp.
   * @param timeStamp the timeStamp to set
   */
  public void setTimeStamp(long timeStamp) {
    setPublishedAt(timeStamp);
  }

  /**
   * Getter serverName.
   * @return the serverName
   */
  public String getServerName() {
    return serverName;
  }

  /**
   * Setter serverName.
   * @param serverName the serverName to set
   */
  public void setServerName(String serverName) {
    this.serverName = serverName;
  }

  /**
   * Getter eventId.
   * @return the eventId
   */
  public String getEventId() {
    return eventId;
  }

  /**
   * Setter eventId.
   * @param eventId the eventId to set
   */
  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  /**
   * Getter topic.
   * @return the topic
   */
  public String getTopic() {
    return topic;
  }

  /**
   * Setter topic.
   * @param topic the topic to set
   */
  public void setTopic(String topic) {
    this.topic = topic;
  }

  /**
   * Getter ptpName.
   * @return the ptpName
   */
  public String getPtpName() {
    return ptpName;
  }

  /**
   * Setter ptpName.
   * @param ptpName the ptpName to set
   */
  public void setPtpName(String ptpName) {
    this.ptpName = ptpName;
  }

  /**
   * Add the filtering parameter
   * @param filterParameters list of filter parameters
   */
  public void setFilterParameters(Map<String, Object> filterParameters) {
    this.filterParameters = filterParameters;
  }

  /**
   * Getter for collection of filtering parameters
   * @return filtering parameters
   */
  public Map<String, Object> getFilterParameters() {
    return filterParameters;
  }

  /**
   * getter of type
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * setter of type
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * getter of sourceArea
   * @return the sourceArea
   */
  public String getSourceArea() {
    return sourceArea;
  }

  /**
   * setter of sourceArea
   * @param sourceArea the sourceArea to set
   */
  public void setSourceArea(String sourceArea) {
    this.sourceArea = sourceArea;
  }

  /**
   * getter of effectiveStartDate
   * @return the effectiveStartDate
   */
  public String getEffectiveStartDate() {
    return effectiveStartDate;
  }

  /**
   * setter of effectiveStartDate
   * @param effectiveStartDate the effectiveStartDate to set
   */
  public void setEffectiveStartDate(String effectiveStartDate) {
    this.effectiveStartDate = effectiveStartDate;
  }

  /**
   * getter of publishedAt
   * @return the publishedAt
   */
  public long getPublishedAt() {
    return publishedAt;
  }

  /**
   * setter of publishedAt
   * @param publishedAt the publishedAt to set
   */
  public void setPublishedAt(long publishedAt) {
    this.publishedAt = publishedAt;
  }

  /**
   * getter of publishedBy
   * @return the publishedBy
   */
  public String getPublishedBy() {
    return publishedBy;
  }

  /**
   * setter of publishedBy
   * @param publishedBy the publishedBy to set
   */
  public void setPublishedBy(String publishedBy) {
    this.publishedBy = publishedBy;
  }

  /**
   * getter of externalAllowed
   * @return the externalAllowed
   */
  public boolean isExternalAllowed() {
    return externalAllowed;
  }

  /**
   * setter of externalAllowed
   * @param externalAllowed the externalAllowed to set
   */
  public void setExternalAllowed(boolean externalAllowed) {
    this.externalAllowed = externalAllowed;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(getClass().getSimpleName());
    sb.append(EQUALITY_SIGN)
    .append(LEFT_CURLY_BRACKET)
      .append("priority").append(COLON).append(this.getPriority()).append(COMMA)
      .append("proxyId").append(COLON).append(DOUBLE_QUOTATION).append(this.getProxyId()).append(DOUBLE_QUOTATION).append(COMMA)
      .append("serverName").append(COLON).append(DOUBLE_QUOTATION).append(this.getServerName()).append(DOUBLE_QUOTATION).append(COMMA)
      .append("topic").append(COLON).append(DOUBLE_QUOTATION).append(this.getTopic()).append(DOUBLE_QUOTATION).append(COMMA)
      .append("ptpName").append(COLON).append(this.getPtpName()).append(COMMA)
      .append("companyId").append(COLON).append(DOUBLE_QUOTATION).append(this.getCompanyId()).append(DOUBLE_QUOTATION).append(COMMA)
      .append("eventId").append(COLON).append(DOUBLE_QUOTATION).append(this.getEventId()).append(DOUBLE_QUOTATION).append(COMMA)
      .append("type").append(COLON).append(DOUBLE_QUOTATION).append(this.getType()).append(DOUBLE_QUOTATION).append(COMMA)
      .append("sourceArea").append(COLON).append(DOUBLE_QUOTATION).append(this.getSourceArea()).append(DOUBLE_QUOTATION).append(COMMA)
      .append("effectiveStartDate").append(COLON).append(DOUBLE_QUOTATION).append(this.getEffectiveStartDate()).append(DOUBLE_QUOTATION).append(COMMA)
      .append("publishedAt").append(COLON).append(DOUBLE_QUOTATION).append(DateUtils.convertDate2String(new Date(this.getPublishedAt()), DateUtils.DEFAULT_SIMPLE_DATE_FORMAT)).append(DOUBLE_QUOTATION).append(COMMA)
      .append("publishedBy").append(COLON).append(DOUBLE_QUOTATION).append(this.getPublishedBy()).append(DOUBLE_QUOTATION).append(COMMA)
      .append("externalAllowed").append(COLON).append(this.isExternalAllowed()).append(COMMA)
      .append("filterParameters").append(COLON).append(LEFT_CURLY_BRACKET).append(this.getFilterParameters()).append(RIGHT_CURLY_BRACKET)
    .append(RIGHT_CURLY_BRACKET);
    return sb.toString();
  }
  
}
