package com.successfactors.hermesstore.service;

import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.sca.ServiceCommand;

import java.util.List;

public class GetEventsByTransactionIdCmd implements ServiceCommand<List<SEBEvent>> {

    private static final long serialVersionUID = 7516008695112977334L;

    private String transactionId;

    public GetEventsByTransactionIdCmd() {

    }

    public GetEventsByTransactionIdCmd(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

}
