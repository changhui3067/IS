package com.company.EventCenter.Scheduler;

/**
 * Created by root on 2/23/16.
 */
public interface Scheduler {
    void post(Runnable runnable);
}
