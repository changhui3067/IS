package com.successfactors.hermesstore.service;

import com.successfactors.hermesstore.bean.EventStatusEnum;
import com.successfactors.sca.ServiceCommand;

/**
 * Service command to remove SEBEvent entities by status.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
public class RemoveEventsByStatusCmd implements ServiceCommand<Void> {

  private static final long serialVersionUID = 6415665844963382499L;

  private int daysToKeep;
  private EventStatusEnum[] statusEnums;
  
  public RemoveEventsByStatusCmd() {
    
  }
  
  public RemoveEventsByStatusCmd(int daysToKeep, EventStatusEnum... statusEnums) {
    this.daysToKeep = daysToKeep;
    this.statusEnums = statusEnums;
  }

  public EventStatusEnum[] getStatusEnums() {
    return statusEnums;
  }

  public void setStatusEnums(EventStatusEnum[] statusEnums) {
    this.statusEnums = statusEnums;
  }

  public int getDaysToKeep() {
    return daysToKeep;
  }

  public void setDaysToKeep(int daysToKeep) {
    this.daysToKeep = daysToKeep;
  }

}
