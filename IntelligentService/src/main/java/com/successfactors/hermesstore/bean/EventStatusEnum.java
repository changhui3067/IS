package com.successfactors.hermesstore.bean;


import com.successfactors.hermesstore.core.SEBEventStore;
import com.successfactors.hermesstore.util.StringUtils;

/**
 * Enumaration of event status.
 *
 * @author Roman.Li(I322223) Success Factors
 */
public enum EventStatusEnum {

  INITIAL(SEBEventStore.EVENT_STATUS_INITIAL), 
  IN_PROGRESS(SEBEventStore.EVENT_STATUS_IN_PROGRESS), 
  PUBLISHED(SEBEventStore.EVENT_STATUS_PUBLISHED), 
  FAILED(SEBEventStore.EVENT_STATUS_FAILED);

  private String status;

  EventStatusEnum(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public static EventStatusEnum getEnum(String status) {
    if (StringUtils.isBlank(status)) {
      return null;
    }
    for (EventStatusEnum eventStatusEnum : EventStatusEnum.values()) {
      if (status.equals(eventStatusEnum.getStatus())) {
        return eventStatusEnum;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return this.status;
  }

}
