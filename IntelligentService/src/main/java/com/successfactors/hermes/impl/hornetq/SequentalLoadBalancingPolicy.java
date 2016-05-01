/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.successfactors.hermes.impl.hornetq;

import java.io.Serializable;
import org.hornetq.api.core.client.loadbalance.ConnectionLoadBalancingPolicy;
import org.hornetq.utils.Random;

/**
 * Re-defines load balancing behavior Forces client session to try servers in
 * order they listed in servers_list property
 *
 * @author ayarmolenko
 */
public class SequentalLoadBalancingPolicy implements ConnectionLoadBalancingPolicy, Serializable {

  private static final long serialVersionUID = 7511196010141439549L;
  private int pos = 0;
  private static Random randomGenerator = new Random();
  private boolean first = true;

  @Override
  public int select(final int max) {
    if (first) {
      //Workaround 
      // We start on a random one, from the first half of the list
      // we assuming that we always have the same number of backup and primary nodes:
      // 0.. max/2 - primary nodes
      // max/2...max - backup nodes
      
      pos = (max > 1) ? randomGenerator.getRandom().nextInt(max / 2) : 0;
      

      first = false;
    } else {
      pos++;

      if (pos >= max) {
        pos = 0;
      }
    }

    return pos;
  }
}
