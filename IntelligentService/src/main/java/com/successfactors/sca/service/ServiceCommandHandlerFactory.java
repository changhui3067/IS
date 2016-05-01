package com.successfactors.sca.service;

import java.util.concurrent.Executor;

/**
 * Created by Xc on 2016/4/19.
 */
public class ServiceCommandHandlerFactory {

    public static ServiceCommandHandler getSCAHandler() {
        return new SimpleServiceCommandHandler();
    }
}
