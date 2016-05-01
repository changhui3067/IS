package com.successfactors.hermesstore.util;

import java.util.*;

/**
 * Utility class for operations on {@link Collection}.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
public class CollectionUtils {

  public static boolean hasElements(Object[] arr) {
    return arr != null && arr.length > 0;
  }
  
  public static boolean hasElements(List list) {
    return list != null && !list.isEmpty();
  }
  
  public static boolean hasElements(Set set) {
    return set != null && !set.isEmpty();
  }
  
  public static boolean hasNoElements(Set set) {
    return !hasElements(set);
  }
  
}
