package com.company.EventCenter.Scheduler;

import com.company.EventCenter.Bus;

/**
 * Created by root on 2/23/16.
 */
public class SenderScheduler implements Scheduler {
    private Bus mBus;

    public SenderScheduler(final Bus bus){
        mBus = bus;
    }
    @Override
    public void post(Runnable runnable) {
        runnable.run();
    }
}
