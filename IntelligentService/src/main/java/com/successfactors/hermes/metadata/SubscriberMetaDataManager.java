package com.successfactors.hermes.metadata;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.data.Stat;

import com.sf.sfv4.util.StringUtils;

import com.successfactors.category.api.enums.ModuleEnum;
import EventCenter.hermes.bean.SubscriberMetaDataBean;
import EventCenter.hermes.config.SubscriberConfigListener;
import EventCenter.hermes.core.SFSubscriber;
import EventCenter.hermes.core.annotation.MetaData;
import EventCenter.hermes.core.annotation.Subscriber;
import EventCenter.hermes.util.JmxUtil;
import EventCenter.hermes.util.SubscriberLeaderAcquiredListener;
import EventCenter.hermes.util.ZookeeperUtil;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;

public class SubscriberMetaDataManager {
  private static Logger logger = LogManager.getLogger();

  //Used to validate the unique metadata id
  private static Map<String, SubscriberMetaDataBean> uniqueIdMap = new HashMap<String, SubscriberMetaDataBean>();

  //Map<topic, Map<subscribername, SubscriberMetaDataBean>>
  private static Map<String, Map<String, SubscriberMetaDataBean>> localSubscribersMD = new HashMap<String, Map<String, SubscriberMetaDataBean>>();

  //The meta data in remote zookeeper, Map<Topic, PathChildrenCache>
  private static Map<String, PathChildrenCache> remoteSubscribersCache = new HashMap<String, PathChildrenCache>();

  public static final String SEB_META_DATA_NODE = "/MetaData";
  
  static {
    if(ZookeeperUtil.client != null) {
      if(isSubscriberServer()) {
        ZookeeperUtil.addLeaderAcquiredListener(new SubscriberLeaderAcquiredListener(){

          @Override
          public void run() {
            refreshMetaData();
          }
          
        });
      }
      syncMetaData();
    } else {
      //Only have local subscribers' meta data.
    }
    logger.info("initialize SubscriberMetaData MBean.");
    JmxUtil.register(new SubscriberMetaData(), "SubscriberMetaData");
  }

  /**
   * Get all subscribers' meta data.
   * 
   * @return Map<topic, Map<subscribername, SubscriberMetaDataBean>>
   */
  public static Map<String, List<SubscriberMetaDataBean>> getAllSubscribers() {
    Map<String, Map<String, SubscriberMetaDataBean>> remoteSubscribersMD = getRemoteSubscribersMD();
    Map<String, Map<String, SubscriberMetaDataBean>> subscribersMD = (remoteSubscribersMD.size() > 0) ? remoteSubscribersMD : localSubscribersMD;
    Map<String, List<SubscriberMetaDataBean>> result = new HashMap<String, List<SubscriberMetaDataBean>>();
    for(String topic : subscribersMD.keySet()) {
      result.put(topic, new ArrayList<SubscriberMetaDataBean>(subscribersMD.get(topic).values()));
    }

    return result;
  }

  public static SubscriberMetaDataBean getMetaData(String topic, String subscriberName) {
    Map<String, Map<String, SubscriberMetaDataBean>> remoteSubscribersMD = getRemoteSubscribersMD();
    Map<String, Map<String, SubscriberMetaDataBean>> subscribersMD = (remoteSubscribersMD.size() > 0) ? remoteSubscribersMD : localSubscribersMD;
    if(null != subscribersMD.get(topic)) {
      return subscribersMD.get(topic).get(subscriberName);
    }

    return null;
  }

  /**
   * Scan meta data for bizX subscribers
   * @param clazz
   */
  public static void scanMetaData(Class clazz) {
    if(SFSubscriber.class.isAssignableFrom(clazz)) {
      Subscriber anno = (Subscriber) clazz.getAnnotation(Subscriber.class);
      if(null != anno && !anno.metaData().id().trim().equals("")) {
        String topic = anno.topic();
        MetaData metaData = anno.metaData();
        ModuleEnum module = null;
        try {
          module = ModuleEnum.valueOf(metaData.module());
        } catch(Exception e) {
          logger.error("Invalid module : " + metaData.module() + " for subscriber[" + clazz.getCanonicalName() + "]", e);
        }
        SubscriberMetaDataBean bean = new SubscriberMetaDataBean(clazz.getCanonicalName(), metaData.id().trim(),
            metaData.nameI18n(), metaData.descI18n(), Arrays.asList(metaData.impactArea()),
            module, metaData.isSmartSub(), metaData.canDisable(),
            metaData.featureEnableCheckFlag());
        registerMetaData(topic, bean);
      } else {
        logger.info("Subscriber [" + clazz.getCanonicalName() + "] does not specify the meta data.");
      }
    }
  }

  /**
   * Register meta data for non-bizX(rest) subscribers
   * @param metaData
   * @param subscriberName
   */
  public static void registerMetaData(String topic, SubscriberMetaDataBean metaData) {
    if(null != metaData && null != metaData.getId()) {
      String subscriberName = metaData.getSubscriberName();
      if(uniqueIdMap.containsKey(metaData.getId())) {
        logger.error("There are two subscribers [" + uniqueIdMap.get(metaData.getId()) + ", " + subscriberName + "] with the same id : [" + metaData.getId() + "]");
      } else if(isValid(metaData)) {
        uniqueIdMap.put(metaData.getId(), metaData);
        if(null == localSubscribersMD.get(topic)) {
          localSubscribersMD.put(topic, new HashMap<String, SubscriberMetaDataBean>());
        }
        localSubscribersMD.get(topic).put(subscriberName, metaData);
        registerOnZKIfRequired(topic, metaData);
        logger.info("Register meta data for " + subscriberName);
      }
    }
  }

  /**
   * Register meta data on zookeeper when
   * 1. This is the subscriber server.
   * 2. This subscriber server is the leader to manage the meta data node.
   * @param topic 
   * @param metaData
   * @param subscriberName
   */
  private static void registerOnZKIfRequired(String topic, SubscriberMetaDataBean metaData) {
    if(ZookeeperUtil.isLeaderSubscriberServer()) {
      String subscriberName = metaData.getSubscriberName();
      String path = SEB_META_DATA_NODE + "/" + topic + "/" + subscriberName;
      CuratorFramework client = ZookeeperUtil.client;
      try {
        String metaDataJson = metaData.toJSON();
        if(null != metaDataJson) {
          Stat stat = client.checkExists().forPath(path);
          if(null == stat) {
            client.create().creatingParentsIfNeeded().forPath(path, metaDataJson.getBytes(StringUtils.DEFAULT_CHARSET));
          } else {
            byte[] oldMetaData = remoteSubscribersCache.get(topic).getCurrentData(SEB_META_DATA_NODE + "/" + topic + "/" + subscriberName).getData();
            SubscriberMetaDataBean oldBean = SubscriberMetaDataBean.fromJSON(oldMetaData, subscriberName);
            if(null != oldBean && !oldBean.equals(metaData)) {
              logger.warn("The metaData is updated for subscriber : " + subscriberName);
              logger.warn("old meta : " + new String(oldMetaData, StringUtils.DEFAULT_CHARSET));
              logger.warn("new meta : " + metaDataJson);
              client.setData().forPath(path, metaDataJson.getBytes(StringUtils.DEFAULT_CHARSET));
            }
          }
        }
      } catch (Exception e) {
        logger.error("Failed to create node for subscriber : " + subscriberName, e);
      }
    }
  }

  /**
   * Acquire leadership to manage the meta data node.
   * When acquired, refresh the meta data node.
   */
  private static void acquireLeadership() {
    //The method is moved to ZookeeperUtil.
  }

  /**
   * Refresh the meta data in zookeeper.
   */
  private static void refreshMetaData() {
    logger.info("Refresh subscriber meta data in zookeeper.");
    try {
      List<String> topics = ZookeeperUtil.client.getChildren().forPath(SEB_META_DATA_NODE);
      for(String topic : topics) {
        List<String> subscribers = ZookeeperUtil.client.getChildren().forPath(SEB_META_DATA_NODE + "/" + topic);
        for(String subscriberName : subscribers) {
          ZookeeperUtil.client.delete().forPath(SEB_META_DATA_NODE + "/" + topic + "/" + subscriberName);
        }
        ZookeeperUtil.client.delete().forPath(SEB_META_DATA_NODE + "/" + topic);
      }
    } catch(Exception e) {
      logger.error("Failed to delete old meta data node.", e);
    }

    for(String topic : localSubscribersMD.keySet()) {
      for(SubscriberMetaDataBean subscriber : localSubscribersMD.get(topic).values()) {
        String subscriberName = subscriber.getSubscriberName();
        CuratorFramework client = ZookeeperUtil.client;
        String path = SEB_META_DATA_NODE + "/" + topic + "/" + subscriberName;
        try {
          String metaDataJson = subscriber.toJSON();
          client.create().creatingParentsIfNeeded().forPath(path, metaDataJson.getBytes(StringUtils.DEFAULT_CHARSET));
        } catch (Exception e) {
          logger.error("Failed to create meta data node in zookeeper for : " + subscriberName, e);
        }
      }
    }
  }

  /**
   * Sync meta data info from zookeeper
   */
  private static void syncMetaData() {
    ZookeeperUtil.createNodeIfNonExists(SEB_META_DATA_NODE);
    CuratorFramework client = ZookeeperUtil.client;
    
    @SuppressWarnings("resource")
    PathChildrenCache topicCache = new PathChildrenCache(client, SEB_META_DATA_NODE, true);
    try {
      topicCache.start(StartMode.BUILD_INITIAL_CACHE);
      topicCache.getListenable().addListener(new PathChildrenCacheListener(){

        @Override
        public void childEvent(CuratorFramework paramCuratorFramework,
            PathChildrenCacheEvent event)
            throws Exception {
          String purePath, topic;
          switch(event.getType()) {
            case CHILD_ADDED:
              purePath = event.getData().getPath();
              topic = ZKPaths.getNodeFromPath(purePath);
              PathChildrenCache newTopic = new PathChildrenCache(ZookeeperUtil.client, purePath, true);
              newTopic.start(StartMode.BUILD_INITIAL_CACHE);
              remoteSubscribersCache.put(topic, newTopic);
              break;
            case CHILD_UPDATED:
              logger.error("This line shouldn't be executed.");
              break;
            case CHILD_REMOVED:
              purePath = event.getData().getPath();
              topic = ZKPaths.getNodeFromPath(purePath);
              PathChildrenCache removedTopic = remoteSubscribersCache.remove(topic);
              removedTopic.close();
              break;
            default:
              logger.warn("Zookeeper connection event: " + event.getType());
              break;
          }
        }
        
      });
      List<ChildData> topics = topicCache.getCurrentData();
      for(ChildData topic : topics) {
        PathChildrenCache subscriberCache = new PathChildrenCache(ZookeeperUtil.client, topic.getPath(), true);
        subscriberCache.start(StartMode.BUILD_INITIAL_CACHE);
        remoteSubscribersCache.put(ZKPaths.getNodeFromPath(topic.getPath()), subscriberCache);
      }
    } catch (Exception e) {
      logger.error("Failed to sync hermes meta data from zookeeper.", e);
    }
  }

  private static boolean isSubscriberServer() {
    return StringUtils.getBoolean(System.getProperty(SubscriberConfigListener.SUBSCRIBER_REG_ENABLED_PROP), false);
  }

  private static boolean isValid(SubscriberMetaDataBean bean) {
    String subscriberName = bean.getSubscriberName();
    if(bean.isSmartSub()) {
      if(StringUtils.isBlank(bean.getId())) {
        logger.error("Invalid meta data for subscriber[" + subscriberName + "] : ID can't be empty for SmartSuite subscriber.");
        return false;
      }
      if(StringUtils.isBlank(bean.getNameI18n())) {
        logger.error("Invalid meta data for subscriber[" + subscriberName + "] : NameI18n can't be empty for SmartSuite subscriber.");
        return false;
      }
      if(StringUtils.isBlank(bean.getDescI18n())) {
        logger.error("Invalid meta data for subscriber[" + subscriberName + "] : DescI18n can't be empty for SmartSuite subscriber.");
        return false;
      }
      if(bean.getImpactArea().size() < 1) {
        logger.error("Invalid meta data for subscriber[" + subscriberName + "] : ImpactArea can't be empty for SmartSuite subscriber.");
        return false;
      }
      if(bean.getModule() == null || bean.getModule() == ModuleEnum.DO_NOT_USE) {
        logger.error("Invalid meta data for subscriber[" + subscriberName + "] : Invalid module");
        return false;
      }
    }
    
    return true;
  }

  public static Map<String, Map<String, SubscriberMetaDataBean>> getRemoteSubscribersMD() {
    Map<String, Map<String, SubscriberMetaDataBean>> result = new HashMap<String, Map<String, SubscriberMetaDataBean>>();
    for(String topic : remoteSubscribersCache.keySet()) {
      result.put(topic, new HashMap<String, SubscriberMetaDataBean>());
      for(ChildData subscriberNameData : remoteSubscribersCache.get(topic).getCurrentData()) {
        String subscriberName = ZKPaths.getNodeFromPath(subscriberNameData.getPath());
        SubscriberMetaDataBean metaData = SubscriberMetaDataBean.fromJSON(subscriberNameData.getData(), subscriberName);
        result.get(topic).put(subscriberName, metaData);
      }
    }
    
    return result;
  }

  public static Map<String, Map<String, SubscriberMetaDataBean>> getLocalSubscribersMD() {
    return localSubscribersMD;
  }
}
