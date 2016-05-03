package com.successfactors.mock;

import com.successfactors.sef.EventReaction;
import com.successfactors.sef.SefEvent;

import java.util.Map;

/**
 * Created by Xc on 2016/4/25.
 */
public class mock_add implements EventReaction {

    @Override
    public void onEvent(SefEvent event) {
        Map<String,Object> params = event.getParams();
        System.out.println("add_mock:"+params.get("empId"));
    }
}
