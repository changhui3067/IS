package com.successfactors.hermesstore.service.impl;

import java.sql.Connection;

import javax.inject.Inject;

import com.successfactors.sca.ServiceCommandImpl;

import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.hermesstore.dao.SEBEventDAO;
import com.successfactors.hermesstore.service.AddEventCmd;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import com.successfactors.platform.bean.ParamBean;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.config.Service;

/**
 * Service implementation of {@link AddEventCmd}.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
@Service
public class AddEventCmdImpl implements ServiceCommandImpl<SEBEvent, AddEventCmd> {
  
  private static final Logger logger = LogManager.getLogger(AddEventCmdImpl.class);
  
  @Inject
  private ParamBean params;
  
  @Inject
  private SEBEventDAO sebEventDAO;
  
  @Inject
  private Connection dbConnection;

  @Override
  public SEBEvent execute(AddEventCmd cmd) throws ServiceApplicationException {
    SEBEvent sebEvent = cmd.getSebEvent();
    sebEventDAO.addEvent(params.getCompanySchema(), dbConnection, sebEvent);
    return sebEvent;
  }
  
}
