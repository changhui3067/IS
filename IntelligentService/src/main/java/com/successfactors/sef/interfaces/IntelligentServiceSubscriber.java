package com.successfactors.sef.interfaces;

import com.successfactors.sef.SefEvent;

/**
 * Created by Xc on 2016/4/24.
 */
public interface IntelligentServiceSubscriber {
    public void onEvent(SefEvent event);
    public boolean checkPermission();
    public void log(Object o);
}
