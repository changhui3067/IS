package com.successfactors.hermes.core;

/**
 * Filterable subscriber API
 * @author yyang
 *
 */
public interface SFFilterableSub extends SFSubscriber {

  /**
   * For supported filter expression, please refer to
   * http://docs.oracle.com/javaee/6/api/javax/jms/Message.html
   * 
   * Only matched events(configured with same filter expression)
   * will be routed to this subscriber
   * 
   * @return filter expression
   */
  String getFilterExpression();
}
