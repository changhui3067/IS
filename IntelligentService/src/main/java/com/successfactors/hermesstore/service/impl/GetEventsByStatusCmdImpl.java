package com.successfactors.hermesstore.service.impl;

import java.sql.Connection;
import java.util.List;

import javax.inject.Inject;

import org.jboss.seam.annotations.In;

import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.hermesstore.dao.SEBEventDAO;
import com.successfactors.hermesstore.service.GetEventsByStatusCmd;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import com.successfactors.platform.bean.ParamBean;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceQueryImpl;
import com.successfactors.sca.config.Service;

/**
 * Service implementation of {@link GetEventsByStatusCmd}.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
@Service
public class GetEventsByStatusCmdImpl implements ServiceQueryImpl<List<SEBEvent>, GetEventsByStatusCmd> {

  private static final Logger logger = LogManager.getLogger(GetEventsByStatusCmdImpl.class);
  
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
  public List<SEBEvent> execute(GetEventsByStatusCmd cmd) throws ServiceApplicationException {
    return sebEventDAO.getEventsByStatus(
      params.getCompanySchema(), dbConnection, cmd.getFetchSize(), cmd.getStatusEnums());
  }
  
}
