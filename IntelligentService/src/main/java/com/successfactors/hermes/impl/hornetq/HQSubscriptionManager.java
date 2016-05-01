/**
 *
 */
package com.successfactors.hermes.impl.hornetq;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.hornetq.api.core.HornetQException;

import EventCenter.hermes.core.HermesException;
import EventCenter.hermes.core.SFEvent;
import EventCenter.hermes.core.SFFilterableSub;
import EventCenter.hermes.core.SFSubscriber;
import EventCenter.hermes.core.Topic;
import EventCenter.hermes.core.annotation.Subscriber;
import EventCenter.hermes.core.subscriber.SubscriberRegistry;
import com.successfactors.hermes.engine.ProviderFactoryCreator.FactoryType;
import com.successfactors.hermes.engine.SubscriberContainer;
import com.successfactors.hermes.engine.SubscriptionManagerBase;
import com.successfactors.hermes.util.JmxUtil;
import com.successfactors.hermes.util.ReflectionUtil;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;

/**
 * Specific manager for hornetq implementation.
 *
 * @author ddiodati
 *
 */
public class HQSubscriptionManager extends SubscriptionManagerBase {

  /** LOGGER */
  private static final Logger LOGGER = LogManager.getLogger(HQSubscriptionManager.class);

  private Map<UUID, HQSubscriber> subscribers = new HashMap<UUID, HQSubscriber>();
  private FactoryType type = null;

  /**
   * Create instance of manager based on type
   *
   * @param type The type to determine a different behavior.
   */
  public HQSubscriptionManager(FactoryType type) {
    this.type = type;
  }

  @Override
  public synchronized UUID addSubscriber(Topic t, String aListener) throws HermesException {
    UUID uuid = super.addSubscriber(t, aListener);
    SubscriberContainer container = new ManageableSubscriberContainer(t.toString(),
        aListener, aListener, getFilter(aListener));
    container.setMaxConcurrentConsumers(getMaxConcurrentConsumers(aListener));
    JmxUtil.register(container, container.toString());
    container.start();
    return uuid;
  }

  @Override
  public synchronized UUID addDLQSubscriber(Topic topic, String binding, String aListener) throws HermesException {

    UUID uuid = UUID.randomUUID();
    
    String filterExpression = getFilter(aListener);
    filterExpression = (filterExpression == null) ? "" : "(" + filterExpression + ")";
    
    if (topic != null){
       if (! filterExpression.isEmpty()){
        filterExpression += " AND ";
      }
      filterExpression += "_HQ_ORIG_ADDRESS='" + topic +"'";
    }
    
    if (binding != null){
      if (! filterExpression.isEmpty()){
        filterExpression += " AND ";
      }
      filterExpression +="_HQ_ORIG_QUEUE='" + binding + "'";
    }
  
    HQSubscriber subscriber = new HQPullingSubscriber(HQConstants.DLQ_ADDRESS, 
            aListener, //queue name = listener name 
            aListener, 
            filterExpression.isEmpty() ? null : filterExpression);

    try {
      subscriber.start();
      subscribers.put(uuid, subscriber);
      return uuid;
    } catch (HornetQException e) {
      throw new HermesException(e);
    }
  }

  @Override
  public synchronized UUID addSubscriber(SFEvent evt, String aListener) throws HermesException {
    UUID uuid = super.addSubscriber(evt, aListener);
    SubscriberContainer container = new ManageableSubscriberContainer(
        HQConstants.PTP_PREFIX + aListener, HQConstants.PTP_PREFIX + aListener,
            aListener, getFilter(aListener));
    container.setMaxConcurrentConsumers(getMaxConcurrentConsumers(aListener));
    JmxUtil.register(container, container.toString());
    container.start();
    return uuid;
  }

  /**
   * Stops given subscriber
   * @param uuid subscriber UUID
   * @throws HermesException 
   */
  public synchronized void removeSubscriber(UUID uuid) throws HermesException {
    HQSubscriber subscriber = subscribers.get(uuid);
    if (subscriber != null) {
      try {
        subscriber.stop();
      } catch (HornetQException ex) {
        throw new HermesException(ex);
      }
    }
    subscribers.remove(uuid);
  }

  private String getFilter(String subscriberName) {
    SFSubscriber subscriber = SubscriberRegistry.getInstance().getSubscriber(
        subscriberName);
    if (subscriber instanceof SFFilterableSub) {
      return ((SFFilterableSub) subscriber).getFilterExpression();
    }
    return null;
  }

  @Override
  @Deprecated
  public UUID addCustSubscriber(String address, String subName,
      String custHQSubClassName) throws HermesException {
    UUID uuid = UUID.randomUUID();
    SubscriberContainer container = new ManageableSubscriberContainer(
        address, subName, subName, getFilter(subName));
    container.start();
    return uuid;
  }

  @Override
  public UUID addCustSubscriber(String address, String subName,
      int maxConcurrentConsumers) throws HermesException {
    UUID uuid = UUID.randomUUID();
    SubscriberContainer container = new ManageableSubscriberContainer(
        address, subName, subName, getFilter(subName));
    container.setMaxConcurrentConsumers(maxConcurrentConsumers);
    container.start();
    return uuid;
  }

  private int getMaxConcurrentConsumers(String subscriberName) {
    SFSubscriber subscriber = SubscriberRegistry.getInstance().getSubscriber(
        subscriberName);
    Subscriber subscriberAnnotation = ReflectionUtil.getSpecifiedAnnotation(
        subscriber.getClass(), Subscriber.class);
    if (subscriberAnnotation != null) {
      return subscriberAnnotation.maxConcurrentConsumers();
    }
    return SubscriberContainer.DEFAULT_MAX_CONCURRENT_CONSUMER_COUNT;
  }
}
