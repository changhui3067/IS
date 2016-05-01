package com.successfactors.hermes.messaging.jms;

import org.hornetq.integration.spring.SpringJmsBootstrap;
import org.hornetq.jms.server.JMSServerManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Extended Embedded JMS server
 * @author yyang
 *
 */
public class ExtEmbeddedJms extends SpringJmsBootstrap implements ApplicationContextAware {

  /** Spring context */
  private static ApplicationContext CONTEXT;
  
  /**
   * get JMS server manager
   * @return JMS server manager
   */
  public JMSServerManager getJmsServerManager() {
    return serverManager;
  }

  @Override
  public void setApplicationContext(ApplicationContext cnt)
      throws BeansException {
    CONTEXT = cnt;
  }
  
  public static ExtEmbeddedJms getExtEmbeddedJms() {
    return CONTEXT.getBean(ExtEmbeddedJms.class);
  }

}
