package com.successfactors.hermes.impl.hornetq;

import java.util.Map;
import java.util.Map.Entry;

import org.hornetq.api.core.HornetQException;

import com.successfactors.hermes.core.SFEvent;
import com.successfactors.hermes.core.Topic;
import com.successfactors.hermes.core.annotation.RetryConfig;
import com.successfactors.hermes.engine.PublishQueue;
import com.successfactors.hermes.engine.QOSQueueManager;
import EventCenter.seb.util.SEBLog;

public class HQQOSQueueManagerImpl implements QOSQueueManager {



  /**
   * Constructor.
   */
  public HQQOSQueueManagerImpl() {

  }

  /**
   * {@inheritDoc}.
   */
  public PublishQueue getQueueInstance(Topic t) {
    PublishQueue impl = new HQPublishQueueImpl(t);
    return impl;
  }

  /**
   * {@inheritDoc}.
   */
  public PublishQueue getQueueInstance(SFEvent e) {
    PublishQueue impl = new HQPublishQueueImpl(null);
    return impl;
  }

  @Override
  public void manageRetryConfigurations(
      Map<String, RetryConfig> retryConfigurations) {
    HQSessionPool hqSessionPool = HQSessionPool.getInstance();
    HQSession hqSession = null;
    try {
      hqSession = hqSessionPool.getSession();
      for (Entry<String, RetryConfig> retryConfEntry : retryConfigurations.entrySet()) {
        RetryConfig retryConf = retryConfEntry.getValue();
        hqSession.manageRetry(retryConfEntry.getKey(),
            retryConf.redeliveryDelay(), retryConf.redeliveryDelayMultiplier(),
            retryConf.maxRedeliveryDelay(), retryConf.maxDeliveryAttempts());
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      if (hqSession != null) {
        try {
          hqSessionPool.releaseSession(hqSession);
        } catch (HornetQException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }

}
