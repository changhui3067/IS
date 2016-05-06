package com.successfactors.sef.service;

import com.successfactors.sca.ServiceCommand;
import com.successfactors.sef.metadata.SubscriberMetadata;

/**
 * Created by Xc on 2016/4/25.
 *
 * get subscriber definition from the event type
 */
public class GetSubscriberMeta implements ServiceCommand<SubscriberMetadata> {
    private String eventType;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public GetSubscriberMeta(String eventType){
        this.eventType = eventType;
    }
}
