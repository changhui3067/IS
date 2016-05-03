package com.successfactors.sef.metadata;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xc on 2016/4/24.
 */
public class EventMedataProvider {
    private static Map<String,EventMetadata> eventMetadataMap;
    private static final String EVENT_METADATA_FILE = "events.json";
    static {
        init();
    }

    private static void init() {
        eventMetadataMap = new HashMap<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            File file = new File(EVENT_METADATA_FILE);
            InputStream is = new FileInputStream(file);
            EventMetadata[] metas = mapper.readValue(is, EventMetadata[].class);
            for (EventMetadata meta : metas) {
                String type = meta.getType();
                if (type == null || type.isEmpty()) {
                    continue;
                }
                eventMetadataMap.put(type, meta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //TODO getEventMetas from conf file/db
//        EventMetadata eventMetadata = new EventMetadata();
//        eventMetadata.setId("com.addEmp");
//        eventMetadata.setTopic("com.addEmp");
//
//        EventMetadata eventMetadata1 = new EventMetadata();
//        eventMetadata1.setId("com.addEmp1");
//        eventMetadata1.setTopic("com.addEmp1");
//
//        eventMetadataMap.put("com.addEmp",eventMetadata);
//        eventMetadataMap.put("com.addEmp1",eventMetadata1);
    }

    public static String[] getEventMetaTypeList(){
        return eventMetadataMap.keySet().toArray(new String[eventMetadataMap.keySet().size()]);
    }

    public static EventMetadata getEventMeta(String eventType){
        if(eventType ==null||eventType.isEmpty()){
            return null;
        }

        return eventMetadataMap.get(eventType);
    }
}
