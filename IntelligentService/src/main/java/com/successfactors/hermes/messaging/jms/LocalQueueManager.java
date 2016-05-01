package com.successfactors.hermes.messaging.jms;

import java.util.ArrayList;
import java.util.List;

import org.hornetq.jms.client.HornetQQueue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;

import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;

/**
 * Local queue manager, mainly to support
 * client side queue lookup, it will dynamically register
 * queues into Spring Context based on MDP configuration
 * 
 * @author yyang
 *
 */
public class LocalQueueManager implements BeanDefinitionRegistryPostProcessor {

  /** LOGGER */
  private static final Logger LOGGER = LogManager.getLogger(LocalQueueManager.class);

  /** message driven pojos */
  private List<MessageDrivenPojo> messageDrivenPojos = new ArrayList<MessageDrivenPojo>();

  /**
   * getter of messageDrivenPojos
   * @return the messageDrivenPojos
   */
  public List<MessageDrivenPojo> getMessageDrivenPojos() {
    return messageDrivenPojos;
  }

  /**
   * setter of messageDrivenPojos
   * @param messageDrivenPojos the messageDrivenPojos to set
   */
  public void setMessageDrivenPojos(List<MessageDrivenPojo> messageDrivenPojos) {
    this.messageDrivenPojos = messageDrivenPojos;
  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
  }

  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
      throws BeansException {
    for (MessageDrivenPojo mdp : getMessageDrivenPojos()) {
      MessagingDestConfig messagingDestConfig = AnnotationUtils.findAnnotation(
          mdp.getClass(), MessagingDestConfig.class);
      if (messagingDestConfig != null) {
        String beanName = messagingDestConfig.destination();
        LOGGER.info(String.format(
            "Registering Queue[%s] into Spring context.", beanName));
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(HornetQQueue.class);
        builder.addConstructorArgValue(QueueDestResolver.resolveQueueName(beanName));
        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
      }
    }
  }

}
