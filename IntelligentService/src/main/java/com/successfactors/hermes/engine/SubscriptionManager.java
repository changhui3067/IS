package com.successfactors.hermes.engine;


import java.util.Set;
import java.util.UUID;


import EventCenter.hermes.core.HermesException;
import EventCenter.hermes.core.SFEvent;
import EventCenter.hermes.core.Topic;
import com.successfactors.hermes.core.HermesException;


/**
 * Internal framework class that hides underlining technology and provides additional functionality for subscribers.
 * 
 * @author ddiodati
 *
 */
public interface SubscriptionManager {
  /**
   * Adds a subscriber for the given topic.
   * @param t The topic to subscribe to.
   * @param sub The seam name of the subscriber to add.
   * @return Id to track subscriber.
   * @throws HermesException If something goes wrong.
   */
  UUID addSubscriber(Topic t, String sub) throws HermesException;
  
  /**
   * Adds a subscriber for DLQ. 
   * @param topic name of the topic which event bounced from.
   * @param binding name of the receiving subscriber, which failed to handle event
   * @param sub The seam name of the subscrber.
   * @return Id to track subscriber.
   * @throws HermesException If something goes wrong.
   */
  UUID addDLQSubscriber(Topic topic, String binding, String sub)  throws HermesException;
  
  /**
   * Adds a subscriber for a specific ptp queue. Uses the ptpname of the event. 
   * @param evt The event with the ptpname value of the queue to use.
   * @param sub The seam name of the subscriber.
   * @return Id to track subscriber.
   * @throws HermesException If something goes wrong.
   */
  UUID addSubscriber(SFEvent evt, String sub)  throws HermesException;
  
  /**
   * Returns all subscribers for a topic.
   * This includes any subscribers on parent nodes of the topic.
   * @param t The topic to look up.
   * @return A set of subscriber seam names that should be called.
   */
  Set<String> getSubscribers(Topic t);
  
  /**
   * Returns a specific subscriber (PointTo Point) for this event.
   * @param e The event with the ptpName attribute set to the name of the queue to use.
   * @return The string seam name of the subscriber.
   */
  String getSubscriber(SFEvent e);
  
  /**
   * Returns a specific subscriber by its UUID identifier.
   * @param uuid To use to find the subscriber.
   * @return The name of the subscriber.
   * 
   */
  String getSubscriber(UUID uuid);

  /**
   * removes subscriber with given UUID
   * @param subscriberID subscriberID
   */
  void removeSubscriber(UUID subscriberID) throws HermesException, HermesException;

  /**
   * add customized subscriber
   * @param address address
   * @param subName subscriber name
   * @param custHQSubClassName customized HQ subscriber class
   * @return UUID
   * @throws HermesException HermesException
   */
  @Deprecated
  UUID addCustSubscriber(String address, String subName,
      String custHQSubClassName) throws HermesException;

  /**
   * add customized subscriber, being able to specify the 
   * max concurrent consumers
   * 
   * @param address address
   * @param subName subName
   * @param maxConcurrentConsumers maxConcurrentConsumers
   * @return UUID
   * @throws HermesException HermesException
   */
  UUID addCustSubscriber(String address, String subName,
      int maxConcurrentConsumers) throws HermesException;
}
