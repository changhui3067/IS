package com.successfactors.hermes.engine;


import com.successfactors.hermes.impl.hornetq.HQProviderFactoryImpl;
/**
 * Provides underlining implementation of the factory to return the specific factory type.
 * @author ddiodati
 *
 */
public class ProviderFactoryCreator {
  
  private ProviderFactoryCreator() {}
  
  /**
   * Location where factory is being used.
   * @author ddiodati
   *
   */
  public static enum FactoryType { server, client};
  
  private static final ProviderFactory FACTORY_SERVER = new HQProviderFactoryImpl(FactoryType.server);
  private static final ProviderFactory FACTORY_CLIENT = new HQProviderFactoryImpl(FactoryType.client);
  
  /**
   * Gets factory instance.
   * @param type The factory type to get.
   * @return A instance of the provider factory.
   */
  public static final ProviderFactory getFactoryInstance(FactoryType type) {
    if (type == FactoryType.server) {
      return FACTORY_SERVER;
    } else {
      return FACTORY_CLIENT;
    }
  }
  
}
