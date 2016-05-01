package com.successfactors.hermesstore.service;

import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.sca.ServiceCommand;

/**
 * Service command to update an SEBEvent entity.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
public class UpdateEventCmd implements ServiceCommand<SEBEvent> {

  private static final long serialVersionUID = 7868235706951743713L;
 
  private SEBEvent sebEvent;
  
  public UpdateEventCmd() {
    
  }
  
  public UpdateEventCmd(SEBEvent sebEvent) {
    this.sebEvent = sebEvent;
  }
  
  public SEBEvent getSebEvent() {
    return sebEvent;
  }

  public void setSebEvent(SEBEvent sebEvent) {
    this.sebEvent = sebEvent;
  }

  
}
