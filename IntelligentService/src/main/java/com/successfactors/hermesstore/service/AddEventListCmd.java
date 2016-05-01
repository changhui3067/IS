package com.successfactors.hermesstore.service;

import java.util.List;

import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.sca.ServiceCommand;

/**
 * Service command to add a list of SEBEvent entities.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
public class AddEventListCmd implements ServiceCommand<List<SEBEvent>> {

  private static final long serialVersionUID = -2016488754797968718L;
  
  private List<SEBEvent> sebEvents;

  public AddEventListCmd() {

  }
  
  public AddEventListCmd(List<SEBEvent> sebEvents) {
    this.sebEvents = sebEvents;
  }

  public List<SEBEvent> getSebEvents() {
    return sebEvents;
  }

  public void setSebEvents(List<SEBEvent> sebEvents) {
    this.sebEvents = sebEvents;
  }

}
