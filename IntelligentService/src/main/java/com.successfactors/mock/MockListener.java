package com.successfactors.mock;

import com.successfactors.sef.EventReaction;
import com.successfactors.sef.SefEvent;

/**
 * Created by Xc on 2016/4/24.
 */
public class MockListener implements EventReaction{

    @Override
    public void onEvent(SefEvent event) {
        System.out.println("This is a mock reaction");
    }
}
