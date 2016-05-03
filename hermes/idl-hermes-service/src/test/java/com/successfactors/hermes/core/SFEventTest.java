package com.successfactors.hermes.core;

import org.testng.annotations.Test;

import com.successfactors.unittest.PropertyTester;

public class SFEventTest {
  
  @Test(groups={"checkin"})
  public void testMeta() {
    Meta meta = new Meta();
    
    PropertyTester pt = new PropertyTester(meta);
   
       // run the test
    //pt.testPropertyMethods();
  }
  
  @Test(groups={"checkin"})
  public void testEventObject() {
  
    SFEvent<String> evt = new SFEvent<String>();
  
    Meta meta = new Meta();
    String body = new String("HERE");

    PropertyTester pt = new PropertyTester(evt);
    
    pt.testPropertyMethods();
    
  }
}
