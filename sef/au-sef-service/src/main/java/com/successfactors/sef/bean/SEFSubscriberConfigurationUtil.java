package com.successfactors.sef.bean;

public final class SEFSubscriberConfigurationUtil {

  // Utility class, private constructor
  private SEFSubscriberConfigurationUtil() {

  }

  public static int hashCode(final String eventType, final String categoryID,
      final String subscriberID) {
    final int prime = 31;
    int result = 1;

    result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
    result = prime * result
        + ((categoryID == null) ? 0 : categoryID.hashCode());
    result = prime * result
        + ((subscriberID == null) ? 0 : subscriberID.hashCode());
    return result;
  }
}
