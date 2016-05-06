package com.successfactors.sef.service.impl;

import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceCommandImpl;
import com.successfactors.sef.metadata.EventMedataProvider;
import com.successfactors.sef.service.GetEventTypeList;

/**
 * Created by Xc on 2016/4/24.
 */
public class GetEventTypeListImpl implements ServiceCommandImpl<String[],GetEventTypeList> {

    @Override
    public String[] execute(GetEventTypeList cmd) throws ServiceApplicationException {
        return EventMedataProvider.getEventMetaTypeList();
    }
}
