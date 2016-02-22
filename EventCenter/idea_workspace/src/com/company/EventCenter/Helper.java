package com.company.EventCenter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2/17/16.
 */
final class Helper {

    public static List<Method> findAnnotatedMethods(final Class<?> type,
                                                    final Class<? extends Annotation> annotation){
        final List<Method> methods = new ArrayList<Method>();

        Method[] ms = type.getDeclaredMethods();

        for(Method method : ms){
            //must not static
            if(Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            //must be public
            if( !Modifier.isPublic(method.getModifiers())){
                continue;
            }
            //must has only one parameter
            if( method.getParameterTypes().length != 1) {
                continue;
            }
            //must has annotation
            if( !method.isAnnotationPresent(annotation)) {
                continue;
            }

            methods.add(method);
        }

        return methods;
    }

    public static void dumpMethod(final Method method){
        final StringBuilder builder = new StringBuilder();
        builder.append("-------------------------------------\n");

        System.out.println(builder.toString());
    }
}
