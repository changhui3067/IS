package com.company.EventCenter.Scheduler;

import com.company.EventCenter.Bus;

/**
 * Created by root on 2/23/16.
 */
public final class Schedulers {
    public static Scheduler sender(final Bus bus){
        return new SenderScheduler(bus);
    }

    //   public static Scheduler getMainThreadScheduler(final Bus bus){
//        return new HandlerScheduler(bus, new Handler(Looper.getMainLooper));
//    }

    public static Scheduler thread(final Bus bus){
        return new ExecutorScheduler(bus);
    }
}
