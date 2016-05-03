package com.successfactors.sef.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xc on 2016/4/25.
 */
public class SubscriberConfigurationBeanProvider {

    private static SubscriberConfigurationBeanProvider instance;
    private Map<String, Map<String, Boolean>> companyConfMap = new HashMap<>();

    private SubscriberConfigurationBeanProvider() {
        Map<String,Boolean> a_confMap = new HashMap<>();
        a_confMap.put("print_add_emp",true);
        a_confMap.put("print_delete_emp",false);

        Map<String,Boolean> b_confMap = new HashMap<>();
        b_confMap.put("print_add_emp",true);
        b_confMap.put("print_delete_emp",true);

        companyConfMap.put("A",a_confMap);
        companyConfMap.put("b",b_confMap);
    }

    public static SubscriberConfigurationBeanProvider getInstance() {
        if (instance == null) {
            synchronized (SubscriberConfigurationBeanProvider.class) {
                if (instance == null) {
                    instance = new SubscriberConfigurationBeanProvider();
                }
            }
        }
        return instance;
    }


    public boolean isEnabled(String companyId, String subscriberId) {
        Map<String, Boolean> confMap = companyConfMap.get(companyId);
        if (confMap != null) {
            Boolean b = confMap.get(subscriberId);
            if (b != null) {
                return b;
            }
        }
        return false;
    }

    public void set(String companyId, String subscriberId, boolean enabled) {
        Map<String, Boolean> confMap = companyConfMap.get(companyId);
        if (confMap == null) {
            confMap = new HashMap<>();
            companyConfMap.put(companyId, confMap);
        }
        confMap.put(subscriberId, enabled);
    }
}
