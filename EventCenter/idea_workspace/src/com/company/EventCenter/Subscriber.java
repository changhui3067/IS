package com.company.EventCenter;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by root on 2/23/16.
 */
public class Subscriber {
    public final MethodInfo method;   // save event function info
    public final Object target;       // event target object
    public final Class<?> targetType; // target type
    public final Class<?> eventType;  // event type
//    public final Bus.EventMode mode;  // dispatch mode
    public final String name;         // name

    public Subscriber(final MethodInfo method, final Object target){
        this.method = method;
        this.target = target;
        this.targetType = method.targetType;
        this.eventType = method.eventType;
//        this.mode = method.mode;
        this.name = method.name;
    }

    public Object invoke(Object event)
        throws InvocationTargetException, IllegalAccessException{
        return this.method.method.invoke(this.target, event);
    }
}
