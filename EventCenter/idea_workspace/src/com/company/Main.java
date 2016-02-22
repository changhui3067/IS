package com.company;

import com.company.EventCenter.Bus;
import com.company.EventCenter.BusReceiver;

public class Main {

    public static void main(String[] args) {
        final Main demo = new Main();
        Bus bus = Bus.getDefault();
        bus.register(demo);
        bus.post(new Object());
        bus.post("an event");
        bus.post(12345);
        bus.post("12345");
        bus.post(new RuntimeException("ErrorTest"));
    }

    //this info will not be print out
    @BusReceiver
    public void onReceiveRunnableNotPost(Runnable event){
        System.out.println("onReceiveRunnableNotPost event = " + event);
    }

    @BusReceiver
    public void onReceiveObjectEvent(Object event){
        System.out.println("onReceiveObjectEvent event = " + event);
    }

    @BusReceiver
    public void onReceiveStringEvent(String event){
        System.out.println("onReceiveStringEvent event = " + event);
    }

    @BusReceiver
    public void onReceiveIntegerEvent(Integer event){
        System.out.println("onReceiveIntegerEvent event = " + event);
    }

    @BusReceiver
    public void onReceiveExceptionEvent(RuntimeException event){
        System.out.println("onReceiveExceptionEvent event = " + event);
    }
}
