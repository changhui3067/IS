package com.successfactors.sef.service;

import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sef.bean.SEFEventListVO;
import com.successfactors.sef.service.impl.GetEventMetaDataImpl;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;

public class GetEventMetaDataImplTest {

  private static final Long BATCH_ID = 1L;

  //
  // @Mock
  // private Connection mockDBConnection;
  // @Mock
  // private GenericEventJDBCDAO mockGenericEventJDBCDAO;

  @BeforeMethod(groups = { "checkin" })
  public void initialize() {
    MockitoAnnotations.initMocks(this);
  }

  @Test(groups = { "checkin" })
  public void testGetEventMetaDataImpl() throws ServiceApplicationException {

    final GetEventMetaDataImpl objectUnderTest = new GetEventMetaDataImpl();

    // mock creation
    final GetEventMetaData mockCmd = mock(GetEventMetaData.class);
    // stubbing

    final SEFEventListVO resultList = objectUnderTest.execute(mockCmd);

    Assert.assertEquals(resultList.getEvents().size(), 1);

  }

}
