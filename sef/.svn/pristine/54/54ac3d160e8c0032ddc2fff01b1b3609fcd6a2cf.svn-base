/*
 * $Id$
 */
package com.successfactors.sef.bean;

import java.util.Comparator;

/**
 * EventVO
 * 
 * @author echen SAP
 */
public class SEFEventVO {

  /**
   * event name
   */
  private String name;

  /**
   * event type
   */
  private String type;

  /**
   * event description
   */
  private String description;

  /**
   * event external code
   */
  private String externalCode;
  
  private String publisher;
  
  private boolean extAllowed;
  

  public String getExternalCode() {
    return externalCode;
  }

  public void setExternalCode(final String externalCode) {
    this.externalCode = externalCode;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  /**
   * @return the publisher
   */
  public String getPublisher() {
    return publisher;
  }

  /**
   * @param publisher the publisher to set
   */
  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  /**
   * @return the extAllowed
   */
  public boolean isExtAllowed() {
    return extAllowed;
  }

  /**
   * @param extAllowed the extAllowed to set
   */
  public void setExtAllowed(boolean extAllowed) {
    this.extAllowed = extAllowed;
  }

  /**
   * Compare EventVO by event name
   */
  public static class NameComparator implements Comparator<SEFEventVO> {
    @Override
    public int compare(final SEFEventVO obj1, final SEFEventVO obj2) {

      return obj1.getName().compareTo(obj2.getName());

    }
  }

}
