package com.company.EventCenter;

import java.lang.reflect.Method;

/**
 * Created by root on 2/23/16.
 */
public class MethodInfo {
    public final Method method;   // save event function info
    public final Class<?> targetType; // target type
    public final Class<?> eventType;  // event type
//    public final Bus.EventMode mode;  // dispatch mode
    public final String name;         // name

    public MethodInfo(final Method method, final Class<?> targetClass//, final Bus.EventMode mode
                       ){
        this.method = method;
        this.targetType = targetClass;
        this.eventType = method.getParameterTypes()[0];
//        this.mode = mode;
        this.name = targetType.getName() + "." + method.getName() + "(" + eventType.getName() + ")";
    }
}
