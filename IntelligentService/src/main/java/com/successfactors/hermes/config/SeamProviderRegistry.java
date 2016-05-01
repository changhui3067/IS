package com.successfactors.hermes.config;

import EventCenter.hermes.util.SEBLog;
import javax.servlet.ServletContext;
import com.successfactors.logging.api.Logger;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.contexts.Context;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.contexts.Lifecycle;
import org.jboss.seam.init.ComponentDescriptor;

/**
 * Manally register to Seam for class without Name annotation.
 */
class SeamProviderRegistry implements RegistryProvider {
  /** Logger. */
  private static final Logger log = SEBLog.getLogger(SeamProviderRegistry.class);
  
  /** servletContext */
  private ServletContext servletContext = null;

  /**
   * Constructor.
   *
   * @param context a <code>ServletContext</code> value
   */
  SeamProviderRegistry(ServletContext context) {
    servletContext = context;
  }

  /** {@inheritDoc} */
  public void startInitialization() {
    Lifecycle.beginInitialization(servletContext);
  }

  /** {@inheritDoc} */
  public void endInitialization() {
    Lifecycle.endInitialization();
  }

  /** {@inheritDoc} */
  public void register(Class clazz) {
    Name name = (Name)clazz.getAnnotation(Name.class);

    // only manually register seam class when it's not already annotated
    if (null == name) {
      String id = clazz.getName();
      String componentName = id + ".component";

      Context context = Contexts.getApplicationContext();
      ComponentDescriptor descriptor = new ComponentDescriptor(id,
                                                               clazz,
                                                               ScopeType.EVENT,
                                                               Boolean.TRUE /* autoCreate */,
                                                               id /* jndi name */,
                                                               Boolean.FALSE /* installed */,
                                                               Install.APPLICATION /* precedence */);

      
      if (log.isDebugEnabled()) {
        log.debug("Manually creating component "
                  + descriptor.getName()
                  +" "+ descriptor.getScope()
                  +" "+ descriptor.getJndiName()
                  +" isAutoCreate="+descriptor.isAutoCreate());
      }

      Component component = new Component(descriptor.getComponentClass(),
                                          descriptor.getName(),
                                          descriptor.getScope(),
                                          descriptor.getJndiName());
      context.set(componentName, component);
    }
  }
}
