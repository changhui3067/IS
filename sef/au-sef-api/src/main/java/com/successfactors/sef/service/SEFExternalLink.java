package com.successfactors.sef.service;

public interface SEFExternalLink {
  /**
   * @param eventType
   *          the event type to set
   * @return external link URL
   */
  String getExternalURL(String eventType);
}
