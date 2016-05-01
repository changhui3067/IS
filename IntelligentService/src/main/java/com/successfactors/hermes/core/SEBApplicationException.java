package com.successfactors.hermes.core;

/**
 * Application based exception occured, which may be recoverable.
 * @author ddiodati
 *
 */
public class SEBApplicationException extends HermesException {
  
  /**
   * Create new exception
   * @param msg The error message
   * @param t The exception that caused the issue.
   */
  public SEBApplicationException(String msg, Throwable t) {
    super(msg,t);
  }
  
  /**
   * Create a new exception.
   * @param t The throwable that caused the issue.
   */
  public SEBApplicationException(Throwable t) {
    super(t);
  }
  
  /**
   * Create a new exception.
   * @param s The message for the exception
   */
  public SEBApplicationException(String s) {
    super(s);
  }

}
