/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.successfactors.hermes.impl.hornetq;

import com.successfactors.hermes.util.SEBLog;
import com.successfactors.logging.api.Logger;
import org.hornetq.api.core.HornetQException;
import org.hornetq.api.core.client.ClientMessage;

/**
 * Subscriber which implements pull method to receive events
 * @author ayarmolenko
 */
public class HQPullingSubscriber extends HQSubscriber {

  private static final Logger log = SEBLog.getLogger(HQPullingSubscriber.class);
  private SubscriberWorker worker;

  /**
   * Constructor
   *
   * @param address address to subscribe
   * @param queue name of the queue for given subscriber
   * @param subscriberName subscriber name, resolved to subscriber instance via
   * SubscribersRegistry
   * @param filter filter string
   */
  public HQPullingSubscriber(String address, String queue, String subscriberName, String filter) {
    super(address, queue, subscriberName, filter);
  }

  @Override
  public void stop() throws HornetQException {
    if (worker != null) {
      worker.stop();
      worker = null;
    }
    closeConsumer();

  }

  @Override
  public void start() throws HornetQException {

    worker = new SubscriberWorker();
    worker.start();

  }

  private class SubscriberWorker implements Runnable {

    private Thread thread;
    boolean stop = false;

    /**
     * Starts the worker thread
     */
    public void start() {
      if (thread != null) {
        throw new IllegalStateException("Starting worker which is already active");
      }
      thread = new Thread(this);
      thread.setDaemon(true);
      thread.start();
      stop = false;
    }

    public void stop() {
      stop = true;
      thread = null;
    }

    @Override
    public void run() {
      while (!stop) {
        try {
          HQSession session = getSession();
          if (session == null || session.isClosed()) {
            //session is not yet created or was already 
            //closed due to connection failure
            //reload
            createConsumer();
          }
          
         
          ClientMessage message = getConsumer().receive();
          if (message != null) {
            onMessage(message);
          } else {
            log.error("consumer.receive returned null");
          }
        } catch (Throwable th) {
          try {
            log.error("Subscriber thread failed", th);
            Thread.sleep(THREAD_FAILED_SLEEP);
          } catch (InterruptedException ex) {
            log.error("Subscriber thread interrupted during quiet period", ex);
          }
        }

      }
    }
    private static final int THREAD_FAILED_SLEEP = 10000;
  }
}
