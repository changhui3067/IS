/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.successfactors.hermes.impl.hornetq;

/**
 * Collection of static definitions, used by HQ classes
 *
 * @author ayarmolenko
 */
public abstract class HQConstants {

  /**
   * name of message_ID header
   */
  public static final String MESSAGE_ID = "com.successfactors.hermes.MessageID";
  
  /**
   * PTP queue name prefix
   */
  public static final String PTP_PREFIX = "com.successfactors.PointToPointQueue.";
  
  /**
   * name of company_name header
   */
  public static final String COMPANY_NAME = "com.successfactors.hermes.CompanyName";
  
  /**
   * name of user_ID header
   */
  public static final String USER_ID = "com.successfactors.hermes.UserID";
  
  /**
   * Priority meta field name
   */
  public static final String MESSAGE_PRIORITY = "com.successfactors.hermes.MessagePriotity";
  
  
  /**
   * DLQ address
   */
  public static final String DLQ_ADDRESS = "jms.queue.DLQ";
}
