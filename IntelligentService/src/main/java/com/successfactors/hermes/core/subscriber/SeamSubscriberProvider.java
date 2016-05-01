/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.successfactors.hermes.core.subscriber;

import com.successfactors.hermes.core.SFSubscriber;
import com.successfactors.hermes.util.SEBLog;
import com.successfactors.xi.util.SeamEnvUtils;
import com.successfactors.logging.api.Logger;

/**
 * This class performs subscriber lookup by it's name in seam context, using
 *
 * @Name annotation
 * @author ayarmolenko
 */
public class SeamSubscriberProvider implements SubscriberProvider {
  /** Logger */
  private static final Logger log = SEBLog.getLogger(SeamSubscriberProvider.class);

  @Override
  public SFSubscriber getSubscriber(String name) {
    try {
      boolean localInit = false;
      try {

        boolean isApplicationContextAlive = SeamEnvUtils.isApplicationContextActive();
        if (!isApplicationContextAlive) {
          SeamEnvUtils.beginCall();
        }
        localInit = !isApplicationContextAlive;


        return (SFSubscriber) SeamEnvUtils.getInstance(name, true, SFSubscriber.class);


      } finally {
        SeamEnvUtils.endLifeCycleIfInitiatedLocally(localInit);
      }
    } catch (Throwable th) {
      //this is workaround to avoid seam NoClassDefFound errors in test harness
      log.error("Failed to get instance for subscriber  " + name + ", " + th.getMessage());
    }
    return null;
  }
}
