package com.successfactors.hermes.config;

import com.successfactors.unittest.TestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.successfactors.hermes.config.test.IsSubscriber;

import static org.mockito.Mockito.*;

public class SubscriberConfigVisitorTest {
  private SubscriberConfigVisitor visitor;
  private RegistryProvider mockRegistry;

  @BeforeMethod(groups={"checkin"})
  public void setup() {
    mockRegistry = mock(RegistryProvider.class);
    visitor = new SubscriberConfigVisitor(mockRegistry, "");
  }

  @Test(groups={"checkin"})
  public void testRegisterSubscriber() throws Exception {
    visitor.visit(IsSubscriber.class);

    verify(mockRegistry).register(IsSubscriber.class);
    
  }

  @Test(groups={"checkin"})
  public void testDoNotRegister() throws Exception {
    visitor.visit(NotASubscriber.class);

    verify(mockRegistry, never()).register(NotASubscriber.class);
  }
}
