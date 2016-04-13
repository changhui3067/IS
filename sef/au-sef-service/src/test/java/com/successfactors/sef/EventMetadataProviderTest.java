package com.successfactors.sef;

import com.successfactors.sef.metadata.EventMetadata;
import com.successfactors.sef.metadata.EventMetadataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: I833237
 * Date: 5/13/15
 * Time: 10:29 PM
 */
public class EventMetadataProviderTest {

  public static final String MANAGER_CHANGE = "ManagerChange";

  @Test(groups = {"checkin"})
  public void testDeserialization() {
    Assert.assertEquals(EventMetadataProvider.getEventTypes().length, 1);

    EventMetadata metadata = EventMetadataProvider.getEventMetadata(MANAGER_CHANGE);
    Assert.assertEquals(metadata.getType(), MANAGER_CHANGE);
    Assert.assertEquals(metadata.getTopic(), "com.successfactors.Employment.JobInformation.ManagerChanged");
    Assert.assertEquals(metadata.getFilterParameters().length, 0);
//    Assert.assertEquals(metadata.getFilterParameters()[0], "param1");
    Assert.assertEquals(metadata.getEntity(), "JobInformation");
   // Assert.assertEquals(metadata.getSourceArea(), EventMetadata.SourceAreaType.MODULE_ECT);
    Assert.assertEquals(metadata.getParams().length, 1);
    Assert.assertEquals(metadata.getEntityKeys().length, 3);

    Assert.assertTrue(metadata.isEffectiveDated());
    Assert.assertTrue(metadata.isExternallyAllowed());
  }

}
