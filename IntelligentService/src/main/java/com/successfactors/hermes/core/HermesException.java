package com.successfactors.hermes.core;


/**
 * Low level event bus exception.
 * 
 * @author ddiodati
 *
 */
public class HermesException extends Exception {

  
  /**
   * Creates an exception. 
   * @param message The message of the exception
   * @param e The exception that caused this one(root cause).
   * @see Exception
   */
  public HermesException(String message,Throwable e) {
    super(message);
    this.initCause(e);
  }

  /**
   * Creates an exception.
   * @param e Root cause exception.
   * @see Exception
   */
  public HermesException(Throwable e) {
    this.initCause(e);
  }
  
  /**
   * Creates an exception.
   * @param s message
   */
  public HermesException(String s) {
    super(s);
  }

}
