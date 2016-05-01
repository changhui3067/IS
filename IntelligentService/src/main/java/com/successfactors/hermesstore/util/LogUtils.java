package com.successfactors.hermesstore.util;

import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.logging.api.Level;
import com.successfactors.logging.api.Logger;

/**
 * Utility class to ease logging.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
public class LogUtils {
  
  /**
   * Logs a message object with the given level and the specified format string and arguments.
   * 
   * @param logger
   *          The logger with the name of the calling class.
   * @param level
   *          The logging level.
   * @param message
   *          The format string.
   * @param parameters
   *          Arguments specified by the format.
   */
  public static void log(Logger logger, Level level, String message, Object... parameters) {
    if (logger.isEnabled(level)) {
      logger.log(level, message, parameters);
    }
  }


  public static void log(Logger logger, Level error, String message, SEBEvent sebEvent, Exception e) {
  }
}
