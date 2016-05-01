/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.successfactors.hermes.core.subscriber;

import EventCenter.category.proxy.SFSubscriberProxyFactory;
import EventCenter.hermes.bean.SubscriberMetaDataBean;
import EventCenter.hermes.core.SEBApplicationException;
import EventCenter.hermes.core.SFEvent;
import EventCenter.hermes.core.SFSubscriber;
import EventCenter.hermes.metadata.SubscriberMetaDataManager;
import EventCenter.hermes.util.SEBLog;

import java.util.ArrayList;
import java.util.List;

import com.successfactors.logging.api.Logger;

/**
 * Class used to resolve subscriber by their name and invoke them
 * Resolution process is delegated to providers
 * @author ayarmolenko
 */
public class SubscriberRegistry {

  private List<SubscriberProvider> providers = new ArrayList<SubscriberProvider>();
  private static final SubscriberRegistry INSTANCE = new SubscriberRegistry();
  
  /** Logger */
  private static final Logger log = SEBLog.getLogger(SubscriberRegistry.class);

  private SubscriberRegistry() {
    providers.add(new SeamSubscriberProvider());
  }
  
  /**
   * Singleton method to obtain instance of registry class
   * @return a registry instance
   */

  public static SubscriberRegistry getInstance() {
    return INSTANCE;
  }

  /**
   * Adds another provider to the list
   * @param provider to add
   */
  public synchronized void addProvider(SubscriberProvider provider) {
    providers.add(provider);
  }

  /**
   * Removed  provider from the list
   * @param provider to remove
   */
  
  public synchronized void removeProvider(SubscriberProvider provider) {
    providers.remove(provider);
  }

    /**
   * Executes the sf subscriber and handles starting seam if needed.
   *
   * @param subname The subscriber name.
   * @param evt The event
   * @param serializationError Exception if it occured.
   * @param logger THe logger to log with.
   * @return boolean if it went thru
   */
  
  public boolean executeSubscriber(String subname, SFEvent evt, Exception serializationError, Logger logger) {

    SFSubscriber subscriber = getSubscriber(subname);
    
    if (subscriber == null) {
      logger.error("Failed to obtain subscriber instance: " + subname);
      return false;
    }
    String topic = evt.getMeta().getTopic();
    SubscriberMetaDataBean metaData = SubscriberMetaDataManager.getMetaData(topic, subname);
    SFSubscriber proxySubscriber = SFSubscriberProxyFactory.newInstance().createProxy(subscriber, metaData);
    logger.debug("Create proxy for subscriber : " + subname);
    subscriber = proxySubscriber;

    try {

      if (serializationError != null) {
        subscriber.onError(evt, new SEBApplicationException("Failed to deserialize message", serializationError));
      } else {
        subscriber.onEvent(evt);

      }
      return evt.isAcknowledged();
    } catch (RuntimeException re) {
      
      logger.error("Subscriber " + subname + " threw exception " + re.getMessage(), re);
      
      try {
        subscriber.onError(evt, new SEBApplicationException(re));
      } catch (RuntimeException e2) {
        logger.error("Subscriber " + subname + " onError method threw exception " + e2.getMessage(), e2);
      }
    }
    return false;
  }
  
  /**
   * Queries all providers to find the subscriber by it's name
   * @param name name of subscriber
   * @return subscriber instance. null if nothing was found.
   */

  public synchronized SFSubscriber getSubscriber(String name) {
    for (SubscriberProvider provider : providers) {
      SFSubscriber subscriber = null;
      try {
        subscriber = provider.getSubscriber(name);
      } catch (Throwable e) { //workaround for no SEAM environment in test harness
        log.info("Provider " + provider + " failed to resolve subscriber instance: " + name 
                + ", error = " + e.getMessage());
      } 
      if (subscriber != null) {
        return subscriber;
      }
    }
    return null;
  }
}
