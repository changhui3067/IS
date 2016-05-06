package com.successfactors.sef.service;

import com.successfactors.sca.ServiceCommand;

import java.util.Map;

/**
 * Created by Xc on 2016/4/24.
 * set subscriber configuration using companyId
 */
public class SetSubscriberConfiguration implements ServiceCommand<Void> {
    private String eventType;
    private Map<String,Boolean> subscriberConfigurationMap;
    private String companyId;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Map<String, Boolean> getSubscriberConfigurationMap() {
        return subscriberConfigurationMap;
    }

    public void setSubscriberConfigurationMap(Map<String, Boolean> subscriberConfigurationMap) {
        this.subscriberConfigurationMap = subscriberConfigurationMap;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
