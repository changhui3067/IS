package com.successfactors.mock;

import com.successfactors.sef.interfaces.IntelligentServiceSubscriber;
import com.successfactors.sef.SefEvent;
import com.successfactors.sef.interfaces.IntelligentServiceSubscriberAdapter;

import java.util.Map;

/**
 * Created by Xc on 2016/4/25.
 */
public class mock_add extends IntelligentServiceSubscriberAdapter {

    @Override
    public void onEvent(SefEvent event) {
        Map<String,Object> params = event.getParams();
        System.out.println("add_mock:"+params.get("empId"));
    }
}
