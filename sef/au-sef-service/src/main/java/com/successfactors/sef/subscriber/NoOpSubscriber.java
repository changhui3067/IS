package com.successfactors.sef.subscriber;

import org.jboss.seam.annotations.In;

import com.successfactors.hermes.core.SEBApplicationException;
import com.successfactors.hermes.core.SFEvent;
import com.successfactors.hermes.core.SFSubscriber;
import com.successfactors.hermes.core.annotation.Subscriber;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import com.successfactors.sca.ServiceCommandHandler;
import javax.inject.Inject;

/**
 * This subscriber is called for every event. This is for QA to test.
 * 
 * 
 */

@Subscriber(topic = "com.successfactors.#")
public class NoOpSubscriber implements SFSubscriber {

  private static final Logger logger = LogManager.getLogger();

  @Inject
  @In(create = true)
  private ServiceCommandHandler scaHandler;

  /**
   * This method will be called when an event is posted to hermes.
   */
  @Override
  public void onEvent(final SFEvent evt) {
    logger.debug("Event Metadata information------->>>" + evt.getMeta());
  }

  @Override
  public void onError(final SFEvent evt, final SEBApplicationException e) {
    logger.error("Failed to receive event " + evt, e);

  }

}
