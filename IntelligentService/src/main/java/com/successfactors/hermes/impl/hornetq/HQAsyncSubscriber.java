/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.successfactors.hermes.impl.hornetq;

import com.successfactors.hermes.util.SEBLog;
import com.successfactors.logging.api.Logger;
import org.hornetq.api.core.HornetQException;

/**
 * Implements async subscription method.
 * @author ayarmolenko
 */
public class HQAsyncSubscriber extends HQSubscriber {

  private static final Logger log = SEBLog.getLogger(HQAsyncSubscriber.class);

  /**
   * Constructor
   * @param address address to subscribe
   * @param queue name of the queue for given subscriber
   * @param subscriberName subscriber name, resolved to subscriber instance via SubscribersRegistry
   * @param filter filter string
   */
  protected HQAsyncSubscriber(String address, String queue, String subscriberName, String filter) {
    super(address, queue, subscriberName, filter);
  }

  @Override
  public void stop() throws HornetQException {
    closeConsumer();
  }

  @Override
  public void start() throws HornetQException {
    createConsumer();
    getConsumer().setMessageHandler(this);

  }
}
