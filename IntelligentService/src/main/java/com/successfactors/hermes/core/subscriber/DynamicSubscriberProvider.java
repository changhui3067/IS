/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.successfactors.hermes.core.subscriber;

import com.successfactors.hermes.core.SFSubscriber;

/**
 * DynamicSubscriberProvider class
 * @author ayarmolenko
 * Implementation of SubscriberProvider which resolves the provider instance via in-memory mapping, provided by client
 */
public class DynamicSubscriberProvider implements SubscriberProvider {

  private String name;
  private SFSubscriber subscriber;

  /**
   * Constructor
   * @param name subscriber name
   * @param subscriber  subscriber instance
   */
  public DynamicSubscriberProvider(String name, SFSubscriber subscriber) {
    this.name = name;
    this.subscriber = subscriber;
  }

  @Override
  public SFSubscriber getSubscriber(String subscriberName) {
    return (this.name.equals(subscriberName)) ? subscriber : null;
  }
}