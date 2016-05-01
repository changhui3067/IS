package com.successfactors.hermes.messaging.jms;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;

import org.springframework.jms.support.destination.DynamicDestinationResolver;

import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;

/**
 * Queue destination resolver
 * @author yyang
 *
 */
public class QueueDestResolver extends DynamicDestinationResolver {

  /** LOGGER */
  private static final Logger LOGGER = LogManager.getLogger(QueueDestResolver.class);

  /** queue destination */
  private String destination;

  /**
   * resolve queue name
   * @param destination destination
   * @return queue name
   */
  public static String resolveQueueName(String destination) {
    return destination.split("/")[1];
  }

  /**
   * constructor
   * @param destination destination
   */
  public QueueDestResolver(String destination) {
    this.destination = destination;
  }

  @Override
  protected Queue resolveQueue(Session session, String queueName) throws JMSException {
    try {
      ExtEmbeddedJms embeddedJms = ExtEmbeddedJms.getExtEmbeddedJms();
      embeddedJms.getJmsServerManager().createQueue(
          false, resolveQueueName(destination), null, false, destination);
      return session.createQueue(queueName);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }
    return null;
  }
}
