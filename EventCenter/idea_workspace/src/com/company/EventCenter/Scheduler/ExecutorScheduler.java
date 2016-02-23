package com.company.EventCenter.Scheduler;

import com.company.EventCenter.Bus;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by root on 2/23/16.
 */
public class ExecutorScheduler implements Scheduler {
    private Bus mBus;
    private Executor mExecutor;

    public ExecutorScheduler(final Bus bus){
        mBus = bus;
        mExecutor = Executors.newCachedThreadPool();
    }
    @Override
    public void post(Runnable runnable) {
        //run the task in the thread pool
        mExecutor.execute(runnable);
    }
}
