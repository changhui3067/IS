package com.company.EventCenter.Bus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by root on 2/17/16.
 */
public class Bus {
//    public enum EventMode(
//            Sender, Main, Thread
//    )

    private static class SingletonHolder{
        static final Bus INSTANCE = new Bus();
    }

    public static Bus getDefault(){
        return SingletonHolder.INSTANCE;
    }

    private Map<Object, Set<Method>> mMethodMap = new HashMap<Object, Set<Method>>();

    public void register(final Object target){
        Set<Method> methods = Helper.findAnnotatedMethods(target.getClass(), BusReceiver.class);
        if(methods == null || methods.isEmpty()){
            return;
        }
        mMethodMap.put(target, methods);
    }

    public void unregister(final Object target){
        mMethodMap.remove(target);
    }

    public void post(Object event){
        final Class<?> eventClass = event.getClass();
        for(Map.Entry<Object, Set<Method>> entry: mMethodMap.entrySet()){
            final Object target = entry.getKey();
            final Set<Method> methods = entry.getValue();
            if(methods == null || methods.isEmpty()){
                continue;
            }

            for(Method method: methods){
                Class<?> patameterClass = method.getParameterTypes()[0];
                if(patameterClass.isAssignableFrom(eventClass)){
                    try {
                        method.invoke(target, event);
                    } catch (IllegalAccessException e){
                        e.printStackTrace();
                    } catch (InvocationTargetException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
