package com.successfactors.sef.service.impl;

import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceCommandImpl;
import com.successfactors.sef.service.SetSubscriberConfiguration;

/**
 * Created by Xc on 2016/4/24.
 */
public class SetSubscriberConfigurationImpl implements ServiceCommandImpl<Void,SetSubscriberConfiguration> {

    @Override
    public Void execute(SetSubscriberConfiguration cmd) throws ServiceApplicationException {
        return null;
    }
}
