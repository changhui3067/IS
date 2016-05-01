package com.successfactors.seb;

import com.successfactors.hermes.core.EventDefinition;
import com.successfactors.hermes.core.HermesException;
import com.successfactors.hermes.core.SFEvent;
import com.successfactors.hermes.core.Topic;
import com.successfactors.hermes.core.subscriber.SubscriberProvider;
import com.successfactors.serialization.SerializationException;

import java.util.List;
import java.util.UUID;

/**
 * Service Event bus api client. This provides clients access to the event bus.
 * 
 * @author ddiodati
 *
 */
public interface ServiceEventBus<T> {
  /**
   * Publishes and event to a specific topic.
   * 
   * @param topic The topic to publish to.
   * @param evt The event to publish
   * @throws SerializationException If the event fails to get serialized.
   * @throws HermesException  If something goes wrong during publish.
   * @return The UUID id for this event for identification.
   */
  UUID publish(Topic topic, SFEvent<T> evt) throws HermesException, SerializationException;
  
  /**
   * Add a subscriber for the given topic.
   * 
   * @param topic The topic to listen on.
   * @param subscriberName The seam name of the subscriber to use.
   * @throws HermesException If an error occurs during registering subscriber.
   */
  UUID addSubscriber(Topic topic, String subscriberName) throws HermesException;
  
  /**
   * Add a subscriber for the Dead Letter Queue.
   * 
   * @param topic The name of the topic message have bounced from, null to subscribe for all topics
   * @param binding Filter by the name of the particular subscriber which failed to handle message, null to ignore this filter
   * @param subscriberName The seam name of the subscriber to use.
   * @throws HermesException If an error occurs during registering subscriber.
   */
  UUID addDLQSubscriber(Topic topic, String binding, String subscriberName) throws HermesException;
  
  
  
    
  /**
   * Allows to register dynamic subscribers. 
   * Subscriber provider is responsible for resolving subscriber instance by it's name
   * Default implementation does that using SEAM annotations on subscriber classes.
   * 
   * @param provider new provider to add
   */
  void addSubscriberProvider(SubscriberProvider provider);
  
  /**
   * De-registers subscriber. Actual implementation of EventBus should stop dispatching
   * events to this subscriber and clean up underlying infrastructure.
   * @param subscriberID is ID of subscriber
   */
  void removeSubscriber(UUID subscriberID) throws HermesException;

  /**
   * load definitions
   * @return list of event definitions
   * @throws HermesException HermesException
   */
  List<EventDefinition> loadDefinitions() throws HermesException;
}
