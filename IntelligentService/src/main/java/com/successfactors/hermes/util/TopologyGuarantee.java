package com.successfactors.hermes.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.core.client.HornetQClientLogger;
import org.hornetq.core.client.impl.TopologyMemberImpl;
import org.hornetq.core.remoting.impl.netty.TransportConstants;

/**
 * HornetQ depends on server time to handle the topology change message(refer to {@link org.hornetq.core.client.impl.Toploogy#updateMember}), which have some problem.
 * This class add more check when update topology.
 * @author wkliu
 *
 */
public class TopologyGuarantee {

  /**
   * Whether the TopologyMember status is correct
   * Just valid the live server since the backup server doesn't serve requests
   * @param memberInput
   */
  public static boolean isValid(TopologyMemberImpl memberInput) {
    TransportConfiguration live = memberInput.getLive();
//    TransportConfiguration backup = memberInput.getBackup();
    if(null != live && isActive(live)) {
      return true;
    }
    
    return false;
  }

  public static boolean isActive(TransportConfiguration transportConfiguration) {
    return isActive((String)transportConfiguration.getParams().get(TransportConstants.HOST_PROP_NAME), Integer.parseInt((String) transportConfiguration.getParams().get(TransportConstants.PORT_PROP_NAME)));
  }
  
  public static boolean isActive(String host, int port) {
    Socket s = null;
    try {
      s = new Socket();
      s.setReuseAddress(true);
      SocketAddress sa = new InetSocketAddress(host, port);
      s.connect(sa, 2000);
      return true;
    } catch (IOException e) {
      HornetQClientLogger.LOGGER.debug("Can't connect to [" + host + ":" + port + "] for HornetQ initial connections.");
    } finally {
      if (s != null) {
        try {
          s.close();
        } catch (IOException e) {
        }
      }
    }
    return false;
  }
}
