package com.successfactors.hermes.metadata;


public interface SubscriberMetaDataMBean {

  public String getNonBizXSubscribersMD();
  
  public String getLocalSubscribersMD();
  
  public String getRemoteSubscribersMD();

  public String getLeaderSubscriberServer();
}
