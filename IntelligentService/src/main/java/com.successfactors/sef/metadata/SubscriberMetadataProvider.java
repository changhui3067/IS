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
public class SubscriberMetadataProvider {
    static {
        init();
    }

    private static Map<String,SubscriberMetadata> subscriberMetadataMap;

    private static final String SUBSCRIBER_METADATA_FILE = "subscriber.json";

    private static void init() {
        subscriberMetadataMap = new HashMap<>();
        //TODO getSubscriberMetadata from db/conf file
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            File file = new File(SUBSCRIBER_METADATA_FILE);
            InputStream is = new FileInputStream(file);
            SubscriberMetadata[] metas = mapper.readValue(is, SubscriberMetadata[].class);
            for (SubscriberMetadata meta : metas) {
                String type = meta.getTargetEventType();
                if (type == null || type.isEmpty()) {
                    continue;
                }
                subscriberMetadataMap.put(type, meta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


//        SubscriberMetadata subscriberMetadata = new SubscriberMetadata();
//        SubscriberMetadata.Subscriber s0 = new SubscriberMetadata.Subscriber();
//        s0.setId("s0");
//        s0.setListenerName("com.successfactors.mock.MockListener");
//        SubscriberMetadata.Subscriber s1 = new SubscriberMetadata.Subscriber();
//        s1.setId("s1");
//        s1.setListenerName("com.successfactors.mock.MockListener");
//        SubscriberMetadata.Subscriber s2 = new SubscriberMetadata.Subscriber();
//        s2.setId("s2");
//        s2.setListenerName("com.successfactors.mock.MockListener");
//        subscriberMetadata.setSubscribers(new SubscriberMetadata.Subscriber[]{s0,s1,s2});
//        subscriberMetadataMap.put("com.addEmp",subscriberMetadata);
//        subscriberMetadataMap.put("com.addEmp1",subscriberMetadata);


    }

    public static String[] getEventTypes(){
        return subscriberMetadataMap.keySet().toArray(new String[subscriberMetadataMap.keySet().size()]);
    }

    public static SubscriberMetadata getSubscriberMetadata(String eventType){
        return subscriberMetadataMap.get(eventType);
    }

}
