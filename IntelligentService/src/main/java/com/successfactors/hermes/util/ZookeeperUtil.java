package com.successfactors.hermes.util;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.Participant;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

import com.sf.sfv4.util.StringUtils;

import EventCenter.hermes.config.SubscriberConfigListener;
import EventCenter.logging.api.LogManager;
import EventCenter.logging.api.Logger;

public class ZookeeperUtil {
  
  private static final Logger log = LogManager.getLogger();

  private static final String ZOOKEEPER_ADDRESS = SEBConstants.ZK_SERVER_LIST_PROPERTY;
  private static final String ZOOKEEPER_ROOT_NODE = SEBConstants.ZK_CONTEXT_PATH_PROPERTY;
  public static final String SEB_SUBSCRIBER_SERVER_NODE = "/SubscriberServer";

  //The root node : /hermes
  private static final String ZOOKEEPER_ROOT = "hermes";

  public static final CuratorFramework client;
  //The Listener just be executed once.
  private static List<SubscriberLeaderAcquiredListener> leaderAcquiredListener = new ArrayList<SubscriberLeaderAcquiredListener>();
  
  private static LeaderLatch leaderLatch = null;
  
  static {
    String zkAddress = System.getProperty(ZOOKEEPER_ADDRESS);
    String zkRoot = System.getProperty(ZOOKEEPER_ROOT_NODE);
    if(StringUtils.isBlank(zkAddress)) {
      log.error("Please config zookeeper address [" + ZOOKEEPER_ADDRESS + "] for hermes");
      client = null;
    } else {
      RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
      zkRoot = (null != zkRoot) ? ZOOKEEPER_ROOT + "/" + zkRoot : ZOOKEEPER_ROOT;
      client = CuratorFrameworkFactory.builder().connectString(zkAddress).retryPolicy(retryPolicy).namespace(zkRoot).build();
      client.start();
      if(isSubscriberServer()) {
        acquireLeadership();
      }
    }
  }
  
  public static void createNodeIfNonExists(String path) {
    Stat stat;
    try {
      stat = client.checkExists().forPath(path);
      if(null == stat) {
        client.create().forPath(path);
      }
    } catch (Exception e) {
      log.error("Failed to create node : " + path, e);
    }
  }

  public static boolean isLeaderSubscriberServer() {
    return isSubscriberServer() && leaderLatch != null && leaderLatch.hasLeadership();
  }

  /**
   * Can only be called by subscriber server, otherwise, the value is meaningless.
   * @return
   */
  public static boolean hasLeaderSubscriberServer() {
    try {
      return leaderLatch != null && leaderLatch.getLeader().isLeader();
    } catch (Exception e) {
      log.error("Unexpected exception when check leadership.", e);
    }
    
    return false;
  }
  
  private static boolean isSubscriberServer() {
    return StringUtils.getBoolean(System.getProperty(SubscriberConfigListener.SUBSCRIBER_REG_ENABLED_PROP), false);
  }

  /**
   * The Listener just be executed once.
   */
  public static void addLeaderAcquiredListener(SubscriberLeaderAcquiredListener listener) {
    if(isLeaderSubscriberServer()) {
      listener.run();
    } else {
      leaderAcquiredListener.add(listener);
    }
  }
  
  private static void acquireLeadership() {
    if(null == leaderLatch) {
      leaderLatch = new LeaderLatch(ZookeeperUtil.client, SEB_SUBSCRIBER_SERVER_NODE, "{host:" + ManagementFactory.getRuntimeMXBean().getName() + ",configFileFirst:" + SEBSettings.useConfigurationFileFirst() + "}");
      try {
        leaderLatch.start();
      } catch (Exception e1) {
        log.error("Failed to start the leaderLatch", e1);
      }
    }
    new Thread(new Runnable() {
      @Override
      public void run() {
        log.info("Acquiring subscriber server leadership.");
        try {
          leaderLatch.await();
          log.info("Got subscriber server leadership.");
          for(SubscriberLeaderAcquiredListener listener : leaderAcquiredListener) {
            listener.run();
          }
          leaderAcquiredListener = new ArrayList<SubscriberLeaderAcquiredListener>();
        } catch (Exception e) {
          log.error("Unexpected exception when acquiring leadership.", e);
        }
      }
    }).start();
  }

  public static Collection<Participant> getSubServers() {
    if(isSubscriberServer()) {
      try {
        return leaderLatch.getParticipants();
      } catch (Exception e) {
        log.error("Failed to get all subscriber server.", e);
      }
    }
    
    return null;
  }
}
