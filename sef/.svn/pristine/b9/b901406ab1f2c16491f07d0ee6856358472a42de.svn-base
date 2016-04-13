package com.successfactors.sef;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.successfactors.sef.metadata.NotificationMetadata;
import com.successfactors.sef.metadata.NotificationMetadataProvider;

public class NotificationMetadataProviderTest {

  public static final String MANAGER_CHANGE = "ManagerChange";

  @Test(groups = { "checkin" })
  public void testDeserialization() {
    Assert.assertEquals(NotificationMetadataProvider.getEventTypes().length, 5);

    final NotificationMetadata metadata = NotificationMetadataProvider
        .getNotificationMetadata(MANAGER_CHANGE);
    Assert.assertEquals(metadata.getType(), MANAGER_CHANGE);
    Assert.assertEquals(metadata.getNotifications().length, 1);
  }

}
