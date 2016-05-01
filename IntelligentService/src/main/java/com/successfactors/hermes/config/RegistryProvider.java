package com.successfactors.hermes.config;

/**
 * Interface for registry to start and end initialization and register
 */
interface RegistryProvider {
  /**
   * Start Initialization of Registry.
   */
  void startInitialization();

  /**
   * End Initialization of Registry.
   */
  void endInitialization();

  /**
   * Register the class.
   * @param clazz to be register
   */
  void register(Class clazz);
}
