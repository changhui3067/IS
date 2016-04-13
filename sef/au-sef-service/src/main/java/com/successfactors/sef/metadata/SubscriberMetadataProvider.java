package com.successfactors.sef.metadata;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sf.sfv4.util.FileUtils;

import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;

public class SubscriberMetadataProvider {

  private static final Logger logger = LogManager
      .getLogger(SubscriberMetadataProvider.class);

  private static Map<String, SubscriberMetadata> subscriberMetadataMap = new HashMap<>();
  private static final String SUBSCRIBER_METADATA_FILE = "sef/events/subscriber.json";

  private SubscriberMetadataProvider() {
  }

  static {
    init();
  }

  private static void init() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      InputStream is = FileUtils.toInputStream(SUBSCRIBER_METADATA_FILE);
      SubscriberMetadata[] metas = mapper.readValue(is,SubscriberMetadata[].class);
      for (SubscriberMetadata meta : metas) {
        String type = meta.getType();
        if (type == null || type.isEmpty()) {
          logger.error(String.format("Skipping subscriber metadata with type '%s'",
              type));
          continue;
        }
        subscriberMetadataMap.put(type, meta);
      }
    } catch (IOException e) {
      logger.error(String.format("Could not parse subscriber Metadata file: %s.",
          SUBSCRIBER_METADATA_FILE), e);
    }
  }

  public static String[] getEventTypes() {
    return subscriberMetadataMap.keySet().toArray(
        new String[subscriberMetadataMap.keySet().size()]);
  }

  public static SubscriberMetadata getSubscriberMetadata(final String eventType)
      throws NullPointerException, IllegalArgumentException {
    if (eventType == null) {
      throw new NullPointerException("type is null.");
    }
    if (eventType.isEmpty()) {
      throw new IllegalArgumentException("type is empty.");
    }
    if (!isValidEventType(eventType)) {
      throw new IllegalArgumentException(String.format(
          "no such subscriber type: %s)", eventType));
    }

    return subscriberMetadataMap.get(eventType);
  }

  public static boolean isValidEventType(final String eventType) {
    return subscriberMetadataMap.containsKey(eventType);
  }

}
