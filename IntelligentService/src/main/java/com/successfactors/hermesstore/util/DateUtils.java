package com.successfactors.hermesstore.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sf.sfv4.util.StringUtils;

/**
 * Utility class for operations on {@link Date}.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
public class DateUtils {

  public static final String DEFAULT_SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  
  /**
   * Converts the input date from {@link Date} to {@link String} with the specified format.
   * 
   * @param date 
   *          The date to convert
   * @param format 
   *          The specified string format 
   * @return A formatted string representation of the date
   */
  public static String convertDate2String(Date date, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.isBlank(format) ? DEFAULT_SIMPLE_DATE_FORMAT : format);
    if (date != null) {
      return sdf.format(date);
    } else {
      return null;
    }
  }  
  
}
