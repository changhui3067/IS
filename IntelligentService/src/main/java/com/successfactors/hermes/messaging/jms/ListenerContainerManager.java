package com.successfactors.hermes.messaging.jms;

import static com.successfactors.hermes.messaging.SpringMessagingContext.LOCAL_CONN_FACTORY;
import static com.successfactors.hermes.messaging.jms.MessageDrivenPojo.NONDURABLE;
import static com.successfactors.hermes.messaging.jms.MessageDrivenPojo.QUEUE;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;

import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;

/**
 * Message listener container manager
 * @author yyang
 *
 */
public class ListenerContainerManager implements BeanDefinitionRegistryPostProcessor {

  /** LOGGER */
  private static final Logger LOGGER = LogManager.getLogger(ListenerContainerManager.class);

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
      LOGGER.info(String.format(
          "Registering message listener container for MDP[%s].", mdp.getClass().getCanonicalName()));
      String beanName = mdp.getClass().getName();
      BeanDefinitionBuilder builder = createContainerBeanBuilder(mdp);
      if (builder != null) {
        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
      }
    }
  }

  private BeanDefinitionBuilder createContainerBeanBuilder(MessageDrivenPojo mdp) {
    BeanDefinitionBuilder builder = null;
    MessagingDestConfig messagingDestConfig = AnnotationUtils.findAnnotation(
        mdp.getClass(), MessagingDestConfig.class);
    if (messagingDestConfig != null) {
      builder = BeanDefinitionBuilder.genericBeanDefinition(ManageableListenerContainer.class);

      // associate with message driven pojo
      builder.addPropertyValue("messageListener", mdp);

      // associate with connection factory
      builder.addPropertyReference("connectionFactory", LOCAL_CONN_FACTORY);
      
      // specify destination
      String destination = messagingDestConfig.destination();
      builder.addPropertyValue("destinationName", QueueDestResolver.resolveQueueName(destination));
      builder.addPropertyValue("destinationResolver", new QueueDestResolver(destination));
      
      // only support P2P domain
      if (!QUEUE.equalsIgnoreCase(messagingDestConfig.destType())) {
        LOGGER.warn(String.format(
            "Destination type[%s] is not supported, changed to [%s] for [%s].",
            messagingDestConfig.destType(), QUEUE, mdp.getClass().getName()));
      }
      builder.addPropertyValue("pubSubDomain", false);
      
      // only support non-durable processing
      if (!NONDURABLE.equalsIgnoreCase(messagingDestConfig.duribility())) {
        LOGGER.warn(String.format(
            "Destination duribility[%s] is not supported, changed to [%s] for [%s].",
            messagingDestConfig.duribility(), NONDURABLE, mdp.getClass().getName()));
      }
      builder.addPropertyValue("subscriptionDurable", false);
      
      configureListenerDefaults(builder);
    }
    return builder;
  }

  private void configureListenerDefaults(BeanDefinitionBuilder builder) {
    builder.addPropertyValue("maxConcurrentConsumers", 15);
    builder.addPropertyValue("idleTaskExecutionLimit", 30000L);
  }
}
