package com.successfactors.hermes.config;

import com.successfactors.unittest.TestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.successfactors.hermes.core.Topic;
import com.successfactors.hermes.core.HermesException;
import com.successfactors.hermes.engine.SubscriptionManager;
import com.successfactors.hermes.config.test.IsSubscriber;

import static org.mockito.Mockito.*;

public class HermesProviderRegistryTest {
  private HermesProviderRegistry registry;
  private RegistryProvider mockDecoratedRegistry;
  private SubscriptionManager mockManager;

  @BeforeMethod(groups={"checkin"})
  public void setup() {
    mockDecoratedRegistry = mock(RegistryProvider.class);
    registry = new HermesProviderRegistry(mockDecoratedRegistry);

    mockManager = mock(SubscriptionManager.class);
    TestUtils.setField(registry, "subManager", mockManager);
  }

//  @Test(groups={"checkin"})
  public void testRegisterFailed() throws Exception {
    doThrow(new HermesException(new Throwable())).when(mockManager).addSubscriber(any(Topic.class), anyString());

    registry.register(IsSubscriber.class);

    verify(mockDecoratedRegistry, never()).register(IsSubscriber.class);
  }

  @Test(groups={"checkin"})
  public void testRegisterPass() throws Exception {
    registry.register(IsSubscriber.class);

//    verify(mockDecoratedRegistry).register(IsSubscriber.class);
  }

  @Test(groups={"checkin"})
  public void testStartInitialization() throws Exception {
    registry.startInitialization();
    registry.register(IsSubscriber.class);

    verify(mockDecoratedRegistry).startInitialization();
  }

  @Test(groups={"checkin"})
  public void testEndInitialization() throws Exception {
    registry.endInitialization();
    registry.register(IsSubscriber.class);

    verify(mockDecoratedRegistry).endInitialization();
  }

  @Test(groups={"checkin"}, expectedExceptions = IllegalArgumentException.class)
  public void testBadConstructorCall() throws Exception {
    new HermesProviderRegistry(null);
  }


}
