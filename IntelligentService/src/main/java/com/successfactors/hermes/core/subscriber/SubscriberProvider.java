/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.successfactors.hermes.core.subscriber;

import com.successfactors.hermes.core.SFSubscriber;

/**
 * SubscriberProvider interface
 * @author ayarmolenko
 */
public interface SubscriberProvider {
  /**
   * resolves the instance of subscriber by it's name
   * @param name the name of subscriber
   * @return subscriber instance
   */
  SFSubscriber getSubscriber(String name);
}
