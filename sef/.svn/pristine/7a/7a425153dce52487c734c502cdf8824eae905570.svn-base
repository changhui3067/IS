package com.successfactors.sef.service;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SEFExternalLinkTest {

  public static final String MANAGER_CHANGE = "ManagerChange";
  public static final String ORIGINAL_LINK = "/acme?fbacme_o=admin&pess_old_admin=true&eventtype=";

  @Test
  public void getExternalURL() {
    final moduleExternalLink linkAPI = new moduleExternalLink();
    final String link = linkAPI.getExternalURL(MANAGER_CHANGE);
    Assert.assertEquals(link, ORIGINAL_LINK + MANAGER_CHANGE);
  }

  private class moduleExternalLink implements SEFExternalLink {
    @Override
    public String getExternalURL(final String eventType) {
      return ORIGINAL_LINK + MANAGER_CHANGE;
    }
  }
}
