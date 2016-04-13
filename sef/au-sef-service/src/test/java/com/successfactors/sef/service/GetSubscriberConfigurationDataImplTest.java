package com.successfactors.sef.service;

import static org.mockito.Mockito.mock;

import java.util.List;

import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sef.bean.SEFSubscriberConfigurationVO;
import com.successfactors.sef.service.impl.GetSubscriberConfigurationDataImpl;

public class GetSubscriberConfigurationDataImplTest {

  private static final Long BATCH_ID = 1L;

  @BeforeMethod(groups = { "checkin" })
  public void initialize() {
    MockitoAnnotations.initMocks(this);
  }

  @Test(groups = { "checkin" })
  public void testGetSubscriberConfigurationDataImpl()
      throws ServiceApplicationException {

    final GetSubscriberConfigurationDataImpl objectUnderTest = new GetSubscriberConfigurationDataImpl();

//    // mock creation
//    final GetSubscriberConfigurationData mockCmd = mock(GetSubscriberConfigurationData.class);
//    // stubbing
//
//    final List<SEFSubscriberConfigurationVO> resultMap = objectUnderTest
//        .execute(mockCmd);
//
//    Assert.assertEquals(resultMap.size(), 17);

  }

}
