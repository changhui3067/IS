package com.successfactors.sef.metadata;

/**
 * Created by Xc on 2016/4/24.
 */
public class SubscriberMetadata {

    //target event id
    private String targetEventType;
    private Subscriber[] subscribers;

    public Subscriber[] getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Subscriber[] subscribers) {
        this.subscribers = subscribers;
    }

    public static class Subscriber{

        //listener to execute onEvent
        private String listenerName;

        //subscriber id unique id
        private String id;

        public String getListenerName() {
            return listenerName;
        }

        public void setListenerName(String listenerName) {
            this.listenerName = listenerName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public String getTargetEventType() {
        return targetEventType;
    }

    public void setTargetEventType(String targetEventType) {
        this.targetEventType = targetEventType;
    }


}
