/*
 * $Id$
 */
package com.successfactors.sef.service.impl;

import java.util.List;

import org.jboss.seam.annotations.In;

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
import com.successfactors.sef.service.SetSubscriberConfigurationData;
import com.successfactors.sef.bean.SEFSubscriberConfigurationUtil;
import com.successfactors.sef.bean.genericobject.SEFSubscriberConfiguration;
import com.successfactors.sef.util.SEFLazySelect;

import javax.inject.Inject;

/**
 * GetSubscriber List
 * 
 */
@Service
public class SetSubscriberConfigurationDataImpl implements
    ServiceCommandImpl<String, SetSubscriberConfigurationData> {

  /** LOGGER */
  private static final Logger LOGGER = LogManager
      .getLogger(SetSubscriberConfigurationDataImpl.class);

  /** SCA Handler */
  @Inject
  @In
  private ServiceCommandHandler scaHandler;

  @Override
  public String execute(final SetSubscriberConfigurationData cmd)
      throws ServiceApplicationException {

    LOGGER.trace(getClass().getSimpleName()+":execute"); 
    // First to query the MDF DB to find the MDF entity if it exists
    final int externalCode = SEFSubscriberConfigurationUtil.hashCode(
        cmd.getEventType(), cmd.getCategoryId(), cmd.getSubscriberId());
    final SEFLazySelect lazySelect = new SEFLazySelect();
    lazySelect.from(SEFSubscriberConfiguration.class);
    lazySelect.where("code").eq(externalCode);

    SEFSubscriberConfiguration sefSubscriberConfiguration = lazySelect
        .getFirst();

    // If not exists, create a MDF bean
    if (sefSubscriberConfiguration == null) {
      LOGGER.debug(" No existing configuration on MDF table"); 
      final CreateMDFBean createBeanCmd = new CreateMDFBean(
          SEFSubscriberConfiguration.class);
      sefSubscriberConfiguration = (SEFSubscriberConfiguration) scaHandler
          .execute(createBeanCmd);
      sefSubscriberConfiguration.setCode(Long.valueOf(externalCode));
      sefSubscriberConfiguration.setEventType(cmd.getEventType());
      sefSubscriberConfiguration.setCatetoryID(cmd.getCategoryId());
      sefSubscriberConfiguration.setSubscriberID(cmd.getSubscriberId());

    }
    sefSubscriberConfiguration.setEnabled(cmd.isEnabled());
    sefSubscriberConfiguration.setDaysInAdvance(cmd.getDaysInAdvance()==null? null: Long.valueOf(cmd.getDaysInAdvance()));
    sefSubscriberConfiguration.setReDelivered(cmd.getReDelivered());

    // Use SaveBean to save the GO
    final SaveBean updateBean = new SaveBean(SEFSubscriberConfiguration.class,
        sefSubscriberConfiguration);

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
