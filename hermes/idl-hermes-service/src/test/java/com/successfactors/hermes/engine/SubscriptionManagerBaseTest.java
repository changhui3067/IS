package com.successfactors.hermes.engine;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.successfactors.hermes.core.HermesException;
import com.successfactors.hermes.core.Meta;
import com.successfactors.hermes.core.SFEvent;
import com.successfactors.hermes.core.Topic;

public class SubscriptionManagerBaseTest extends SubscriptionManagerBase {


  
  @Test
  public void addTopicSubscriberTest() {
    SubscriptionManagerBaseTest smbt = new SubscriptionManagerBaseTest();
    
    Topic topic = new Topic("com.hermes.blah");
    try {
      smbt.addSubscriber(topic, "FirstListener");
    } catch (HermesException e) {
      // TODO Auto-generated catch block
      Assert.fail();
    }
   
    
    Set subs = smbt.getSubscribers(topic);
    Assert.assertEquals(subs.size(), 1);
    
    Assert.assertTrue(subs.contains("FirstListener"), "Should contain a first listener");
    
  }
  
  @Test
  public void addTopicSubscribersTest() {
    SubscriptionManagerBaseTest smbt = new SubscriptionManagerBaseTest();
    Topic topic = new Topic("com.hermes.blah");
    Topic topic2 = new Topic("com.hermes.foo");
    
    try {
      smbt.addSubscriber(topic, "FirstListenerblah");
      smbt.addSubscriber(topic, "FirstListenerblah2");
      smbt.addSubscriber(topic2, "SecListenerfoo");
      smbt.addSubscriber(topic2, "SecListenerfoo2");
      smbt.addSubscriber(topic2, "SecListenerfoo3");
    } catch (HermesException e) {
      // TODO Auto-generated catch block
      Assert.fail();
    }
   
    
    Set subs = smbt.getSubscribers(topic);
    Assert.assertEquals(subs.size(), 2);
    Iterator<String> iter1 = subs.iterator();
    
    while (iter1.hasNext()) {
      Assert.assertTrue(iter1.next().startsWith("FirstListener"), "Should only be firstlisteners");  
    }
    
    Set subs2 = smbt.getSubscribers(topic2);
    Assert.assertEquals(subs2.size(), 3);
    Iterator<String> iter2 = subs2.iterator();
    
    while (iter2.hasNext()) {
      Assert.assertTrue(iter2.next().startsWith("SecListener"), "Should only be seclisteners");  
    }
   
  }
  
  @Test
  public void subscribersTopicHierachyTest() {
    SubscriptionManagerBaseTest smbt = new SubscriptionManagerBaseTest();
    Topic topic = new Topic("com.hermes.blah");
    Topic topic2 = new Topic("com.hermes");
    
    try {
      smbt.addSubscriber(topic, "FirstListener");
      smbt.addSubscriber(topic2, "SecListener");

    } catch (HermesException e) {
      // TODO Auto-generated catch block
      Assert.fail();
    }
   
    
   
    
    Iterator<Topic> iter = topic.getParentIterator();
  
    if(iter.hasNext()) {
      Topic next = iter.next();
      Set<String> subs = smbt.getSubscribers(next);
      
      Assert.assertNotNull(subs);
      Assert.assertEquals(subs.size(), 2);  
      Iterator<String> subiter = subs.iterator();
      String s = subiter.next();
      Assert.assertEquals(s, "FirstListener", "Expected first listener");
      String s2 = subiter.next();
      Assert.assertEquals(s2, "SecListener", "Expected parent listener");
    } else {
      Assert.fail();
    }
    
    if(iter.hasNext()) {
      Topic next = iter.next();
      Set<String> subs = smbt.getSubscribers(next);
      Assert.assertNotNull(subs);
      Assert.assertEquals(subs.size(), 1);  
      Iterator<String> subiter = subs.iterator();
      String s = subiter.next();
      Assert.assertEquals(s, "SecListener", "Expected parent sec listener on parent node only");
    } else {
      Assert.fail();
    }
    
   
  }
  
  @Test
  public void addEventSubscriberTest() {
    SubscriptionManagerBaseTest smbt = new SubscriptionManagerBaseTest();
    SFEvent evt = new SFEvent();
    Meta m = new Meta();
    //m.setEventId("MYId");
    m.setPtpName("ptpname");
    evt.setBody("Body");
    evt.setMeta(m);
    
    SFEvent evt2 = new SFEvent();
    Meta m2 = new Meta();
    //m.setEventId("MYId");
    m2.setPtpName("ptpname2");
    evt2.setBody("Body2");
    evt2.setMeta(m2);
    
    UUID id,id2 = null;
    
    try {
      id = smbt.addSubscriber(evt, "EvtListener");
      
      
      id2 = smbt.addSubscriber(evt2, "EvtListener2");
      
      Assert.assertNotEquals(id, id2);
    } catch (HermesException e) {
      // TODO Auto-generated catch block
      Assert.fail();
    }
   
    
    String sub = smbt.getSubscriber(id2);
    Assert.assertTrue(sub.equals("EvtListener2"), "Should contain a second listener");
    
  }
  
  @Test
  public void addEventSubscriberWithPrevIdTest() {
    SubscriptionManagerBaseTest smbt = new SubscriptionManagerBaseTest();
    SFEvent evt = new SFEvent();
    Meta m = new Meta();
    UUID myid = UUID.randomUUID();
    
    m.setEventId(myid.toString());
    m.setPtpName("ptpname");
    evt.setBody("Body");
    evt.setMeta(m);
    
    try {
      UUID id = smbt.addSubscriber(evt, "EvtListener");
      Assert.assertEquals(id, myid);
    } catch (HermesException e) {
      // TODO Auto-generated catch block
      Assert.fail();
    }
   
    
    String sub = smbt.getSubscriber(myid);
    Assert.assertTrue(sub.equals("EvtListener"), "Should contain Evtlistener");
    
  }

  @Override
  public UUID addDLQSubscriber(Topic topic, String binding, String sub) throws HermesException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public UUID addCustSubscriber(String address, String subName,
      String custHQSubClassName) throws HermesException {
    return null;
  }

  @Override
  public UUID addCustSubscriber(String address, String subName,
      int maxConcurrentConsumers) throws HermesException {
    return null;
  }

}
