package com.company.EventCenter.Scheduler;

import com.company.EventCenter.Bus;

import java.util.logging.Handler;


/**
 * Created by root on 2/23/16.
 */
public class HandlerScheduler implements Scheduler {
    private Bus mBus;
    private Handler mHandler;

    public HandlerScheduler(final Bus bus, final Handler handler){
        mBus = bus;
        mHandler = handler;
    }

    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }
}
