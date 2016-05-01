package com.successfactors.hermes.config.test;

import com.successfactors.hermes.core.annotation.Subscriber;
import com.successfactors.hermes.core.SFSubscriber;
import com.successfactors.hermes.core.SFEvent;
import com.successfactors.hermes.core.SEBApplicationException;
      

@Subscriber (topic="dummy")
public class IsSubscriber implements SFSubscriber {
  public IsSubscriber() {}
  public void onEvent(SFEvent evt) {}
  
  public void onError(SFEvent evt, SEBApplicationException e) {}
}
