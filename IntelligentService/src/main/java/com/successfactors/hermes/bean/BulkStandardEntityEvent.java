package com.successfactors.hermes.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: I833237
 * Date: 6/2/15
 * Time: 2:45 PM
 */
public class BulkStandardEntityEvent {

  private List<StandardEntityEvent> events;

  public List<StandardEntityEvent> getEvents() {
    if (events == null) {
      events = new ArrayList<>();
    }
    return events;
  }

  public void setEvents(List<StandardEntityEvent> events) {
    this.events = events;
  }
}
