package com.successfactors.hermes.engine;

import java.util.UUID;

import com.successfactors.hermes.core.HermesException;
import com.successfactors.hermes.core.SFEvent;


/**
 * A specific queue that handles publishing. This queue is for quality of service 
 * and gives different priority to events within it.
 * 
 * @author ddiodati
 *
 */
public interface  PublishQueue {
  
  /**
   * Publishes the event to this queue.
   * @param evt The event to publish.
   * @return UUID unique id to track the event.
   * @throws HermesException If something goes wrong.
   */
  UUID publishEvent(SFEvent evt) throws HermesException;
}
