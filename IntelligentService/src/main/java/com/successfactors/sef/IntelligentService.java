package com.successfactors.sef;

import com.successfactors.seb.SebEvent;
import com.successfactors.seb.ServiceEventBus;
import com.successfactors.seb.ServiceEventBusEngine;
import com.successfactors.sef.bean.SubscriberConfigurationBeanProvider;
import com.successfactors.sef.interfaces.IntelligentServiceSubscriber;
import com.successfactors.sef.metadata.EventMedataProvider;
import com.successfactors.sef.metadata.EventMetadata;
import com.successfactors.sef.metadata.SubscriberMetadata;
import com.successfactors.sef.metadata.SubscriberMetadataProvider;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Xc on 2016/4/24.
 */
public class IntelligentService {

    private static IntelligentService instance;

    private ServiceEventBus seb;

    private IntelligentService() throws JMSException {
        seb = new ServiceEventBusEngine();
        // fetch event types from provider
        String[] eventTypes = EventMedataProvider.getEventMetaTypeList();
        // subscriber topic from seb
        for(String eventType : eventTypes){
            EventMetadata eventMetadata = EventMedataProvider.getEventMeta(eventType);
            SubscriberMetadata subscriberMetadata = SubscriberMetadataProvider.getSubscriberMetadata(eventType);
            seb.addSubscriber(eventMetadata.getTopic(),new IntelligentServiceListener(subscriberMetadata));
        }
    }



    public static IntelligentService getInstance(){
        if(instance ==null){
            synchronized (IntelligentService.class){
                if(instance ==null){
                    try {
                        instance = new IntelligentService();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public void publish(SefEvent event){
        try {
            SebEvent<Map<String,Object>> sebEvent = new SebEvent<Map<String,Object>>();
            sebEvent.setEventId(UUID.randomUUID().toString());
            sebEvent.setBody(event.getParams());
            seb.publish(event.getMeta().getTopic(),sebEvent);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic) {
        try {
            seb.publish(topic,new SebEvent());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static class IntelligentServiceListener implements MessageListener{
        private SubscriberMetadata subscriberMetadata;

        public IntelligentServiceListener(SubscriberMetadata subscriberMetadata){
            setSubscriberMetadata(subscriberMetadata);
        }

        @Override
        public void onMessage(Message message) {
            try {
                SefEvent event = getSefEvent(message);
                for(SubscriberMetadata.Subscriber subscriber : subscriberMetadata.getSubscribers()){
                    String id = subscriber.getId();//TODO get enabled using subscriberType
                    if(!SubscriberConfigurationBeanProvider.getInstance().isEnabled("A",subscriber.getId())){
                        continue;
                    }
                    String className = subscriber.getListenerName();
                    System.out.println(subscriber.getListenerName());
                    try {
                        Class clazz = Class.forName(className);
                        IntelligentServiceSubscriber iss = (IntelligentServiceSubscriber) clazz.newInstance();
                        if(iss.checkPermission()){
                            iss.onEvent(event);
                        }
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }

        private SefEvent getSefEvent(Message message) throws JMSException {
            SebEvent event = (SebEvent) ((ObjectMessage)message).getObject();
            SefEvent sefEvent = new SefEvent();
            sefEvent.setParams((Map<String,Object>)event.getBody());
            return sefEvent;
        }

        public SubscriberMetadata getSubscriberMetadata() {
            return subscriberMetadata;
        }

        public void setSubscriberMetadata(SubscriberMetadata subscriberMetadata) {
            this.subscriberMetadata = subscriberMetadata;
        }
    }
}
