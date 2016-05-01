/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.successfactors.hermes.impl.hornetq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.hornetq.api.core.DiscoveryGroupConfiguration;
import org.hornetq.api.core.HornetQException;
import org.hornetq.api.core.HornetQExceptionType;
import org.hornetq.api.core.SimpleString;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.core.UDPBroadcastGroupConfiguration;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.ClientSessionFactory;
import org.hornetq.api.core.client.ClusterTopologyListener;
import org.hornetq.api.core.client.FailoverEventListener;
import org.hornetq.api.core.client.FailoverEventType;
import org.hornetq.api.core.client.HornetQClient;
import org.hornetq.api.core.client.ServerLocator;
import org.hornetq.api.core.client.TopologyMember;
import org.hornetq.api.core.client.loadbalance.ConnectionLoadBalancingPolicy;
import org.hornetq.api.core.client.loadbalance.RoundRobinConnectionLoadBalancingPolicy;
import org.hornetq.core.client.impl.ServerLocatorInternal;
import org.hornetq.core.client.impl.Topology;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;
import org.hornetq.utils.ClassloadingUtil;

import com.successfactors.appconfig.AppConfig;
import com.successfactors.hermes.util.SEBConstants;
import com.successfactors.hermes.util.SEBLog;
import com.successfactors.hermes.util.TopologyGuarantee;
import com.successfactors.logging.api.Logger;

/**
 * Session pool for ClientSession
 *
 * @author ayarmolenko
 *
 */
public class HQSessionPool {

  private static final Logger log = SEBLog.getLogger(HQSessionPool.class);
  private static final HQSessionPool INSTANCE = new HQSessionPool();
  private static final String CONNECTION_TIMEOUT_PROP = "com.successfactors.hermes.connection.timeout";
  private static final String GROUP_ADDRESS_PROP = "com.successfactors.hermes.group_address";
  private static final String DIRECT_SERVERS_LIST = SEBConstants.SEB_SERVER_LIST_PROPERTY;
  private static final String GROUP_PORT_PROP = "com.successfactors.hermes.port";
  private static final String SESSION_POOL_SIZE_PROP = "com.successfactors.hermes.session_pool_size";
  private static final String PROVIDER_USER_NAME_PROP = SEBConstants.SEB_CLUSTER_USER_PROPERTY;
  private static final String PROVIDER_PASSWORD_PROP = SEBConstants.SEB_CLUSTER_PASS_PROPERTY;
  //will try to re-connect every 1 sec
  private static final int RECONNECT_INTERVAL = 1000;
  //10 sec of trying to re-connect
  private static final int RECONNECT_TIMEOUT_DEFAULT = 10 * 1000;
  private static final int SESSION_POOL_SIZE_PROP_DEFAILT = 512;
  //default ports
  private static final int GROUP_PORT_PROP_DEFAULT = 9876;
  private static final int DIRECT_PORT_PROP_DEFAULT = 5445;
  private ServerLocator locator = null;
//  private BlockingQueue<HQSession> availableSessions = new LinkedBlockingQueue<HQSession>();
  private Map<ClientSessionFactory, BlockingQueue<HQSession>> availableSessions = new HashMap<ClientSessionFactory, BlockingQueue<HQSession>>();
  private Map<ClientSessionFactory, BlockingQueue<HQSession>> tempUnavailableSessions = new HashMap<ClientSessionFactory, BlockingQueue<HQSession>>();
  //Map<TopologyMemberNodeId, ClientSessionFactory>
  private Map<String, ClientSessionFactory> availableSessionFactory = new ConcurrentHashMap<String, ClientSessionFactory>();
  private Map<String, ClientSessionFactory> tempUnavailableSessionFactory = new HashMap<String, ClientSessionFactory>();
  //Just used to controll the total session size
  private List<HQSession> sessions = new LinkedList<HQSession>();
  private Map<HQSession, HQSubscriber> subscribers = new HashMap<HQSession, HQSubscriber>();
  private int maxPoolSize = SESSION_POOL_SIZE_PROP_DEFAILT;
  private static final int POLL_TIMEOUT = 1000;
  private int curretPoolSize = 0;
  //Use the same load balancing policy with server locator for session pool.
  private ConnectionLoadBalancingPolicy sessionFactoryLoadBalancingPolicy;
  
  /**
   * default constructor
   */
  protected HQSessionPool() {
    maxPoolSize = Integer.getInteger(SESSION_POOL_SIZE_PROP, SESSION_POOL_SIZE_PROP_DEFAILT);
  }

  /**
   * Singleton method, returns the object instance
   * @return HQSessionPool instance
   */
  public static HQSessionPool getInstance() {
    return INSTANCE;
  }

  /**
   * Get HQSession for subscriber.
   * @param subscriber
   * @return
   * @throws HornetQException
   */
  public HQSession getSession(HQSubscriber subscriber) throws HornetQException {
    HQSession result = null;
    ClientSessionFactory factory = getSessionFactory();
    //Disabled message redistribution for duplicate message issue. Then we should create consumer for server which have unconsumed message first.
    for(ClientSessionFactory sessionFactory : availableSessions.keySet()) {
      HQSession session = getSession(sessionFactory);
      ClientSession.QueueQuery query = session.getClientSession().queueQuery(new SimpleString(subscriber.getQueue()));
      //The message can't be redistributed, should create consumer on this server.
      if(query.isExists() && query.getConsumerCount() == 0 && query.getMessageCount() > 0) {
        result = session;
        break;
      } else if(factory == sessionFactory) {
        result = session;
      }
    }
    if(null == result) {
      result = getSession(factory);
    }
    subscribers.put(result, subscriber);
    
    return result;
  }
  
  private HQSession getSession(ClientSessionFactory factory) throws HornetQException {
    HQSession session = null;
    //Step 1. check if there is already session in the pool
    BlockingQueue<HQSession> sessionPool = availableSessions.get(factory);
    if(null == sessionPool) {
      availableSessions.put(factory, new LinkedBlockingQueue<HQSession>());
    }
    while (true && sessionPool != null) {
      session = sessionPool.poll();
      if (session != null && session.isClosed()) {
        log.warn("Invalid session found in pool: " + session);
        deleteSession(session);
        continue; //ignore closed sessions and remove them from pool
      }
      break;
    }

    //Step 2. Create new session if there was no available session no session in pool
    if (session == null) {
      session = createSession(factory);
    }
    if (session == null || session.isClosed()) {
      throw new HornetQException(HornetQExceptionType.INTERNAL_ERROR, "No session available in the pool");
    }
    return session;
  }
  
  /**
   * Gets a session from pool If there is no available session in pool, creates
   * a new one
   *
   * @throws HornetQException If session pool reached it's maximum capacity
   * @return a session
   *
   */
  public HQSession getSession() throws HornetQException {
    ClientSessionFactory factory = getSessionFactory();
    
    return getSession(factory);
  }

  /**
   * Returns session back to pool
   *
   * @param session session to release.
   * @throws HornetQException If this session is not from pool or not locked
   */
  public void releaseSession(HQSession session) throws HornetQException {
    if(availableSessions.containsKey(session.getSessionFactory()) && !availableSessions.get(session.getSessionFactory()).contains(session)) {
      availableSessions.get(session.getSessionFactory()).offer(session);
    }
    else if(tempUnavailableSessions.containsKey(session.getSessionFactory()) && !tempUnavailableSessions.get(session.getSessionFactory()).contains(session)) {
      tempUnavailableSessions.get(session.getSessionFactory()).offer(session);
    }
    if(subscribers.containsKey(session)) {
      subscribers.remove(session);
    }
  }

  private HQSession createSession(ClientSessionFactory factory) throws HornetQException {
    if (sessions.size() >= maxPoolSize) {
      log.error("Unable to create more sessions, maximum pool size reached. "
              + "Set " + SESSION_POOL_SIZE_PROP + " property to increase pool size");
      return null;
    }

    String username = AppConfig.getString(PROVIDER_USER_NAME_PROP);
    String password = AppConfig.getString(PROVIDER_PASSWORD_PROP);

    ClientSession session = factory.createSession(username, password,
            false, //NO xa
            true, //auto-commit send
            true, //auto-commit ack
            false, //pre-ack DISABLED for manual ACK.
            0);

    session.start();
    

    HQSession hqSession = new HQSession(session);
    sessions.add(hqSession);

    log.info("New session created: " + session
            + " (pool size = " + sessions.size()
            + ", maxSize = " + maxPoolSize + ")");
    return hqSession;
  }

  /**
   * Get session factory with load balancing policy.
   *   -- Use ConcurrentHashMap to prevent concurrent issue here.
   * @return
   * @throws HornetQException
   */
  private ClientSessionFactory getSessionFactory() throws HornetQException {
    getLocator();
    ClientSessionFactory result = null;
    if(availableSessionFactory.isEmpty()) {
      log.error("Failed to get avaliable hornetQ sessionFactory.");
      throw new HornetQException("Failed to get avaliable hornetQ sessionFactory.");
    }
    if(null == sessionFactoryLoadBalancingPolicy) {
      sessionFactoryLoadBalancingPolicy = (ConnectionLoadBalancingPolicy)ClassloadingUtil.newInstanceFromClassLoader(getLocator().getConnectionLoadBalancingPolicyClassName());
    }
    int index = sessionFactoryLoadBalancingPolicy.select(availableSessionFactory.size());
    Iterator iterator = availableSessionFactory.keySet().iterator();
    while(iterator.hasNext()) {
      String key = (String) iterator.next();
      if(index-- == 0) {
        result = availableSessionFactory.get(key);
      }
    }

    return result;
  }

  private synchronized void deleteSession(HQSession session) {
    ClientSessionFactory sessionFactory = session.getSessionFactory();
    if(availableSessions.containsKey(sessionFactory)) {
      availableSessions.get(sessionFactory).remove(session);
    }
    if(tempUnavailableSessions.containsKey(sessionFactory)) {
      tempUnavailableSessions.get(sessionFactory).remove(session);
    }
    sessions.remove(session);

    log.info("Session removed from pool: " + session
            + " (pool size = " + sessions.size() + ")");

  }

  public synchronized ServerLocator getLocator() throws HornetQException {
    //ServerLocator locator = null;
    if (locator == null) {
      String multicastGroup = AppConfig.getString(GROUP_ADDRESS_PROP);
      String serversList = AppConfig.getString(DIRECT_SERVERS_LIST);

      int timeout = Integer.getInteger(CONNECTION_TIMEOUT_PROP, RECONNECT_TIMEOUT_DEFAULT);
      int nReconnects = timeout / RECONNECT_INTERVAL;
      log.info("HQ Connection timeout is " + timeout + " msec");

      int port = Integer.getInteger(GROUP_PORT_PROP, -1);

      if (multicastGroup != null) {
        if (port == -1) {
          port = GROUP_PORT_PROP_DEFAULT;
        }
        UDPBroadcastGroupConfiguration udpCfg =
                new UDPBroadcastGroupConfiguration(multicastGroup, port, null, -1);
        DiscoveryGroupConfiguration groupConfiguration =
                new DiscoveryGroupConfiguration(
                HornetQClient.DEFAULT_DISCOVERY_REFRESH_TIMEOUT,
                HornetQClient.DEFAULT_DISCOVERY_INITIAL_WAIT_TIMEOUT, udpCfg);

        locator = HornetQClient.createServerLocatorWithoutHA(groupConfiguration);

        log.info("Will connect using discovery group " + multicastGroup + ":" + port);
      } else if (serversList != null) {
        if (port == -1) {
          port = DIRECT_PORT_PROP_DEFAULT;
        }

        List<TransportConfiguration> tcs = new ArrayList<TransportConfiguration>();
        StringTokenizer st = new StringTokenizer(serversList, " ,");
        while (st.hasMoreTokens()) {
          String host = st.nextToken();
          Map<String, Object> parameters = new HashMap<String, Object>();
          parameters.put(TransportConstants.PORT_PROP_NAME, String.valueOf(port));
          parameters.put(TransportConstants.HOST_PROP_NAME, host);

          if(TopologyGuarantee.isActive(host, port)) {
            tcs.add(new TransportConfiguration(NettyConnectorFactory.class.getName(), parameters));
          }
        }
        if(tcs.isEmpty()) {
          log.error("All the configured hornetq servers(" + serversList + ") are invalid, please check.");
          throw new HornetQException(HornetQExceptionType.INTERNAL_ERROR, "All the configured hornetq servers(" + serversList + ") are invalid, please check.");
        }

//        locator = HornetQClient.createServerLocatorWithoutHA(
//                tcs.toArray(new TransportConfiguration[tcs.size()]));
        locator = HornetQClient.createServerLocatorWithHA(
          tcs.toArray(new TransportConfiguration[tcs.size()]));
        //this will override default behavior and iterate thru the list of servers from the 1st position
        locator.setConnectionLoadBalancingPolicyClassName(RoundRobinConnectionLoadBalancingPolicy.class
                .getName());


        log.info(
                "Will connect using static server list:" + serversList + ", port:" + port);
        nReconnects = nReconnects / tcs.size();
      } else {
        log.error("HornetQ server locator is not configured!\n"
                + "\tuse property " + GROUP_ADDRESS_PROP + " to define multicact group address\n"
                + "\tor property " + DIRECT_SERVERS_LIST + " to provided comma-separated server list.");
        throw new HornetQException(HornetQExceptionType.INTERNAL_ERROR, "HornetQ server locator is not configured");

      }

      locator.setConsumerMaxRate(STOPITSOT);
      locator.setInitialConnectAttempts(nReconnects);
      locator.setReconnectAttempts(nReconnects);
      locator.setRetryInterval(RECONNECT_INTERVAL);
      locator.setBlockOnAcknowledge(true);
//    locator.setConsumerWindowSize(-1);
      //In my investigation, below parameter is never used in hornetQ
//    locator.setFailoverOnInitialConnection(true);

      final Topology topology = locator.getTopology();
      topology.addClusterTopologyListener(new ClusterTopologyListener(){

        @Override
        public void nodeUP(TopologyMember member, boolean last) {
          log.info("Node up [" + member.getNodeId() + "] : " + member);
          addSessionFactory(member);
        }

        @Override
        public void nodeDown(long eventUID, String nodeID) {
          log.info("Node down : " + nodeID);
          ClientSessionFactory factory = availableSessionFactory.remove(nodeID);
          if(null != factory) {
            tempUnavailableSessions.put(factory, availableSessions.remove(factory));
            tempUnavailableSessionFactory.put(nodeID, factory);
          }
        }
      });
      //Create sessionFactory to trigger the hornetQ client get the cluster topology, and this sessionFactory is never used.
      ClientSessionFactory initSessionFactory = null;
      try {
        initSessionFactory = locator.createSessionFactory();
      } catch (InterruptedException e) {
        log.error("Unexcepted exception when waiting for avaliable HornetQ sessionFactory");
      } catch (Exception e) {
        log.warn("Failed to create initial hornetQ sessionFactory.", e);
        throw new HornetQException(HornetQExceptionType.INTERNAL_ERROR, "Failed to create initial session factory.", e);
      } finally {
        if(initSessionFactory != null) {
          initSessionFactory.close();
        }
      }
    }
    return locator;

  }

  /**
   * Add sessionFactory to a member if not exist.
   * @param member
   */
  private void addSessionFactory(TopologyMember member) {
    if(!availableSessionFactory.containsKey(member.getNodeId()) && !tempUnavailableSessionFactory.containsKey(member.getNodeId())) {
      
      TransportConfiguration transport = member.getLive();
      try {
        ClientSessionFactory factory = locator.createSessionFactory(transport);
        factory.addFailoverListener(new HQFailoverEventListener(member.getNodeId(), factory));
        availableSessionFactory.put(member.getNodeId(), factory);
        log.info("Add new sessionFactory for node : " + member.getNodeId());
      } catch (Exception e) {
        log.error("Failed to create SessionFactory", e);
      }
      if(availableSessions.size() > 0) {
        reallocateConsumer();
      }
    }
  }

  private class HQFailoverEventListener implements FailoverEventListener {
    
    private String nodeId;
    private ClientSessionFactory factory;

    public HQFailoverEventListener(String nodeId, ClientSessionFactory factory) {
      this.nodeId = nodeId;
      this.factory = factory;
    }

    @Override
    public void failoverEvent(FailoverEventType eventType) {
      log.info("Failover event[" + eventType + "] for node : " + nodeId);
      if(eventType == FailoverEventType.FAILURE_DETECTED) {
        if(availableSessionFactory.containsKey(factory)) {
          tempUnavailableSessions.put(factory, availableSessions.remove(factory));
          tempUnavailableSessionFactory.put(nodeId, availableSessionFactory.remove(nodeId));
        }
      } else if(eventType == FailoverEventType.FAILOVER_COMPLETED) {
        if(tempUnavailableSessionFactory.containsKey(factory)) {
          availableSessions.put(factory, tempUnavailableSessions.remove(factory));
          availableSessionFactory.put(nodeId, tempUnavailableSessionFactory.remove(nodeId));
        }
      } else if(eventType == FailoverEventType.FAILOVER_FAILED) {
        log.debug("Current sessions size : " + sessions.size());
        BlockingQueue<HQSession> sessionsToRemove = availableSessions.remove(factory);
        if(null != sessionsToRemove) {
          for(HQSession session : sessionsToRemove) {
            sessions.remove(session);
          }
        }
        sessionsToRemove = tempUnavailableSessions.remove(factory);
        if(null != sessionsToRemove) {
          for(HQSession session : sessionsToRemove) {
            sessions.remove(session);
          }
        }
        availableSessionFactory.remove(nodeId);
        tempUnavailableSessionFactory.remove(nodeId);
        factory.removeFailoverListener(this);
        //Start a new thread to close the factory, to prevent dead lock
        //When session failover failoved, the factory cleanup all sessions, it may cause dead lock here
        //ClientSessionFactoryImpl.interruptConnectAndCloseAllSessions <=> ClientSessionFactoryImpl.createSessionInternal
        new Thread(new Runnable(){
          @Override
          public void run() {
            try {
              log.info("Close session factory.");
              //factory.close will send close message to server which will block the execution.
              factory.cleanup();
              ((ServerLocatorInternal) locator).factoryClosed(factory);
              //When all server in the cluster down, close the server locator, otherwise, we can't get the server start notification with the old locator.
              if(availableSessionFactory.size() == 0 && tempUnavailableSessionFactory.size() == 0) {
                locator.close();
                locator = null;
                log.info("Close the locator since the cluster is down, will recreate it when the cluster recover.");
              }
              //Remove the closed session from #sessions.
              Iterator iterator = sessions.iterator();
              while(iterator.hasNext()) {
                HQSession session = (HQSession) iterator.next();
                if(session.isClosed()) {
                  iterator.remove();
                }
              }
              log.info("Current sessions size : " + sessions.size());
            } catch (Exception e) {
              log.error("Exception in closing session factory.", e);
            }
          }
          
        }).start();
        log.info("Remove sessionFactory for node : " + nodeId);
        log.debug("Current sessions size : " + sessions.size());
        //Consumer will be auto recreated in {@link HQPullingSubscriber#run}
      }
    }
  }

  /**
   * When a member down(all nodes in the member down), all consumer will be moved to the live member.
   * Then when the member failover, should reallocate the consumers.
   */
  private synchronized void reallocateConsumer() {
    for(HQSession session : subscribers.keySet()) {
      if(!session.isClosed()) {
        subscribers.get(session).reAttachSession();
      }
    }
    log.info("Reallocated all consumers.");
  }

  // ?????????????????? (Stopitsot) is a russian equvalent for "too many". 
  // We use this number as an abstract "very large number" to workaround HQ bug with consumer rate throttling
  private static final int STOPITSOT = 100500;
}
