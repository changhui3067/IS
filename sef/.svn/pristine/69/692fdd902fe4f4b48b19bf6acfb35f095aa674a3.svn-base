/*
 * $Id$
 */
package com.successfactors.sef.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.seam.annotations.In;

import com.successfactors.genericobject.app.facade.MDFFacade;
import com.successfactors.genericobject.app.processor.OperationContext;
import com.successfactors.genericobject.app.processor.ValidationContext;
import com.successfactors.genericobject.service.CreateMDFBean;
import com.successfactors.genericobject.service.SaveBean;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceCommandHandler;
import com.successfactors.sca.ServiceCommandImpl;
import com.successfactors.sca.config.Service;
import com.successfactors.sef.bean.SEFEntityEventsUtil;
import com.successfactors.sef.bean.genericobject.SEFEntityEventsMap;
import com.successfactors.sef.bean.genericobject.SEFEvent;
import com.successfactors.sef.bean.genericobject.SEFKeyValuePair;
import com.successfactors.sef.service.SetEntityEventsMap;
import com.successfactors.sef.util.SEFLazySelect;

/**
 * GetSubscriber List
 * 
 */
@Service
public class SetEntityEventsMapImpl implements
    ServiceCommandImpl<String, SetEntityEventsMap> {

  /** LOGGER */
  private static final Logger LOGGER = LogManager
      .getLogger(SetEntityEventsMapImpl.class);

  /** SCA Handler */
  @Inject
  @In
  private ServiceCommandHandler scaHandler;

  @Inject
  @In(create = true)
  private MDFFacade mdfFacade;

  @Override
  public String execute(final SetEntityEventsMap cmd)
      throws ServiceApplicationException {

    LOGGER.trace(getClass().getSimpleName() + ":execute");
    // First to query the MDF DB to find the MDF entity if it exists

    Map<String, String> entityKeyValues = cmd.getEntityKeyValues();

    Long code = SEFEntityEventsUtil.hashCode(cmd.getEntityName(),
        entityKeyValues, cmd.getEffectiveDate());

    final SEFLazySelect lazySelect = new SEFLazySelect();
    lazySelect.from(SEFEntityEventsMap.class);
    lazySelect.where("code").eq(code);

    final List<SEFEntityEventsMap> entityEvents = lazySelect.getAll();

    SEFEntityEventsMap sefEntityEventsMap = null;

    if (entityEvents != null && !entityEvents.isEmpty()) {
      if (entityEvents.size() > 1) {
        LOGGER.error("More than one entity are found for "
            + cmd.getEntityName());
        for (Map.Entry<String, String> entry : entityKeyValues.entrySet()) {
          LOGGER.error("key : " + entry.getKey() + " value : "
              + entry.getValue());
        }
      } else {
        sefEntityEventsMap = entityEvents.get(0);
      }
    }

    // If not exists, create a MDF bean
    if (sefEntityEventsMap == null) {
      LOGGER.debug(" No existing configuration on MDF table");

      final CreateMDFBean createMapBeanCmd = new CreateMDFBean(
          SEFEntityEventsMap.class);

      sefEntityEventsMap = (SEFEntityEventsMap) scaHandler
          .execute(createMapBeanCmd);

      List<SEFKeyValuePair> keyValueStringPairs = sefEntityEventsMap
          .getEntityKeys();
      for (Map.Entry<String, String> entry : entityKeyValues.entrySet()) {

        final CreateMDFBean createEntityBeanCmd = new CreateMDFBean(
            SEFKeyValuePair.class);
        SEFKeyValuePair keyValuePair = (SEFKeyValuePair) scaHandler
            .execute(createEntityBeanCmd);
        keyValuePair.setKey(entry.getKey());
        keyValuePair.setValue(entry.getValue());

        keyValueStringPairs.add(keyValuePair);
      }
      sefEntityEventsMap.setEffectiveDate(cmd.getEffectiveDate());
      sefEntityEventsMap.setCode(code);
      sefEntityEventsMap.setEntityName(cmd.getEntityName());

    }

    List<SEFEvent> events = sefEntityEventsMap.getEvents();
    boolean eventExists = false;
    for (SEFEvent event : events) {
      if (event.getEventType().equalsIgnoreCase(cmd.getEventType())) {
        eventExists = true;
      }
    }

    if (!eventExists) {

      lazySelect.from(SEFEvent.class);
      lazySelect.where("code").eq(SEFEvent.hashCode(cmd.getEventType()));
      SEFEvent sefEvent = lazySelect.getFirst();

      if (sefEvent == null) {
        final CreateMDFBean sefEventBeanCmd = new CreateMDFBean(SEFEvent.class);
        sefEvent = (SEFEvent) scaHandler.execute(sefEventBeanCmd);
        sefEvent.setEventType(cmd.getEventType());
        sefEvent.setCode(SEFEvent.hashCode(cmd.getEventType()));

        final SaveBean eventSaveBean = new SaveBean(SEFEvent.class, sefEvent);

        scaHandler.execute(eventSaveBean);
      }

      events.add(sefEvent);
    }

    // Use SaveBean to save the GO
    final SaveBean updateBean = new SaveBean(SEFEntityEventsMap.class,
        sefEntityEventsMap);

    final ValidationContext ctx = scaHandler.execute(updateBean);

    final List<? extends OperationContext> failedOperationContexts = ctx
        .getFailedOperationContexts();

    StringBuilder msg = new StringBuilder();
    for (final OperationContext failedCtx : failedOperationContexts) {
      msg = msg.append(failedCtx.getFormattedMessages()).append("  ");
    }
    LOGGER.debug(msg.toString());
    return msg.toString();
  }
}
