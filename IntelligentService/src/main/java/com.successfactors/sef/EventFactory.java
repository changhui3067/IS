package com.successfactors.sef;

import com.successfactors.sef.metadata.EventMedataProvider;
import com.successfactors.sef.metadata.EventMetadata;

import java.util.Map;

/**
 * Created by Xc on 2016/4/25.
 */
public class EventFactory {

    public static SefEvent getEvent(String eventType, Map<String,Object> params){
        EventMetadata metadata = EventMedataProvider.getEventMeta(eventType);
        return new SefEvent(metadata,params);
    }
}
