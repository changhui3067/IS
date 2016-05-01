package com.successfactors.hermesstore.service.impl;

import java.sql.Connection;

import javax.inject.Inject;

import com.successfactors.sca.ServiceCommandImpl;
import org.jboss.seam.annotations.In;

import com.successfactors.hermesstore.dao.SEBEventDAO;
import com.successfactors.hermesstore.service.RemoveEventsByStatusCmd;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import com.successfactors.platform.bean.ParamBean;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.config.Service;

/**
 * Service implementation of {@link RemoveEventsByStatusCmd}.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
@Service
public class RemoveEventsByStatusCmdImpl implements ServiceCommandImpl<Void, RemoveEventsByStatusCmd> {
  
  private static final Logger logger = LogManager.getLogger(RemoveEventsByStatusCmdImpl.class);

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
  public Void execute(RemoveEventsByStatusCmd cmd) throws ServiceApplicationException {
    sebEventDAO.removeEventsByStatus(params.getCompanySchema(), dbConnection, cmd.getDaysToKeep(), cmd.getStatusEnums());
    return null;
  }

}
