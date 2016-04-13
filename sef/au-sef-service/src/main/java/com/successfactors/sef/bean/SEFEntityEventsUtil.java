package com.successfactors.sef.bean;

import java.util.Map;

public final class SEFEntityEventsUtil {

  // Utility class, private constructor
  private SEFEntityEventsUtil() {

  }

  public static long hashCode(final String eventType, Map<String, String> entityKeys, String effectiveDate) {
    final int prime = 31;
    long result = 1L;

    result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
    for(Map.Entry<String, String> entry : entityKeys.entrySet()) {
      result = prime * result + entry.getKey().hashCode();
      result = prime * result + entry.getValue().hashCode();
    }
    result = prime * result + ((effectiveDate == null) ? 0 : effectiveDate.hashCode());
    return result;
  } 
}
