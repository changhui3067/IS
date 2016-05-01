package com.successfactors.hermes.messaging.jms;

import java.util.HashMap;
import java.util.Map;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.hornetq.jms.client.HornetQQueueConnectionFactory;
import org.springframework.jms.connection.DelegatingConnectionFactory;

/**
 * remote delegated queue connection factory
 * @author yyang
 *
 */
public class RemoteQueueConnectionFactory extends DelegatingConnectionFactory {

  /**
   * constructor
   * @param server server
   */
  public RemoteQueueConnectionFactory(String server) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put(TransportConstants.HOST_PROP_NAME, server);
    params.put(TransportConstants.PORT_PROP_NAME, TransportConstants.DEFAULT_PORT);
    TransportConfiguration transport = new TransportConfiguration(
        NettyConnectorFactory.class.getName(), params);
    HornetQConnectionFactory targetConnectionFactory = HornetQJMSClient
        .createConnectionFactoryWithoutHA(JMSFactoryType.QUEUE_CF, transport);
    setTargetConnectionFactory((HornetQQueueConnectionFactory) targetConnectionFactory);
  }

}
