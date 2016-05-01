package com.successfactors.hermes.util;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.zookeeper.data.Stat;

import com.sf.sfv4.util.StringUtils;

import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;

/**
 * This class depends on Zookeeper, otherwise, it won't handle duplicate messages issue.
 * 
 * Prevent duplicate message on client.
 *   1. Hornet server(without backup) shutdown, server haven't receive the acknowledge from client; then when server restart, the message will be redelivered.
 *   2. Hornet server(with backup) shutdown, server haven't receive the acknowledge from client; then when backup server take over, the message will be redelivered.
 *   3. Without shared store, when primary server down, the messages in queue have been sent by the backup server; then backup server down; the restart primary server, the messages in queue will be resent. TODO
 *   4. Without shared store, when backup server take over, and down, the messages in queue will be lost. TODO HornetQ document suggest that OPS should decide which server(have the latest data) should start as primary first.
 * @author wkliu
 *
 */
public class DuplicateMessageGuarantee {
  private static Logger logger = LogManager.getLogger();
  
  private static final String DUPLICATE_MESSAGE_NODE_NAME = "/DuplicateMessage";
  private static PathChildrenCache duplicateMessages = null;
  static {
    if(ZookeeperUtil.client != null) {
      duplicateMessages = new PathChildrenCache(ZookeeperUtil.client, DUPLICATE_MESSAGE_NODE_NAME, true);
      try {
        duplicateMessages.start(StartMode.BUILD_INITIAL_CACHE);
        removeDirtyData();
      } catch (Exception e) {
        logger.error("Failed to build initial duplicate message cache.", e);
      }
    }
  }

  /**
   * Put the message which may have duplicate issue to zookeeper.
   * @param messageId
   * @param subscriberName
   */
  public static void putDuplicateMessageToZookeeper(String messageId, String subscriberName) {
    if(ZookeeperUtil.client == null) {
      return;
    }
    
    logger.info("Adding duplicate check for message[" + messageId + "]");
    String path = DUPLICATE_MESSAGE_NODE_NAME + "/" + subscriberName;
    try {
      Stat stat = ZookeeperUtil.client.checkExists().forPath(path);
      if(null != stat) {
        String messageIds = new String(ZookeeperUtil.client.getData().forPath(path), StringUtils.DEFAULT_CHARSET);
        if(!messageIds.contains(messageId)) {
          messageIds += ((messageIds.length() > 0) ? "," : "") + messageId + "-" + System.currentTimeMillis();
          ZookeeperUtil.client.setData().forPath(path, messageIds.getBytes(StringUtils.DEFAULT_CHARSET));
        }
      } else {
        ZookeeperUtil.client.create().creatingParentsIfNeeded().forPath(path, (messageId + "-" + System.currentTimeMillis()).getBytes(StringUtils.DEFAULT_CHARSET));
      }
    } catch (Exception e) {
      logger.error("Unexpected exception when put duplicate message to Zookeeper.", e);
    }
  }

  public static boolean isDuplicate(String messageId, String subscriberName) {
    if(ZookeeperUtil.client == null) {
      return false;
    }
    
    String path = DUPLICATE_MESSAGE_NODE_NAME + "/" + subscriberName;
//    try {
//      duplicateMessages.rebuildNode(path);
//    } catch (Exception e) {
//      logger.error("Unexpected exception when rebuild node: " + subscriberName, e);
//    }
    ChildData subscriber = duplicateMessages.getCurrentData(path);
    if(null != subscriber) {
      String messageIds = new String(subscriber.getData(), StringUtils.DEFAULT_CHARSET);
      if(messageIds.contains(messageId)) {
        return true;
      }
    }

    return false;
  }
  
  public static void removeDuplicate(String messageId, String subscriberName) {
    logger.info("Remove duplicate check for message[" + messageId + "]");
    String path = DUPLICATE_MESSAGE_NODE_NAME + "/" + subscriberName;
    String messageIds = new String(duplicateMessages.getCurrentData(path).getData(), StringUtils.DEFAULT_CHARSET);
    int startIndex = messageIds.indexOf(messageId);
    if(-1 != startIndex) {
      int endIndex = messageIds.indexOf(",", startIndex);
      endIndex = (endIndex != -1) ? endIndex + 1 : messageIds.length();
      startIndex = (startIndex != 0) ? startIndex - 1 : startIndex;
      String messageToRemove = messageIds.substring(startIndex, endIndex);
      messageIds = messageIds.replace(messageToRemove, "");
      try {
        ZookeeperUtil.client.setData().forPath(path, messageIds.getBytes(StringUtils.DEFAULT_CHARSET));
      } catch (Exception e) {
        logger.error("Unexpected exception when remove duplicate message to Zookeeper.", e);
      }
    }
  }

  /**
   * Start a thread to check duplicate messages in Zookeeper, clean up the dirty data.
   * TODO
   */
  private static void removeDirtyData() {
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        long currentTime = System.currentTimeMillis();
        long sevenDay = 7*24*60*60*1000;
        logger.info("Remove duplicate messages which over 7 days.");
        for(ChildData subscriber : duplicateMessages.getCurrentData()) {
          String duplicateMessageIds = new String(subscriber.getData(), StringUtils.DEFAULT_CHARSET);
          if(!StringUtils.isBlank(duplicateMessageIds)) {
            String[] duplicateMessageArray = duplicateMessageIds.split(",");
            for(String duplicateMessage : duplicateMessageArray) {
              int index = duplicateMessage.lastIndexOf("-");
              String messageid = duplicateMessage.substring(0, index);
              //TODO 
              long timeStamp = Long.parseLong(duplicateMessage.substring(index + 1));
              //more than 7 days
              if(currentTime - timeStamp > sevenDay) {
                String fullPath = subscriber.getPath();
                removeDuplicate(messageid, fullPath.substring(fullPath.lastIndexOf("/") + 1, fullPath.length()));
              }
            }
          }
        }
        logger.info("Finish removeing duplicate messages which over 7 days.");
      }
    };
    Timer timer = new Timer("hermes duplicate message guard");
    if(ZookeeperUtil.client != null) {
      timer.schedule(task, 0, 86400000);
    }
  }
}
