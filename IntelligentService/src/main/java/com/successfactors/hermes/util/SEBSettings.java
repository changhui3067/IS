package com.successfactors.hermes.util;

import com.sf.sfv4.util.StringUtils;

public class SEBSettings {

  //According to OPS's suggestion, still use the configuration file to setup rest subscriber when CONFIG_UI_READONLY is true.
  public static boolean useConfigurationFileFirst() {
    return StringUtils.getBoolean(System.getProperty(SEBConstants.CONFIG_UI_READONLY), false);
  }
}
