/*
 * $Id$
 */
package com.successfactors.sef.bean;

/**
 * EventVO
 * 
 */
public class SEFSubscriberVO implements Comparable<SEFSubscriberVO> {

  /**
   * event name
   */
  private String moduleName;
  
  private String moduleId;

  /**
   * event type
   */
  private String type;

  private final String[] impactArea = new String[0];
  private String descriptionMessageKey;

  /**
   * @return the moduleId
   */
  public String getModuleId() {
    return moduleId;
  }

  /**
   * @param moduleId the moduleId to set
   */
  public void setModuleId(String moduleId) {
    this.moduleId = moduleId;
  }

  public String getModuleName() {
    return moduleName;
  }

  public void setModuleName(final String moduleName) {
    this.moduleName = moduleName;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public String getDescriptionMessageKey() {
    return descriptionMessageKey;
  }

  public void setDescriptionMessageKey(final String descriptionMessageKey) {
    this.descriptionMessageKey = descriptionMessageKey;
  }

  public String[] getImpactArea() {
    return impactArea;
  }

  @Override
  public int compareTo(final SEFSubscriberVO o) {

    return this.moduleName.compareTo(o.getModuleName());
  }

}
