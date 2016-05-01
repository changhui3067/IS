package com.successfactors.hermesstore.service;

import com.successfactors.hermesstore.bean.EventStatusEnum;
import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.sca.ServiceCommand;

import java.util.List;

/**
 * Service query to retrieve SEBEvent entities by status.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
public class GetEventsByStatusCmd implements ServiceCommand<List<SEBEvent>> {

  private static final long serialVersionUID = 1611315985246519047L;
  
  private int fetchSize;
  private EventStatusEnum[] statusEnums;
  
  public GetEventsByStatusCmd() {
    
  }
  
  public GetEventsByStatusCmd(int fetchSize, EventStatusEnum... statusEnums) {
    this.fetchSize = fetchSize;
    this.statusEnums = statusEnums;
  }

  public int getFetchSize() {
    return fetchSize;
  }

  public void setFetchSize(int fetchSize) {
    this.fetchSize = fetchSize;
  }
  
  public EventStatusEnum[] getStatusEnums() {
    return statusEnums;
  }

  public void setStatusEnums(EventStatusEnum[] statusEnums) {
    this.statusEnums = statusEnums;
  }

}
