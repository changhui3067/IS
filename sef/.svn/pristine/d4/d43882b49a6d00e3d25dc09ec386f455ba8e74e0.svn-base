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

public class NotificationMetadataProvider {

  private static final Logger logger = LogManager
      .getLogger(NotificationMetadataProvider.class);

  private static Map<String, NotificationMetadata> notificationMetadataMap = new HashMap<>();
  private static final String NOTIFICATION_METADATA_FILE = "sef/events/notification.json";

  private NotificationMetadataProvider() {
  }

  static {
    init();
  }

  private static void init() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      InputStream is = FileUtils.toInputStream(NOTIFICATION_METADATA_FILE);
      NotificationMetadata[] metas = mapper.readValue(is,NotificationMetadata[].class);
      for (NotificationMetadata meta : metas) {
        String type = meta.getType();
        if (type == null || type.isEmpty()) {
          logger.error(String.format("Skipping notification metadata with type '%s'",
              type));
          continue;
        }
        notificationMetadataMap.put(type, meta);
      }
    } catch (IOException e) {
      logger.error(String.format("Could not parse notification Metadata file: %s.",
          NOTIFICATION_METADATA_FILE), e);
    }
  }

  public static String[] getEventTypes() {
    return notificationMetadataMap.keySet().toArray(
        new String[notificationMetadataMap.keySet().size()]);
  }

  public static NotificationMetadata getNotificationMetadata(final String eventType)
      throws NullPointerException, IllegalArgumentException {
    if (eventType == null) {
      throw new NullPointerException("type is null.");
    }
    if (eventType.isEmpty()) {
      throw new IllegalArgumentException("type is empty.");
    }
    if (!isValidEventType(eventType)) {
      /*throw new IllegalArgumentException(String.format(
          "no such notification type: %s)", eventType));*/
      return null;
    }

    return notificationMetadataMap.get(eventType);
  }

  public static boolean isValidEventType(final String eventType) {
    return notificationMetadataMap.containsKey(eventType);
  }

}
