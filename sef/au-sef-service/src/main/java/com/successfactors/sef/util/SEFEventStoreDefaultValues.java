package com.successfactors.sef.util;

import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;

/**
 * Default Values for Standard Event Manager
 * 
 * @author echen
 */
public class SEFEventStoreDefaultValues {

  /** Logger */
  private static final Logger logger = LogManager
      .getLogger(SEFEventStoreDefaultValues.class.getSimpleName());

  /**
   * Default Activiti schema name.
   */
  private static final String DEFAULT_NAME = "SEF.";

  /**
   * SEF schema name. Should be ended with a dot.
   */
  public static final String SEF_EVENTSTORE_SCHEMA;

  /**
   * Flag to enable/disable event store
   */
  public static final boolean EVENTSTORE_SUPPORT;

  static {

    final String defaultSchema = System.getProperty("db.schema") + DEFAULT_NAME;

    String sefSchema = System.getProperty("com.successfactors.sef.schema",
        defaultSchema);
    if (!sefSchema.endsWith(".")) {
      sefSchema = sefSchema.concat(".");
    }

    final boolean eventStoreSupport = Boolean.parseBoolean(System.getProperty(
        "com.successfactors.sef.eventstoreSupport", "false"));

    SEF_EVENTSTORE_SCHEMA = sefSchema;

    EVENTSTORE_SUPPORT = eventStoreSupport;

    logger.info("Maestro.Activiti.Driver.Config={engineSchema=" + sefSchema
        + ", eventStoreSupport=" + eventStoreSupport + "}");

  }

  /**
   * Private CTOR.
   */
  private SEFEventStoreDefaultValues() {
  }

}