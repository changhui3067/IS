package com.successfactors.hermes.core;

import java.security.Principal;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.successfactors.db.CompanyDatabaseContext;
import com.successfactors.hermes.engine.ServiceEventBusEngine;
import com.successfactors.unittest.TestUtils;

public class ServiceEventBusFactoryTest {
  
  @Test(groups={"checkin"})
  public void testServiceEventBusCreate() {
    
    ServiceEventBusFactory sebf = new ServiceEventBusFactory();
    CompanyDatabaseContext dummyCompany = new DummyDatabaseContext();
    Principal dummyP = new DummyPrincipal();
    
    TestUtils.setField(sebf, "company", dummyCompany);
    TestUtils.setField(sebf, "params", dummyP);
    
      ServiceEventBus seb = sebf.getInstance();
      
     Assert.assertEquals(seb.getClass().getName(), new ServiceEventBusEngine("SEBCOMPANY","SEBUSER").getClass().getName());
     
     Assert.assertSame(TestUtils.getField(seb,"company"),"SEBCOMPANY");
     Assert.assertSame(TestUtils.getField(seb, "user"), "SEBUSER");
     
    }

    
  private static final class DummyDatabaseContext implements CompanyDatabaseContext {

    public String getCompanyId() {
      // TODO Auto-generated method stub
      return "SEBCOMPANY";
    }

    public String getSchema() {
      // TODO Auto-generated method stub
      return null;
    }

    public String getPoolId() {
      // TODO Auto-generated method stub
      return null;
    }

    public boolean isVpd() {
      // TODO Auto-generated method stub
      return false;
    }

    public String getAuditPoolId() {
      // TODO Auto-generated method stub
      return null;
    }
    
  }
  
  
  private static final class DummyPrincipal implements Principal {

    public String getName() {
      return "SEBUSER";
    }
    
  }
  
}
