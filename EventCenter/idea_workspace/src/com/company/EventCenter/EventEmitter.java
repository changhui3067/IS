package com.company.EventCenter;

/**
 * Created by root on 2/23/16.
 */
public class EventEmitter implements Runnable {
    //private static final String TAG = Bus.TAG;

    public final Bus bus;
    public final Object event;
    public final Subscriber subscriber;
    public final Bus.EventMode mode;

    public EventEmitter(final Bus bus, final Object event, final Subscriber subscriber){
        this.bus = bus;
        this.event = event;
        this.subscriber = subscriber;
        this.mode = subscriber.mode;
    }

    @Override
    public void run() {
        try{
            subscriber.invoke(event);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
