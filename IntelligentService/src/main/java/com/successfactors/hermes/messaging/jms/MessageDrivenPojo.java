package com.successfactors.hermes.messaging.jms;

import javax.jms.MessageListener;

/**
 * Spring JMS message driven pojo
 * @author yyang
 *
 */
public interface MessageDrivenPojo extends MessageListener {

  /** queue destination */
  public static final String QUEUE = "java.jms.Queue";

  /** currently ONLY Queue destination is supported */
//  public static final String TOPIC = "java.jms.Topic";

  /** destination duribility, ONLY NONDURABLE is supported */
  public static final String NONDURABLE = "NonDurable";

}
