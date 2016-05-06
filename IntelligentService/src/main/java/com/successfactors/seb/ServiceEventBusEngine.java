package com.successfactors.seb;


import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.*;

/**
 * Service event bus implementation of the ServiceEventBus interface.
 *
 * @author ddiodati
 */
public class ServiceEventBusEngine implements ServiceEventBus {

//    Things you might do after running this example:
//    Setup a broker instead of using the org.activemq.broker.impl.Main class directly
//    Use JNDI to lookup a javax.jms.ConnectionFactory rather than creating ActiveMQConnectionFactory directly.
//    Implement the javax.jms.MessageListener interface rather than calling consumer.receive()
//    Use transactional sessions
//    Use a Topic rather than a queue

    private String clientId= "EventCenter" +UUID.randomUUID();
    private Connection connection;
    private Session publishSession;
    private Session subscribeSession;
    private Map<String, MessageProducer> producerMap = new HashMap<>();
    private Map<UUID, MessageConsumer> consumerMap = new HashMap<>();

    public ServiceEventBusEngine() throws JMSException {
        init();
    }


    public void init() throws JMSException {
        // create a Connection Factory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_BROKER_URL);
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);
        connection.start();
        // create a Session
        publishSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        subscribeSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }


    private MessageProducer getMessageProducer(String topicName) throws JMSException {
        MessageProducer producer = producerMap.get(topicName);
        if (producer == null) {
            producer = publishSession.createProducer(getPublishTopic(topicName));
            producerMap.put(topicName,producer);
        }

        return producer;
    }


    private Topic getPublishTopic(String topicName) throws JMSException {
        return publishSession.createTopic(topicName);
    }

    private Topic getSubscriberTopic(String topicName) throws JMSException {
        return subscribeSession.createTopic(topicName);
    }


    private UUID generateUUID() {
        return UUID.randomUUID();
    }


    private boolean isBlank(String s) {
        return "".equals(s) || s == null;
    }

    @Override
    public void publish(String topic, SebEvent evt) throws JMSException {
        ObjectMessage objectMessage = publishSession.createObjectMessage();
        convert(evt,objectMessage);
        getMessageProducer(topic).send(objectMessage);
    }

    private void convert(SebEvent evt, ObjectMessage objectMessage) throws JMSException {
        objectMessage.setObject(evt);
        objectMessage.setJMSPriority(evt.getPriority());
    }

    public void publish(String topic, String evtStr) throws JMSException {
        TextMessage textMessage = publishSession.createTextMessage(evtStr);
        getMessageProducer(topic).send(textMessage);
    }

    @Override
    public UUID addSubscriber(String topicName, MessageListener subscriber) throws JMSException {
        UUID uuid = generateUUID();

        MessageConsumer consumer = subscribeSession.createConsumer(getSubscriberTopic(topicName));

        consumer.setMessageListener(subscriber);

        consumerMap.put(uuid,consumer);

        return uuid;
    }

    @Override
    public void removeSubscriber(UUID uuid) {
        try {
            consumerMap.get(uuid).close();
            consumerMap.remove(uuid);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
