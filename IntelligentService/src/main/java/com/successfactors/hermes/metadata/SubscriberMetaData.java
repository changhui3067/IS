package com.successfactors.hermes.metadata;

import EventCenter.hermes.bean.SubscriberMetaDataBean;
import EventCenter.hermes.util.ZookeeperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sf.sfv4.util.StringUtils;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import org.apache.curator.framework.recipes.locks.LockInternals;
import org.apache.curator.framework.recipes.locks.LockInternalsSorter;
import org.apache.curator.framework.recipes.locks.StandardLockInternalsDriver;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.util.*;

@ManagedResource(description = "Subscriber meta data")
public class SubscriberMetaData implements SubscriberMetaDataMBean {
    private Logger logger = LogManager.getLogger();

    @Override
    public String getLocalSubscribersMD() {
        Map<String, List<SubscriberMetaDataBean>> result = new HashMap<String, List<SubscriberMetaDataBean>>();
        for (String topic : SubscriberMetaDataManager.getLocalSubscribersMD().keySet()) {
            result.put(topic, new ArrayList<SubscriberMetaDataBean>(SubscriberMetaDataManager.getLocalSubscribersMD().get(topic).values()));
        }

        String output = "format error";
        try {
            output = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return output;
    }

    @Override
    public String getRemoteSubscribersMD() {
        Map<String, Map<String, SubscriberMetaDataBean>> remoteSubscribersMD = SubscriberMetaDataManager.getRemoteSubscribersMD();
        Map<String, List<SubscriberMetaDataBean>> result = new HashMap<String, List<SubscriberMetaDataBean>>();
        for (String topic : remoteSubscribersMD.keySet()) {
            result.put(topic, new ArrayList<SubscriberMetaDataBean>(remoteSubscribersMD.get(topic).values()));
        }

        String output = "format error";
        try {
            output = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return output;
    }

    @Override
    public String getLeaderSubscriberServer() {
        if (ZookeeperUtil.client != null) {
            Collection<String> participantNodes;
            try {
                participantNodes = LockInternals.getParticipantNodes(
                        ZookeeperUtil.client, ZookeeperUtil.SEB_SUBSCRIBER_SERVER_NODE, "latch-",
                        new LockInternalsSorter() {

                            public String fixForSorting(String str, String lockName) {
                                return StandardLockInternalsDriver.standardFixForSorting(str,
                                        lockName);
                            }
                        });
                if (participantNodes.size() > 0) {
                    byte[] bytes = (byte[]) ZookeeperUtil.client.getData().forPath((String) participantNodes.iterator().next());
                    return new String(bytes, StringUtils.DEFAULT_CHARSET);
                } else {
                    return "No subscriber server registed.";
                }
            } catch (Exception e) {
                logger.error("Unexpected exception when get subscriber leader server.", e);
                return "Unexpected exception when get subscriber leader server.";
            }
        } else {
            return "Can't connection to zookeeper";
        }
    }

    @Override
    public String getNonBizXSubscribersMD() {
        Map<String, List<SubscriberMetaDataBean>> result = new HashMap<String, List<SubscriberMetaDataBean>>();
        Map<String, Map<String, SubscriberMetaDataBean>> localSubscribersMD = SubscriberMetaDataManager.getLocalSubscribersMD();
        Map<String, Map<String, SubscriberMetaDataBean>> remoteSubscribersMD = SubscriberMetaDataManager.getRemoteSubscribersMD();
        for (String topic : remoteSubscribersMD.keySet()) {
            if (null == localSubscribersMD.get(topic)) {
                result.put(topic, new ArrayList<SubscriberMetaDataBean>(remoteSubscribersMD.get(topic).values()));
            } else {
                for (String subscriberName : remoteSubscribersMD.get(topic).keySet()) {
                    if (null == localSubscribersMD.get(topic).get(subscriberName)) {
                        if (null == result.get(topic)) {
                            result.put(topic, new ArrayList<SubscriberMetaDataBean>());
                        }
                        result.get(topic).add(remoteSubscribersMD.get(topic).get(subscriberName));
                    }
                }
            }
        }

        String output = "format error";
        try {
            output = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return output;
    }
}
