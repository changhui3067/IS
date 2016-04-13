package com.successfactors.sef.metadata;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sf.sfv4.util.FileUtils;
import com.successfactors.hermes.bean.EventDefinitionBean;
import com.successfactors.hermes.core.event.EventDefRegistry;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import com.successfactors.standardevent.StandardEventRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Manish Manwani
 * Date: 5/13/15
 * Time: 6:47 PM
 */
public class EventMetadataProvider {

  private static final Logger logger = LogManager.getLogger(EventMetadataProvider.class);

  private static Map<String, EventMetadata> eventMetadataMap
      = new HashMap<>();
  private static final String EVENT_METADATA_FILE = "sef/events/events.json";

  private EventMetadataProvider() {
  }

  static {
    init();
  }

  private static void init() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      InputStream is = FileUtils.toInputStream(EVENT_METADATA_FILE);
      EventMetadata[] metas = mapper.readValue(is, EventMetadata[].class);
      for (EventMetadata meta : metas) {
        String type = meta.getType();
        if (type == null || type.isEmpty()) {
          logger.error(String.format("Skipping event metadata with type '%s'", type));
          continue;
        }
        if (meta.isExternallyAllowed()) {
          EventDefinitionBean eventDef = new EventDefinitionBean();
          eventDef.setTopic(meta.getTopic());
          eventDef.setLabelKey(meta.getLocalizedTypeKey());
          Map<String, String> attrs = new HashMap<>();
          attrs.put("external", "true");
          eventDef.setAttributes(attrs);
          EventDefRegistry.getInstance().resiger(eventDef);
        }
        eventMetadataMap.put(type, meta);
      }
    } catch (IOException e) {
      logger.error(String.format("Could not parse Even Metadata file: %s.",EVENT_METADATA_FILE), e);
    }
  }

  public static String[] getEventTypes() {
    return eventMetadataMap.keySet().toArray(new String[eventMetadataMap.keySet().size()]);
  }

  public static EventMetadata getEventMetadata(String eventType)
      throws  StandardEventRuntimeException {
    if (eventType == null) {
      throw new StandardEventRuntimeException("type is null.", null);
    }
    if (eventType.isEmpty()) {
      throw new StandardEventRuntimeException("type is empty.", null);
    }
    if (!isValidEventType(eventType)) {
      throw new StandardEventRuntimeException(String.format("no such event type: %s)", eventType), null);
    }

    return eventMetadataMap.get(eventType);
  }

  public static boolean isValidEventType(String eventType) {
    return eventMetadataMap.containsKey(eventType);
  }

}
