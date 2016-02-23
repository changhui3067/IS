package com.company;

import com.company.EventCenter.Bus;
import com.company.EventCenter.BusReceiver;

public class Main {

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        Bus.getDefault().register(this);
        Bus.getDefault().post(new StringBuilder("a stringbuilder"));
        Bus.getDefault().unregister(this);
    }

    @BusReceiver
    public void onStringEvent(String event){
        System.out.println("onStringEvent event= " + event);
    }

    @BusReceiver
    public void onObjectEvent(Object event)
    {
        System.out.println("onObjectEvent event = " + event);
    }

    @BusReceiver
    public void onCharSequenceEvent(CharSequence event)
    {
        System.out.println("onCharSequenceEvent event = " + event);
    }

    @BusReceiver
    public void onExceptionEvent(Exception event){
        System.out.println("onExceptionEvent event = " + event);
    }
}
