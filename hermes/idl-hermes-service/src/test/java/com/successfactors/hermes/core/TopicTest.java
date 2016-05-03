package com.successfactors.hermes.core;

import java.util.Iterator;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TopicTest {
  
  @Test(groups={"checkin"})
  public void createSimpleTopic() {
    Topic t = new Topic("blah");
    
    Assert.assertEquals(t.toString(), "blah", "Should have just one node.");
  }
  
  @Test(groups={"checkin"})
  public void createMultiLevelTopic() {
    Topic t = new Topic("blah.foo.something");
    
    
    
    Assert.assertEquals(t.toString(), "blah.foo.something", "Should have three levels");
 
    
     
  }
  
  @Test(groups={"checkin"})
  public void testTopicIterating() {
    Topic t = new Topic("blah.foo.something");
    Iterator<Topic> iter = t.getParentIterator();
    
 
    Topic next = iter.next();
    Assert.assertEquals(next.toString(), "blah.foo.something", "Should be at something.");  
   
    next = iter.next();
    Assert.assertEquals(next.toString(), "blah.foo", "Should be at blah.foo node.");  
    
    next = iter.next();
    Assert.assertEquals(next.toString(), "blah", "Should be at blah node.");  

    
    Assert.assertFalse(iter.hasNext() , "Should not have anymore.");
    
  }
  
  
  @Test(groups={"checkin"})
  public void testTopicParentNewInstanceCompare() {
    Topic t = new Topic("blah.foo.something");
    Topic t2 = new Topic("blah.foo");
    
    System.out.println(t.getParent().toString());
    System.out.println(t2.toString());
    
    Assert.assertEquals(t.getParent(), t2); // should be the same event thought different instances
   
    
  }
  
  

}
