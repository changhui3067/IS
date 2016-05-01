package com.successfactors.hermesstore.service;

import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.sca.ServiceCommand;

/**
 * Service command to add an SEBEvent entity.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
public class AddEventCmd implements ServiceCommand<SEBEvent> {

  private static final long serialVersionUID = -8495786887303230397L;
 
  private SEBEvent sebEvent;
  
  public AddEventCmd() {
    
  }
  
  public AddEventCmd(SEBEvent sebEvent) {
    this.sebEvent = sebEvent;
  }

  public SEBEvent getSebEvent() {
    return sebEvent;
  }

  public void setSebEvent(SEBEvent sebEvent) {
    this.sebEvent = sebEvent;
  }

}
