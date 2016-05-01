package com.successfactors.hermesstore.util;

/**
 * Utility class for operations on {@link String}.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
public class StringUtils {

  public static boolean isBlank(String str) {
    return com.sf.sfv4.util.StringUtils.isBlank(str);
  }
  
  public static boolean isNotBlank(String str) {
    return !isBlank(str);
  }
  
}
