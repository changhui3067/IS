package com.successfactors.sca.service;

import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceCommand;

/**
 * Created by Xc on 2016/4/19.
 */
public interface ServiceCommandHandler {

    public <R, C extends ServiceCommand<R>> R execute(C cmd) throws ServiceApplicationException;
}
