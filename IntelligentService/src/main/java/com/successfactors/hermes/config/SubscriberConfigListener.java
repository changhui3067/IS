package com.successfactors.hermes.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import EventCenter.hermes.impl.SEBStatus;
import EventCenter.hermes.util.JmxUtil;
import EventCenter.hermes.util.SEBConstants;
import EventCenter.hermes.util.SEBLog;
import com.successfactors.logging.api.Logger;

/**
 * Webapp listener to setup subscriber related objects
 *
 */
public class SubscriberConfigListener implements ServletContextListener {
  /** Logger */
  private static final Logger log = SEBLog.getLogger(SubscriberConfigListener.class);
  

  /** property for enable subrcriber regristration */
  public static final String SUBSCRIBER_REG_ENABLED_PROP = SEBConstants.SUB_ENABLEMENT_PROPERTY;

  /**
   * {@inheritDoc}
   */
  public void contextInitialized(ServletContextEvent event) {
//    boolean subscriberRegEnabled = 
//      StringUtils.getBoolean(System.getProperty(SUBSCRIBER_REG_ENABLED_PROP), false);

    JmxUtil.register(new SEBStatus(), "ServiceEventBusStatus");

    initEventDef(event.getServletContext());

//    if (subscriberRegEnabled) {
      initSubscriber(event.getServletContext());
//    }
//    else {
//      log.info("JVM flag hermes.subscriberRegEnable is not set or set to false, will not be registering subscribers.");
//    }
  }

  /**
   * Starting initialization of subscriber
   *
   * @param e a <code>ServletContextEvent</code> value
   */
  public void contextDestroyed(ServletContextEvent e) {
  }

  private static void initEventDef(ServletContext servletContext) {
    log.info("Start loading event definitions...");
    EventDefConfig config = new EventDefConfig(new EventDefRegistryProvider());
    config.configure();
    log.info("End loading event definitions.");
  }

  private static void initSubscriber(ServletContext servletContext) {
    RegistryProvider registry = 
      new HermesProviderRegistry(new SeamProviderRegistry(servletContext));
    
    log.info("Start registering subscriber....");
    SubscriberConfig config = new SubscriberConfig(registry);
    config.configure();
    log.info("End registering subscriber.");
  }
}
