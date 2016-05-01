package com.successfactors.hermes.util;

/**
 * hermes Constants
 * @author yyang
 *
 */
public class SEBConstants {

  /** Subscriber constants */

  /** Default subscriber consuming window size set as 1MB (per subscriber) */
  public static final int SUB_CONSUMING_WINDOW_SIZE = 1048576;

  /** Max subscriber consuming rate set as 1000000/s (per subscriber) */
  public static final int SUB_MAX_CONSUMING_RATE = 1000000;

  /** Default concurrent subscriber count per container */
  public static final int SUB_DEFAULT_CONCURRENT_SUBSCRIBER_COUNT = 1;

  /** Maximum concurrent subscriber count per container */
  public static final int SUB_MAX_CONCURRENT_SUBSCRIBER_COUNT = 15;

  /** Idle execution limit for each subscriber working thread */
  public static final int SUB_IDLE_EXECUTION_LIMIT = 30000;



  /** Publisher constants */
  /**
   * Default publisher producing window size set as 64KB 
   * (This is the overall amount of data size of all publishers being created 
   * on the same ServerLocator/ConnectionFactory)
   */
  public static final int PUB_PRODUCING_WINDOW_SIZE = 65536;

  /** Max publisher producing rate set as 1000000/s (per publisher) */
  public static final int PUB_MAX_PRODUCING_RATE = 1000000;

  /** Zookeeper server list */
  public static final String ZK_SERVER_LIST_PROPERTY = "hermes.zookeeper.address";
  /** Zookeeper context path */
  public static final String ZK_CONTEXT_PATH_PROPERTY = "hermes.zookeeper.root";
  /** hermes server list */
  public static final String SEB_SERVER_LIST_PROPERTY = "com.successfactors.hermes.servers_list";
  /** hermes cluster username */
  public static final String SEB_CLUSTER_USER_PROPERTY = "com.successfactors.hermes.provider.username";
  /** hermes cluster password */
  public static final String SEB_CLUSTER_PASS_PROPERTY = "com.successfactors.hermes.provider.password";
  /** Subscriber enablement */
  public static final String SUB_ENABLEMENT_PROPERTY = "hermes.subscriberRegEnabled";
  /** hermes config ui readonly */
  public static final String CONFIG_UI_READONLY = "hermes.config.ui.readonly";

  /** line break */
  public static final String LINE_BREAK = "\r\n";

  /** Invisible constructor */
  private SEBConstants() {
  }
}
