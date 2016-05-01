/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.successfactors.hermes.util;

import com.successfactors.logging.api.Logger;
import com.successfactors.logging.api.LogManager;

/**
 * hermes logger
 * creates logger with name prefixed by __SEB__ string
 * @author ayarmolenko
 */
public class SEBLog {
  private SEBLog(){
    
  }
  /**
   * creates a logger
   * @param cls class name
   * @return  logger
   */
  public static Logger getLogger(Class cls){
    return LogManager.getLogger(SEB_LOG_PREFIX + cls.getCanonicalName());
  }
  
  private static final String SEB_LOG_PREFIX = "__SEB__";
  
}
