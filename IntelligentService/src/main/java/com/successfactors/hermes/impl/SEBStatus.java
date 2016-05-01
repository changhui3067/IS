package com.successfactors.hermes.impl;

import static EventCenter.hermes.util.SEBConstants.LINE_BREAK;
import static EventCenter.hermes.util.SEBConstants.SEB_CLUSTER_PASS_PROPERTY;
import static EventCenter.hermes.util.SEBConstants.SEB_CLUSTER_USER_PROPERTY;
import static EventCenter.hermes.util.SEBConstants.SEB_SERVER_LIST_PROPERTY;
import static EventCenter.hermes.util.SEBConstants.SUB_ENABLEMENT_PROPERTY;
import static EventCenter.hermes.util.SEBConstants.ZK_CONTEXT_PATH_PROPERTY;
import static EventCenter.hermes.util.SEBConstants.ZK_SERVER_LIST_PROPERTY;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.curator.framework.CuratorFramework;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.core.client.ClientSessionFactory;
import org.hornetq.api.core.client.HornetQClient;
import org.hornetq.api.core.client.ServerLocator;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;
import org.jboss.mx.loading.UnifiedClassLoader3;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.sf.sfv4.util.StringUtils;
import EventCenter.hermes.util.ZookeeperUtil;


/**
 * Service Event Bus Status Management Bean
 * @author yyang
 *
 */
@ManagedResource(description = "Service Event Bus Status")
public class SEBStatus {

  @ManagedAttribute(description = "Configured Zookeeper Server List")
  public String getZookeeperServerList() {
    return String.format("%s=%s", ZK_SERVER_LIST_PROPERTY, 
        System.getProperty(ZK_SERVER_LIST_PROPERTY));
  }

  @ManagedAttribute(description = "Configured Zookeeper Context Path")
  public String getZookeeperContextPath() {
    return String.format("%s=%s", ZK_CONTEXT_PATH_PROPERTY, 
        System.getProperty(ZK_CONTEXT_PATH_PROPERTY));
  }

  @ManagedAttribute(description = "Configured HornetQ Server List")
  public String getHornetQServerList() {
    return String.format("%s=%s", SEB_SERVER_LIST_PROPERTY, 
        System.getProperty(SEB_SERVER_LIST_PROPERTY));
  }

  @ManagedAttribute(description = "Configured HornetQ Cluster Username")
  public String getHornetQClusterUser() {
    return String.format("%s=%s", SEB_CLUSTER_USER_PROPERTY, 
        System.getProperty(SEB_CLUSTER_USER_PROPERTY));
  }

  @ManagedAttribute(description = "Configured HornetQ Cluster Password")
  public String getHornetQClusterPass() {
    return String.format("%s=%s", SEB_CLUSTER_PASS_PROPERTY, 
        System.getProperty(SEB_CLUSTER_PASS_PROPERTY));
  }

  @ManagedAttribute(description = "Configured Subscriber Server Enablement")
  public String getSubscriberEnablement() {
    return String.format("%s=%s", SUB_ENABLEMENT_PROPERTY, 
        System.getProperty(SUB_ENABLEMENT_PROPERTY));
  }

  @ManagedOperation(description = "Check HonetQ Server Accessibility")
  public String checkHornetQ() {
    StringBuilder resultBuilder = new StringBuilder();
    String serverList = System.getProperty(SEB_SERVER_LIST_PROPERTY);
    long communicationPort = 5445;
    if (StringUtils.isEmpty(serverList)) {
      resultBuilder.append(String.format(
          "ERROR: Missing hermes server list configuration!!! [%s]", SEB_SERVER_LIST_PROPERTY));
      return resultBuilder.toString();
    }
    resultBuilder.append(String.format("Configured HornetQ Server List: %s", 
        System.getProperty(SEB_SERVER_LIST_PROPERTY))).append(LINE_BREAK);
    resultBuilder.append(String.format("Configured HornetQ Cluster Username: %s", 
        System.getProperty(SEB_CLUSTER_USER_PROPERTY))).append(LINE_BREAK);
    resultBuilder.append(String.format("Configured HornetQ Cluster Password: %s", 
        System.getProperty(SEB_CLUSTER_PASS_PROPERTY))).append(LINE_BREAK);
    
    List<TransportConfiguration> transportConfigurations = new ArrayList<TransportConfiguration>();
    StringTokenizer st = new StringTokenizer(serverList, " ,");
    while (st.hasMoreTokens()) {
      Map<String, Object> parameters = new HashMap<String, Object>();
      parameters.put(TransportConstants.PORT_PROP_NAME, String.valueOf(communicationPort));
      parameters.put(TransportConstants.HOST_PROP_NAME, st.nextToken());
      transportConfigurations.add(new TransportConfiguration(
          NettyConnectorFactory.class.getName(), parameters));
    }
    
    ServerLocator locator = HornetQClient.createServerLocatorWithHA(
        transportConfigurations.toArray(new TransportConfiguration[transportConfigurations.size()]));
    ClientSessionFactory sessionFactory = null;
    try {
      resultBuilder.append(LINE_BREAK);
      sessionFactory = locator.createSessionFactory();
      resultBuilder.append(locator.getTopology()).append(LINE_BREAK);
    } catch (Exception e) {
      resultBuilder.append(e.getMessage()).append(LINE_BREAK);
    } finally {
      if (sessionFactory != null) {
        sessionFactory.close();
      }
      locator.close();
    }
    return resultBuilder.toString();
  }

  @ManagedOperation(description = "Check Zookeeper Server Accessibility")
  public String checkZookeeper() {
    StringBuilder resultBuilder = new StringBuilder();
    String serverList = System.getProperty(ZK_SERVER_LIST_PROPERTY);
    if (StringUtils.isEmpty(serverList)) {
      resultBuilder.append(String.format(
          "ERROR: Missing Zookeeper server list configuration!!! [%s]", ZK_SERVER_LIST_PROPERTY));
      return resultBuilder.toString();
    }
    resultBuilder.append(String.format("Configured Zookeeper Server List: %s", 
        System.getProperty(ZK_SERVER_LIST_PROPERTY))).append(LINE_BREAK);
    resultBuilder.append(String.format("Configured Zookeeper Context Path: %s", 
        System.getProperty(ZK_CONTEXT_PATH_PROPERTY))).append(LINE_BREAK);
    
    resultBuilder.append(LINE_BREAK);
    CuratorFramework client = ZookeeperUtil.client;
    resultBuilder.append(String.format(
        "Zookeeper client connected status: %s", client == null ? "NULL" : client.getZookeeperClient().isConnected()));
    resultBuilder.append(LINE_BREAK);
    return resultBuilder.toString();
  }

  @ManagedOperation(description = "Check Subscriber Server Deployment")
  public String checkSubscriberDeployment() {
    StringBuilder resultBuilder = new StringBuilder();
    String subEnablement = System.getProperty(SUB_ENABLEMENT_PROPERTY);
    resultBuilder.append(String.format(
        "Configured Subscriber Server Enablement: %s", subEnablement)).append(LINE_BREAK);
    
    List<URL> subDeployments = new ArrayList<URL>();
    for (URL url : ((UnifiedClassLoader3) getClass().getClassLoader()).getAllURLs()) {
      if (url.toString().contains("hermes")) {
        subDeployments.add(url);
      }
    }
    
    if (Boolean.valueOf(subEnablement)) {
      if (subDeployments.isEmpty()) {
        resultBuilder.append("ERROR: Missing deployment of zseb_xxx.ear!!!").append(LINE_BREAK);
      }
    } else {
      if (!subDeployments.isEmpty()) {
        resultBuilder.append(
            "ERROR: Underlying server is NOT configured as SUBSCRIBER server, "
                + "but found deployment of zseb_xxx.ear!!!").append(LINE_BREAK);
      }
    }
    
    if (!subDeployments.isEmpty()) {
      resultBuilder.append(LINE_BREAK);
      resultBuilder.append("Subscriber deployment: ");
      resultBuilder.append(LINE_BREAK);
      for (URL subDeployment : subDeployments) {
        resultBuilder.append(subDeployment).append(LINE_BREAK);;
      }
    }
    
    if (subDeployments.size() > 50) {
      resultBuilder.append(LINE_BREAK);
      resultBuilder.append("WARN: Possible multiple deployment of zseb_xxx.ear.");
    }
    
    return resultBuilder.toString();
  }
}
