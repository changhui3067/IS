package com.successfactors.hermes.config;

import com.sf.sfv4.util.StringUtils;

import EventCenter.hermes.core.Topic;
import EventCenter.hermes.core.HermesException;
import com.successfactors.hermes.metadata.SubscriberMetaDataManager;
import com.successfactors.hermes.engine.SubscriptionManager;
import com.successfactors.hermes.engine.ProviderFactory;
import com.successfactors.hermes.engine.ProviderFactoryCreator;
import com.successfactors.hermes.engine.ProviderFactoryCreator.FactoryType;
import EventCenter.hermes.core.annotation.Subscriber;
import EventCenter.hermes.util.SEBLog;
import com.successfactors.logging.api.Logger;

/**
 * Register for Hermes subscriber registry.
 */
class HermesProviderRegistry extends AbstractRegistryDecorator {
  /** Logger */
  private static final Logger log = SEBLog.getLogger(HermesProviderRegistry.class);
  
  private SubscriptionManager subManager = null;

  /**
   * Constructor.
   *
   * @param registry a <code>SubscriberProviderRegistry</code> value
   */
  HermesProviderRegistry(RegistryProvider registry) {
    super(registry);

    ProviderFactory factory = 
      ProviderFactoryCreator.getFactoryInstance(FactoryType.server);
    subManager = factory.getSubscriptionManager();
  }

  /** {@inheritDoc} */
  public void startInitialization() {
    super.startInitialization();
  }

  /** {@inheritDoc} */
  public void endInitialization() {
    super.endInitialization();
  }

  /** {@inheritDoc} */
  public void register(Class clazz) {

    Subscriber an = (Subscriber)clazz.getAnnotation(Subscriber.class);

    Topic topic = new Topic(an.topic());

    try {
      if (log.isDebugEnabled()) {
        log.debug(String.format("Adding topic: %s for subscriber: %s to hermes"
                                ,topic 
                                ,clazz.getName()));
      }
      // first register in super provider
      boolean subscriberRegEnabled = 
          StringUtils.getBoolean(System.getProperty(SubscriberConfigListener.SUBSCRIBER_REG_ENABLED_PROP), false);

      if (subscriberRegEnabled) {
        super.register(clazz);
        subManager.addSubscriber(topic, clazz.getName());
      }
      else {
        log.info("JVM flag hermes.subscriberRegEnable is not set or set to false, will not be registering subscribers.");
      }
      SubscriberMetaDataManager.scanMetaData(clazz);

    }
    catch (HermesException e) {
      log.error("Exception registering " + clazz.getName() + " to hermes.", e);
    }
  }
}
