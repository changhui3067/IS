package com.successfactors.hermes.impl.hornetq;

import com.successfactors.serialization.SerializationException;
import com.successfactors.serialization.json.JSONSerializationUtils;
import com.successfactors.unittest.TestUtils;

import org.hornetq.api.core.HornetQException;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.successfactors.hermes.core.HermesException;
import com.successfactors.hermes.core.SFEvent;
import com.successfactors.hermes.core.Topic;

import java.util.HashMap;

import static org.mockito.Mockito.*;

public class HQPublishQueueImplTest {
  

 

  @BeforeMethod(groups={"checkin"})
  public void setup() {
   }

  @Test(groups={"checkin"})
  public void testMissingRequiredPropsEvent()  {
    
    HQPublishQueueImpl pq = new HQPublishQueueImpl(null);
    
    SFEvent evt = new SFEvent("This is the body");
    
    try {
      pq.publishEvent(evt);
    } catch (HermesException e) {
      Assert.assertTrue(e.getMessage().contains("Topic or ptpname is required"), "Exception should have been throw");
    }catch (SerializationException e2) {
      
    }
    
    
    
  }
  
  @Test(groups={"checkin"})
  public void testbothPropsSetEvent()  {
    
    HQPublishQueueImpl pq = new HQPublishQueueImpl( new Topic("com.successfactors.hermes.test"));
    
    SFEvent evt = new SFEvent("This is the body");
    evt.getMeta().setPtpName("Destination point");
    
    try {
      pq.publishEvent(evt);
    } catch (HermesException e) {
      Assert.assertTrue(e.getMessage().contains("Topic or ptpname is required"), "Exception should have been throw");
    }catch (SerializationException e2) {
      
    }
   
    
  }
  /*
  @Test(groups={"checkin"})
  public void testSuccessfulTopicPublish()  {
    Topic topic = new Topic("com.succcessfactors.hermes.test");
    
    HQPublishQueueImpl pq = new HQPublishQueueImpl( topic);
    
    SFEvent evt = new SFEvent("This is the body");
    evt.getMeta().setFilterParameters(new HashMap<String, Object>());
    String json = null;
    
    try {
      pq.publishEvent(evt);
      json = JSONSerializationUtils.toJSON(evt,true);
      
     
    } catch (HermesException e) {
      Assert.fail("no exception should occur " +e.getMessage());
    }catch (SerializationException e2) {
      Assert.fail("no exception should occur " +e2.getMessage());
    }
    
    
    
    
  }
  
  @Test(groups={"checkin"})
  public void testSuccessfulPtoPPublish()  {
    
    
    HQPublishQueueImpl pq = new HQPublishQueueImpl( null);
    
    SFEvent evt = new SFEvent("This is the body");
    evt.getMeta().setPtpName("Point to Point Dest");
    evt.getMeta().setFilterParameters(new HashMap<String, Object>());
    
    String json = null;
    
    try {
      pq.publishEvent(evt);
      json = JSONSerializationUtils.toJSON(evt,true);
      
     
    } catch (HermesException e) {
      Assert.fail("no exception should occur " +e.getMessage());
    }catch (SerializationException e2) {
      Assert.fail("no exception should occur " +e2.getMessage());
    }
    
    
    

    
  }
  */
  
  
  }