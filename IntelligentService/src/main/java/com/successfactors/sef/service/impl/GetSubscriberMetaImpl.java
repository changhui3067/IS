package com.successfactors.sef.service.impl;

import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceCommandImpl;
import com.successfactors.sef.metadata.SubscriberMetadata;
import com.successfactors.sef.metadata.SubscriberMetadataProvider;
import com.successfactors.sef.service.GetSubscriberMeta;

/**
 * Created by Xc on 2016/4/25.
 */
public class GetSubscriberMetaImpl implements ServiceCommandImpl<SubscriberMetadata,GetSubscriberMeta> {

    @Override
    public SubscriberMetadata execute(GetSubscriberMeta cmd) throws ServiceApplicationException {
        return SubscriberMetadataProvider.getSubscriberMetadata(cmd.getEventType());
    }
}
