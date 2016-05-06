package com.successfactors.mock;

import com.successfactors.sef.interfaces.IntelligentServiceSubscriber;
import com.successfactors.sef.SefEvent;
import com.successfactors.sef.interfaces.IntelligentServiceSubscriberAdapter;

/**
 * Created by Xc on 2016/4/24.
 */
public class MockListener extends IntelligentServiceSubscriberAdapter {

    @Override
    public void onEvent(SefEvent event) {
        System.out.println("This is a mock reaction");
    }
}
