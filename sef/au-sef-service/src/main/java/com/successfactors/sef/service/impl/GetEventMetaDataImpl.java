/*
 * $Id$
 */
package com.successfactors.sef.service.impl;

import org.jboss.seam.annotations.In;

import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceCommandHandler;
import com.successfactors.sca.ServiceQueryImpl;
import com.successfactors.sca.config.Service;
import com.successfactors.sef.bean.SEFEventListVO;
import com.successfactors.sef.bean.SEFEventVO;
import com.successfactors.sef.metadata.EventMetadata;
import com.successfactors.sef.metadata.EventMetadataProvider;
import com.successfactors.sef.service.GetEventMetaData;
import javax.inject.Inject;

/**
 * GetRecentLinksImpl
 * 
 * @author echen Success Factors
 */
@Service
public class GetEventMetaDataImpl implements
    ServiceQueryImpl<SEFEventListVO, GetEventMetaData> {

  /** LOGGER */
  private static final Logger LOGGER = LogManager.getLogger();

  /** SCA Handler */
  @Inject
  @In
  private ServiceCommandHandler scaHandler;

  /** {@inheritDoc} */
  @Override
  public SEFEventListVO execute(GetEventMetaData cmd)
      throws ServiceApplicationException {

    SEFEventListVO eventListVO = new SEFEventListVO();

    for (String eventType : EventMetadataProvider.getEventTypes()) {
      EventMetadata eventMetaData = EventMetadataProvider
          .getEventMetadata(eventType);

      if (eventMetaData.isSmartSuiteEvent()) {
        SEFEventVO event = new SEFEventVO();
        event.setType(eventMetaData.getType());
        event.setName(eventMetaData.getLocalizedTypeKey());
        event.setDescription(eventMetaData.getDescriptionMessageKey());
        event.setExternalCode(eventMetaData.getRuleCode());
        event.setPublisher(eventMetaData.getSourceArea().name());
        event.setExtAllowed(eventMetaData.isExternallyAllowed());

        eventListVO.getEvents().add(event);
      }
    }

    return eventListVO;
  }
}
