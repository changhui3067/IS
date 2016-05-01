package com.successfactors.hermes.config;

import EventCenter.hermes.core.SFSubscriber;
import EventCenter.hermes.core.annotation.Subscriber;
import EventCenter.hermes.util.SEBLog;
import com.successfactors.xi.util.resource.ClassVisitor;
import com.successfactors.logging.api.Logger;

/**
 * Subscriber Visitor implementation for resource scanner.
 */
class SubscriberConfigVisitor extends ClassVisitor {
  /** Logger */
  private static final Logger log = SEBLog.getLogger(SubscriberConfigVisitor.class);
  
  /** registry */
  private RegistryProvider registry;

  /** subscriber type */
  private String subType;

  /** supported extensions */
  private String[] exts = {"*.class"};
  /**
   * Creates a new <code>SubscriberConfigVisitor</code> instance.
   *
   */
  public SubscriberConfigVisitor(RegistryProvider registry, String subType) {
    if (log.isDebugEnabled()) {
      log.debug("Calling constructor with registry: " + registry);
    }
    this.registry = registry;
    this.subType = subType;
  }

  /**
   * {@inheritDoc}
   */
  public String[] getSupportedFileExts() {
    if (log.isDebugEnabled()) {
      log.debug("calling getSupportedFileExts");
    }
    return exts;
  }

  /**
   * {@inheritDoc}
   */
  public void visit(Class clazz) {
    if (log.isDebugEnabled()) {
      log.debug("Visiting class " + clazz.getName());
    }

    Subscriber an = (Subscriber) clazz.getAnnotation(Subscriber.class);

    if (null != an) {
      // only register classes implementation SFSubscriber interface
      if (SFSubscriber.class.isAssignableFrom(clazz)
          && (subType != null && subType.equalsIgnoreCase(an.type()))) {
        registry.register(clazz);
      }
    }
  }
}
