package com.successfactors.hermesstore.service;

import java.util.List;

import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.sca.ServiceCommand;

/**
 * Service command to remove a list of SEBEvent entities.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
public class RemoveEventListCmd implements ServiceCommand<List<SEBEvent>> {

  private static final long serialVersionUID = 1986874504183704002L;
  
  private List<SEBEvent> sebEvents;
  
  public RemoveEventListCmd() {
    
  }
  
  public RemoveEventListCmd(List<SEBEvent> sebEvents) {
    this.sebEvents = sebEvents;
  }

  public List<SEBEvent> getSebEvents() {
    return sebEvents;
  }

  public void setSebEvents(List<SEBEvent> sebEvents) {
    this.sebEvents = sebEvents;
  }
  
}
