package com.successfactors.hermes.config;

/**
 * Abstract decorator class for Subscriber Provider Registry.
 */
class AbstractRegistryDecorator implements RegistryProvider {
  /** registry being decorated */
  private RegistryProvider decoratedRegistry;

  /**
   * Constructor.
   *
   * @param decoratedRegistry will throw IllegalArgumentException if it's null.
   */
  AbstractRegistryDecorator(RegistryProvider decoratedRegistry) {
    if (null == decoratedRegistry) {
      throw new IllegalArgumentException("Decorated registry cannot be null.");
    }
    this.decoratedRegistry = decoratedRegistry;
  }

  /** {@inheritDoc} */
  public void startInitialization() {
    decoratedRegistry.startInitialization();
  }

  /** {@inheritDoc} */
  public void endInitialization() {
    decoratedRegistry.endInitialization();
  }
  
  /** {@inheritDoc} */
  public void register(Class clazz) {
    decoratedRegistry.register(clazz);
  }
}
