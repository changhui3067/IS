/*
 * $Id$
 */
package com.successfactors.sef.bean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * SEFSubscriberMapVO
 * 
 */
public class SEFSubscriberMapVO {

  /**
   * subscribersMap
   */
  private final Map<String, List<SEFSubscriberVO>> subscribersMap = new LinkedHashMap<String, List<SEFSubscriberVO>>();

  /**
   * get subscribers
   * 
   * @return the subscribers
   */

  public Map<String, List<SEFSubscriberVO>> getSubscribers() {
    return subscribersMap;
  }
}
