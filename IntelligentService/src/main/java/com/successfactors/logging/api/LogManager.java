package com.successfactors.logging.api;

import com.successfactors.hermesstore.core.SEBEventStore;

/**
 * Created by Xc on 2016/4/19.
 */
public class LogManager {
    public static Logger getLogger(Class clazz) {
        return new Logger(clazz);
    }

    public static Logger getLogger() {
        return new Logger();
    }
}
