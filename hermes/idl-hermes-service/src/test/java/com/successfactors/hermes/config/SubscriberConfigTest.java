package com.successfactors.hermes.config;

import com.successfactors.unittest.TestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.successfactors.hermes.config.test.IsSubscriber;

import static org.mockito.Mockito.*;

public class SubscriberConfigTest {
  private SubscriberConfig config;
  private RegistryProvider mockRegistry;

  @BeforeMethod(groups={"checkin"})
  public void setup() {
    mockRegistry = mock(RegistryProvider.class);
    config = new SubscriberConfig(mockRegistry);

    TestUtils.setField(config, "configFile", "hermes/subscriberTestConfig.properties");
  }

//  @Test(groups={"checkin"})
//  public void testValidClassRegistry() throws Exception {
//    config.configure();
//
//    // verify(mockRegistry).register(IsSubscriber.class);
//    verify(mockRegistry).startInitialization();
//  
//  }

}
