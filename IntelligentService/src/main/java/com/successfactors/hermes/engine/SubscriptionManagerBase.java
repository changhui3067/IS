package com.successfactors.hermes.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;



import com.successfactors.hermes.core.HermesException;
import com.successfactors.hermes.core.SFEvent;
import com.successfactors.hermes.core.Topic;
import org.springframework.util.StringUtils;

/**
 * Base implementation that provides standard capabilities. Provides the behavior of parent topic nodes getting events.
 * and tracks all subscribers added internally.
 * 
 * @author ddiodati
 *
 */
public abstract class SubscriptionManagerBase implements SubscriptionManager {
  
  private Map<Topic,Set<String>> topicsToSubs = new HashMap<Topic, Set<String>>();
  
  private Map<String, String> queueToSubs = new HashMap<String, String>();
  private Map<UUID, String> subscribers = new HashMap<UUID, String>();

  /**
   * 
   * {@inheritDoc}.
   */
  public Set<String> getSubscribers(Topic t) {
    Iterator<Topic> iter = t.getParentIterator();
    
    Set<String> allListeners = new HashSet<String>();
    
    while (iter.hasNext()) {
      Topic next = iter.next();
      Set<String> subs = topicsToSubs.get(next);
      if (subs != null && subs.size() > 0) {
        allListeners.addAll(subs);
      }
    }
    
    //maybe cache later
    return allListeners;
    
  }
  
  /**
   * 
   * {@inheritDoc}.
   */
  public UUID addSubscriber(Topic t, String aListener) throws HermesException {
    UUID id = UUID.randomUUID();
    
    Set<String> set = topicsToSubs.get(t);
    if(set == null) {
      set = new HashSet<String>();
      
      topicsToSubs.put(t,set);
    }
    set.add(aListener);
    //System.out.println("Adding subscriber [" + t +"]," + id +"," + aListener +" to set " + set);
    subscribers.put(id, aListener);
    
    return id;
  }
  
  /**
   * 
   * {@inheritDoc}.
   */
  public String getSubscriber(SFEvent e) {
    
    String directQueue = e.getMeta().getPtpName();
   
   return this.queueToSubs.get(directQueue);
   
  }
  
/**
 * 
 * {@inheritDoc}.
 */
  public String getSubscriber(UUID uuid) {
   return this.subscribers.get(uuid);
   
  }
  
  /**
   * 
   * {@inheritDoc}.
   */
  public UUID addSubscriber(SFEvent e, String aListener) throws HermesException {
    UUID uuid = UUID.randomUUID();
    String directQueue = e.getMeta().getPtpName();
    
    String id = e.getMeta().getEventId();
    
    if (!StringUtils.isEmpty(id)) {
        uuid = UUID.fromString(id);
    }
    
    // set.add(aListener);
    subscribers.put(uuid, aListener);
    this.queueToSubs.put(directQueue, aListener);
    
    return uuid;
  }

  /**
   * removes subscriber with specified ID
   * @param subscriberUUID id of subscriber
   * @throws HermesException 
   */
  public void removeSubscriber(UUID subscriberUUID)  throws HermesException {
    String aListener = subscribers.get(subscriberUUID);
    for (Map.Entry<String,String> subscriberEntry : queueToSubs.entrySet()){
      if (subscriberEntry.getValue().equals(aListener)){
        queueToSubs.remove(subscriberEntry.getKey());
      }
    }
    subscribers.remove(subscriberUUID);
  }
  
  
    
}
