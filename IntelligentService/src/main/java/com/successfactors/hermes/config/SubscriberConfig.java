package com.successfactors.hermes.config;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import com.sf.sfv4.util.StringUtils;

import EventCenter.hermes.util.SEBLog;
import com.successfactors.logging.api.Logger;
import com.successfactors.xi.util.PropUtils;
import com.successfactors.xi.util.resource.ResourceScanner;

/**
 * Class to configure all sbuscriber, by adding it to registries.
 */
class SubscriberConfig {
  /** Logger */
  private static final Logger log = SEBLog.getLogger(SubscriberConfig.class);
  
  /** Provider Registry */
  private RegistryProvider providerRegistry;

  /** Default property file to look for subscriber implementations */
  private static final String DEFAULT_CONFIG_FILE = "subscriberConfig.properties";

  /** properties key for subscriber package for scanning. */
  private static final String SUBSCRIBER_PACKAGE="SUBSCRIBER";

  private String configFile = DEFAULT_CONFIG_FILE;

  /**
   * system property to indicate subscribers of certain types to be 
   * activated during underlying server starting up
   */
  private static final String SUBSCRIBER_TYPES_TO_ACTIVATE = "hermes.subscriber.activate";

  /**
   * Creates a new <code>SubscriberConfig</code> instance.
   */
  SubscriberConfig(RegistryProvider registry) {
    if (null == registry) {
      throw new IllegalArgumentException("Provider registry cannot be null.");
    }
    this.providerRegistry = registry;
  }

  void configure() {
    configure(configFile);
  }

  private void configure(String newConfigFile) {
    Enumeration<URL> resources = null;

    try {
      resources = Thread.currentThread().getContextClassLoader()
          .getResources(newConfigFile);
      if (null == resources || !resources.hasMoreElements()) {
        log.error("Failed to find " + newConfigFile);
      }
    }
    catch (IOException e) {
      log.error("IO Error while loading " + newConfigFile);
    }

    while (resources.hasMoreElements()) {
      URL resource = resources.nextElement();
      scanFileForResource(resource);
    }
  }

  private void scanFileForResource(URL resource) {
    Properties props = null;
    try {
      props = PropUtils.loadProps(resource, false);
    } catch (IOException e) {
      log.error("IOException while loading properties from " + resource, e);
    }

    String toBeActivatedSubTypes = System.getProperty(
        SUBSCRIBER_TYPES_TO_ACTIVATE, "");
    List<String> subTypeList = StringUtils.getListFromString(
        toBeActivatedSubTypes, ",");
    try {
      providerRegistry.startInitialization();
      if (subTypeList.isEmpty()) {
        scanSubscriberByType(resource, props, SUBSCRIBER_PACKAGE, "");
      } else {
        for (String subType : subTypeList) {
          scanSubscriberByType(resource, props, SUBSCRIBER_PACKAGE, subType);
        }
      }
    } catch (IOException e) {
      log.error("Failed to scan classes " + e.getMessage());
    } finally {
      providerRegistry.endInitialization();
    }
  }

  private void scanSubscriberByType(URL resource, Properties props,
      String patternKey, String subType) throws IOException {
    StringBuilder propErrors = new StringBuilder();

    if (log.isDebugEnabled()) {
      log.debug("scan path is: "
          + PropUtils.getReqProperty(props, patternKey, propErrors));
    }

    if (!propErrors.toString().isEmpty()) {
      // skip scanning for not configured entries
      log.debug(String.format("No entries found for key[%s] in [%s].", 
          propErrors, resource));
      return;
    }

    SubscriberConfigVisitor visitor = new SubscriberConfigVisitor(
        providerRegistry, subType);
    ResourceScanner scanner = new ResourceScanner(resource,
        PropUtils.getReqProperty(props, patternKey, propErrors), visitor);

    if (log.isDebugEnabled()) {
      log.debug("scanner is: " + scanner);
    }

    if (propErrors.length() > 0) {
      log.error(configFile + " is missing required properties: " + propErrors);
    }

    if (log.isDebugEnabled()) {
      log.debug("starting scanner");
    }
    scanner.scan();
  }
}
