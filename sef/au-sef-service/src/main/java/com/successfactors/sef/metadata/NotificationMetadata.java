package com.successfactors.sef.metadata;

public class NotificationMetadata {

  public static class NotificationsMetadata {
    private String module;
    private String impactArea;
    private String notificationDescription;
    private String notificationType;

    /**
     * @return the module
     */
    public String getModule() {
      return module;
    }

    /**
     * @param module
     *          the module to set
     */
    public void setModule(final String module) {
      this.module = module;
    }

    /**
     * @return the impactArea
     */
    public String getImpactArea() {
      return impactArea;
    }

    /**
     * @param impactArea the impactArea to set
     */
    public void setImpactArea(String impactArea) {
      this.impactArea = impactArea;
    }

    /**
     * @return the notificationType
     */
    public String getNotificationType() {
      return notificationType;
    }

    /**
     * @param notificationType the notificationType to set
     */
    public void setNotificationType(String notificationType) {
      this.notificationType = notificationType;
    }

    /**
     * @return the notificationDescription
     */
    public String getNotificationDescription() {
      return notificationDescription;
    }

    /**
     * @param notificationDescription the notificationDescription to set
     */
    public void setNotificationDescription(String notificationDescription) {
      this.notificationDescription = notificationDescription;
    }


  }

  private String type;
  private final NotificationsMetadata[] notifications = new NotificationsMetadata[0];

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public NotificationsMetadata[] getNotifications() {
    return notifications;
  }

}
