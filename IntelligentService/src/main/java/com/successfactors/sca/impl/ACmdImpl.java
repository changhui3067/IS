package com.successfactors.sca.impl;

import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceCommandImpl;
import com.successfactors.sca.ACmd;

/**
 * Created by Xc on 2016/4/19.
 */
public class ACmdImpl implements ServiceCommandImpl<String, ACmd> {
    @Override
    public String execute(ACmd cmd) throws ServiceApplicationException {
        return "Success";
    }
}
