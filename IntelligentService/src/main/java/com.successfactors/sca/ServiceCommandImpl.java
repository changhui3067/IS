package com.successfactors.sca;


/**
 * Created by Xc on 2016/4/19.
 */
public interface ServiceCommandImpl<R,C> {
    R execute(C cmd) throws ServiceApplicationException;
}
