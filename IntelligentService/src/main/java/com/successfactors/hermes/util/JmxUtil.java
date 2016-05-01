package com.successfactors.hermes.util;

import static com.successfactors.platform.SpringContextAccessor.getBean;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.assembler.MBeanInfoAssembler;
import org.springframework.jmx.export.naming.ObjectNamingStrategy;
import org.springframework.jmx.support.RegistrationPolicy;

import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;

/**
 * JMX Utility
 * 
 * @author yyang
 *
 */
public class JmxUtil {

  /** LOGGER */
  private static final Logger LOGGER = LogManager.getLogger(JmxUtil.class);

  private static MBeanExporter mbeanExporter;

  private static ObjectNamingStrategy namingStrategy;

  private static boolean initialized;

  synchronized static boolean exporterLocated() {
    if (!initialized) {
      try {
        namingStrategy = getBean("namingStrategy", ObjectNamingStrategy.class);
        mbeanExporter = new MBeanExporter();
        mbeanExporter.setServer(getBean("mbeanServer", MBeanServer.class));
        mbeanExporter.setAssembler(getBean("assembler", MBeanInfoAssembler.class));
        mbeanExporter.setNamingStrategy(namingStrategy);
        mbeanExporter.setRegistrationPolicy(RegistrationPolicy.IGNORE_EXISTING);
        initialized = true;
      } catch (RuntimeException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    return initialized;
  }

  public static void register(Object resource) {
    if (exporterLocated()) {
      mbeanExporter.registerManagedResource(resource);
    }
  }

  public static void register(Object resource, String objectNameStr) {
    if (exporterLocated()) {
      ObjectName objectName = null;
      try {
        objectName = namingStrategy.getObjectName(resource, objectNameStr);
        mbeanExporter.registerManagedResource(resource, objectName);
      } catch (MalformedObjectNameException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
  }

  /** invisible constructor */
  private JmxUtil() {
  }
}
