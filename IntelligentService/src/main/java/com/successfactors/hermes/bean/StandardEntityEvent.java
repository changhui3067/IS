package com.successfactors.hermes.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Standard entity event
 * @author yyang
 *
 */
public class StandardEntityEvent {

  public enum ValidityType {
    current,
    future
  }

  /** entity */
  private String entity;

  /** entity keys */
  private Map<String, String> entityKeys = new HashMap<String, String>();

  /** params */
  private Map<String, String> params = new HashMap<String, String>();


  private String effectiveStartDate;

  private long publishedAt;

  private String publishedBy;

  private String validity;

  private String processId;

  private String internalId;

  private boolean postFromTransaction = true;

  /**
   * getter of entity
   * @return the entity
   */
  public String getEntity() {
    return entity;
  }

  /**
   * setter of entity
   * @param entity the entity to set
   */
  public void setEntity(String entity) {
    this.entity = entity;
  }

  /**
   * getter of entityKeys
   * @return the entityKeys
   */
  public Map<String, String> getEntityKeys() {
    return entityKeys;
  }

  /**
   * setter of entityKeys
   * @param entityKeys the entityKeys to set
   */
  public void setEntityKeys(Map<String, String> entityKeys) {
    this.entityKeys = entityKeys;
  }

  /**
   * getter of params
   * @return the params
   */
  public Map<String, String> getParams() {
    return params;
  }

  /**
   * setter of params
   * @param params the params to set
   */
  public void setParams(Map<String, String> params) {
    this.params = params;
  }

  public String getEffectiveStartDate() {
    return effectiveStartDate;
  }

  public void setEffectiveStartDate(String effectiveStartDate) {
    this.effectiveStartDate = effectiveStartDate;
  }

  public long getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(long publishedAt) {
    this.publishedAt = publishedAt;
  }

  public String getPublishedBy() {
    return publishedBy;
  }

  public void setPublishedBy(String publishedBy) {
    this.publishedBy = publishedBy;
  }

  public String getProcessId() {
    return processId;
  }

  public void setProcessId(String processId) {
    this.processId = processId;
  }

  public String getInternalId() {
    return internalId;
  }

  public void setInternalId(String internalId) {
    this.internalId = internalId;
  }

  public boolean isPostFromTransaction() {
    return postFromTransaction;
  }

  public void setPostFromTransaction(boolean postFromTransaction) {
    this.postFromTransaction = postFromTransaction;
  }

  public String getValidity() {
    return validity;
  }

  public void setValidity(String validity) {
    this.validity = validity;
  }
}
