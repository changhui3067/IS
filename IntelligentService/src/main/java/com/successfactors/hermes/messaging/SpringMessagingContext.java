package com.successfactors.hermes.messaging;

import static com.successfactors.platform.SpringContextAccessor.getBean;
import static com.successfactors.platform.SpringContextAccessor.getContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;

/**
 * Spring delegated messaging context
 * @author yyang
 *
 */
public class SpringMessagingContext extends InitialContext {

  /** LOGGER */
  private static final Logger LOGGER = LogManager.getLogger(SpringMessagingContext.class);

  /** local connection factory */
  public static final String LOCAL_CONN_FACTORY = "java:/ConnectionFactory";

  /** local ha connection factory */
  public static final String LOCAL_HA_CONN_FACTORY = "java:/JmsXA";

  /** remote connection factory */
  public static final String REMOTE_CONN_FACTORY = "ConnectionFactory";

  /** JNDI context */
  private Context jndiContext;

  /** indicator of remote lookup */
  private boolean remote;

  /**
   * constructor
   * @param jndiContext jndiContext
   * @throws NamingException NamingException
   */
  public SpringMessagingContext(Context jndiContext) throws NamingException {
    if (jndiContext instanceof SpringMessagingContext) {
      this.jndiContext = ((SpringMessagingContext) jndiContext).getJndiContext();
    } else {
      this.jndiContext = jndiContext;
    }
  }

  /**
   * Constructor with remote option
   * @param jndiContext jndiContext
   * @param remote remote
   * @throws NamingException NamingException
   */
  public SpringMessagingContext(Context jndiContext, boolean remote)
      throws NamingException {
    this(jndiContext);
    this.remote = remote;
  }

  @Override
  public Object lookup(String name) throws NamingException {
    if (Boolean.valueOf(System.getProperty(
        MessagingConfiguration.PROP_SPRINGJMS_ON_HORNETQ))) {
      try {
        if (remote && REMOTE_CONN_FACTORY.equalsIgnoreCase(name)) {
          LOGGER.info("Remotely looking up ConnectionFactory ...");
          
          String jndiUrl = (String) getJndiContext().getEnvironment().get(PROVIDER_URL);
          LOGGER.info(String.format("Configured JNDI URL is [%s].", jndiUrl));
          String targetServer = jndiUrl.split(":")[0];
          LOGGER.info(String.format("Target server is [%s].", targetServer));
          return getContext().getBean(name, targetServer);
        }
        return getBean(name);
      } catch (NoSuchBeanDefinitionException e) {
        throw new NamingException(e.getMessage());
      }
    }
    return jndiContext.lookup(name);
  }

  /**
   * getter of jndiContext
   * @return the jndiContext
   */
  public Context getJndiContext() {
    return jndiContext;
  }

}
