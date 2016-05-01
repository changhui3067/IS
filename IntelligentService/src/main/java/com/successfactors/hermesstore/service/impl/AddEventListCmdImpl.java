package com.successfactors.hermesstore.service.impl;

import java.sql.Connection;
import java.util.List;

import javax.inject.Inject;

import com.successfactors.sca.ServiceCommandImpl;
import org.jboss.seam.annotations.In;

import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.hermesstore.dao.SEBEventDAO;
import com.successfactors.hermesstore.service.AddEventListCmd;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import com.successfactors.platform.bean.ParamBean;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.config.Service;

/**
 * Service implementation of {@link AddEventListCmd}.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
@Service
public class AddEventListCmdImpl implements ServiceCommandImpl<List<SEBEvent>, AddEventListCmd> {

  private static final Logger logger = LogManager.getLogger(AddEventListCmdImpl.class);
  
  @Inject
  @In
  private ParamBean params;

  @Inject
  @In(value = SEBEventDAO.DAO_NAME, create = true)
  private SEBEventDAO sebEventDAO;
  
  @Inject
  @In
  private Connection dbConnection;
  
  @Override
  public List<SEBEvent> execute(AddEventListCmd cmd) throws ServiceApplicationException {
    List<SEBEvent> sebEvents = cmd.getSebEvents();
    sebEventDAO.addEventList(params.getCompanySchema(), dbConnection, sebEvents);
    return sebEvents;
  }

}
