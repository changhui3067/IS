/*
 * $Id$
 */
package com.successfactors.sef.service;

import java.util.List;
import java.util.Map;

import com.successfactors.sca.ServiceQuery;

/**
 * GetEntityEventsMap
 * 
 */
public class GetEntityEventsMap implements ServiceQuery<List<String>> {

  private String entityName;
  private Map<String, String> entityKeys;
  private String effectiveDate;

  /**
   * @return the entityName
   */
  public String getEntityName() {
    return entityName;
  }

  /**
   * @param entityName
   *          the entityName to set
   */
  public void setEntityName(final String entityName) {
    this.entityName = entityName;
  }

  /**
   * @return the entityKeys
   */
  public Map<String, String> getEntityKeys() {
    return entityKeys;
  }

  /**
   * @param entityKeys
   *          the entityKeys to set
   */
  public void setEntityKeys(final Map<String, String> entityKeys) {
    this.entityKeys = entityKeys;
  }

  /**
   * Constructor
   */
  public GetEntityEventsMap() {

  }

  /**
   * @return the effectiveDate
   */
  public String getEffectiveDate() {
    return effectiveDate;
  }

  /**
   * @param effectiveDate the effectiveDate to set
   */
  public void setEffectiveDate(String effectiveDate) {
    this.effectiveDate = effectiveDate;
  }

}
