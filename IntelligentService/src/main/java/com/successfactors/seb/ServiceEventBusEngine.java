package com.successfactors.seb;

import com.successfactors.hermes.core.EventDefinition;
import com.successfactors.hermes.core.event.EventDefRegistry;
import com.successfactors.hermes.core.subscriber.SubscriberProvider;
import com.successfactors.hermes.core.subscriber.SubscriberRegistry;
import com.successfactors.hermes.engine.ProviderFactory;
import com.successfactors.hermes.engine.ProviderFactoryCreator;
import com.successfactors.hermesstore.bean.EventStatusEnum;
import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.hermesstore.core.SEBEventStore;
import com.successfactors.hermesstore.core.SEBPublishSynchronization;
import com.successfactors.hermesstore.util.LogUtils;
import com.successfactors.hermesstore.util.StringUtils;
import com.successfactors.logging.api.Level;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import com.successfactors.serialization.SerializationException;
import com.successfactors.sfutil.jndi.JNDIService;

import javax.naming.NamingException;
import javax.transaction.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Service event bus implementation of the ServiceEventBus interface.
 *
 * @author ddiodati
 *
 */
public class ServiceEventBusEngine implements ServiceEventBus {
  
  private static final Logger logger = LogManager.getLogger(ServiceEventBusEngine.class);

  private ProviderFactory pf;
  private String company, user;

  /**
   * Creates a new instance.
   *
   * @param company The company id .
   * @param userid The user id.
   */
  public ServiceEventBusEngine(String company, String userid) {
    this.company = company;
    this.user = userid;
    pf = ProviderFactoryCreator.getFactoryInstance(ProviderFactoryCreator.FactoryType.server);
  }

  /**
   *
   * {@inheritDoc}.
   */
  public UUID publish(Topic topic, SFEvent event) throws HermesException, SerializationException {
    event.getMeta().setCompanyId(company);
    event.getMeta().setUserId(user);
    event.getMeta().setTopic(topic.toString());
    populatePrefinedAttributes(event);
    if (SEBEventStore.isEnabled(company)) {
      LogUtils.log(logger, Level.INFO, "Event store is enabled for company: {}", company);
      return publishFromTransaction(topic, event);
    } else {
      LogUtils.log(logger, Level.INFO, "Event store is disabled for company: {}", company);
      return publishImmediately(topic, event, false);
    }
  }

  /**
   * Internal call to do a point to point publish.
   *
   * @param event Event to publish.
   * @return Id to track event.
   * @throws HermesException If something goes wrong.
   * @throws SerializationException If event fails to serialize.
   */
  public UUID publish(SFEvent event) throws HermesException, SerializationException {
    event.getMeta().setCompanyId(company);
    event.getMeta().setUserId(user);
    if (SEBEventStore.isEnabled(company)) {
      LogUtils.log(logger, Level.INFO, "Event store is enabled for company: {}", company);
      return publishFromTransaction(null, event);
    } else {
      LogUtils.log(logger, Level.INFO, "Event store is disabled for company: {}", company);
      return publishImmediately(null, event, false);
    }
  }

  /**
   * Republish event with the same UUID so that it is tried again.
   *
   * @param uuid The UUID to use.
   * @param event The event to publish.
   * @throws HermesException If something goes wrong.
   * @throws SerializationException If event fails to serialize.
   */
  public void publish(UUID uuid, SFEvent event) throws HermesException, SerializationException {
    event.getMeta().setCompanyId(company);
    event.getMeta().setUserId(user);
    event.getMeta().setEventId(uuid.toString());
    if (SEBEventStore.isEnabled(company)) {
      publishFromTransaction(null, event);
    } else {
      publishImmediately(null, event, false);
    }
  }

  /**
   * {@inheritDoc}.
   */
  public UUID addSubscriber(Topic topic, String sub) throws HermesException {
    return pf.getSubscriptionManager().addSubscriber(topic, sub);

  }

  /**
   * {@inheritDoc}.
   */
  public UUID addDLQSubscriber(Topic topic, String binding, String subscriberName) throws HermesException {
    return pf.getSubscriptionManager().addDLQSubscriber(topic, binding, subscriberName);
  }

  /**
   * Allows to register dynamic subscribers. Subscriber provider is responsible for resolving subscriber instance by
   * it's name Default implementation does that using SEAM annotations on subscriber classes.
   *
   * @param provider new provider to add
   */
  public void addSubscriberProvider(SubscriberProvider provider) {
    SubscriberRegistry.getInstance().addProvider(provider);

  }

  @Override
  public void removeSubscriber(UUID subscriberID) throws HermesException {
    pf.getSubscriptionManager().removeSubscriber(subscriberID);
  }

  @Override
  public List<EventDefinition> loadDefinitions() throws HermesException {
    return EventDefRegistry.getInstance().getDefinitions();
  }

  private void populatePrefinedAttributes(SFEvent event) throws HermesException {
    EventDefRegistry registry = EventDefRegistry.getInstance();
    String topic = event.getMeta().getTopic();
    if (registry.hasDefinition(topic)) {
      Map<String, String> attributes = registry.getDefinition(topic).getAttributes();
      if (attributes != null) {
        event.getMeta().getFilterParameters().putAll(attributes);
      }
    }
  }
  
  private UUID publishFromTransaction(Topic topic, SFEvent event) throws HermesException, SerializationException {
    checkEvent(topic, event);
    UUID uuid = generateUUID(event);
    event.getMeta().setEventId(uuid.toString());
    Transaction currTxn = null;
    try {
      TransactionManager tm = (TransactionManager)JNDIService.lookup("java:/TransactionManager");
      currTxn = tm.getTransaction();
    } catch (NamingException | SystemException e) {
      String message = "Failed to get transaction: error = {}";
      LogUtils.log(logger, Level.ERROR, message, e);
      throw new HermesException(e);
    }
    if (currTxn == null) {
      publishImmediately(topic, event, true);
    } else {
      String transactionId = currTxn.hashCode() + "";
      Synchronization synchronization = SEBEventStore.getSynchronization(transactionId);
      if (synchronization == null) {
        synchronization = SEBEventStore.createSynchronization(transactionId);
        try {        
          currTxn.registerSynchronization(synchronization);
        } catch (IllegalStateException | RollbackException | SystemException e) {
          String message = "Failed to register synchronization: error = {}";
          LogUtils.log(logger, Level.ERROR, message, e);
          throw new HermesException("Failed to register synchronization", e);
        } 
      }
      ((SEBPublishSynchronization) synchronization).addEvent(event);
    }    
    return uuid;
  }
  
  private void checkEvent(Topic topic, SFEvent event) throws SEBApplicationException {
    if (topic != null && StringUtils.isBlank(topic.getName())) {
      throw new SEBApplicationException("Invalid topic, topic name is null or empty");
    }
    if (event == null || event.getBody() == null || event.getMeta() == null ) {
      throw new SEBApplicationException("Invalid event, meta or body is null");
    }
    if (StringUtils.isBlank(event.getMeta().getCompanyId())) {
      throw new SEBApplicationException("Invalid event, companyId is null");
    }
    if (StringUtils.isBlank(event.getMeta().getUserId())) {
      throw new SEBApplicationException("Invalid event, userId is null");
    }
    String ptpName = event.getMeta().getPtpName();   
    if ((topic == null && StringUtils.isBlank(ptpName) || 
        (topic != null && StringUtils.isNotBlank(ptpName)))) {
      throw new SEBApplicationException("Topic or ptpname is required (Not both at the same time)");
    }
  }
  
  private UUID generateUUID(SFEvent event) {
    String id = event.getMeta().getEventId();
    UUID uuid = null;
    if (StringUtils.isBlank(id)) {
      uuid = UUID.randomUUID();
    } else {
      uuid = UUID.fromString(id);
    }
    return uuid;
  }
  
  private UUID publishImmediately(Topic topic, SFEvent event, boolean saveEvent) throws HermesException, SerializationException {
    UUID uuid = null; 
    if (saveEvent) {
      Exception ex = null;
      HermesException he = null;
      SerializationException se = null;
      RuntimeException re = null;
      boolean isPublished = false;
      try {
        if (topic == null) {
          uuid = pf.getQueueManager().getQueueInstance(event).publishEvent(event);
        } else {
          uuid = pf.getQueueManager().getQueueInstance(topic).publishEvent(event);
        }
        isPublished = true;
      } catch(HermesException e) {
        ex = he = e;
      } catch(SerializationException e) {
        ex = se = e;
      } catch (RuntimeException e) {
        ex = re = e;
      }
      if (!isPublished) {
        String message = "Failed to publish event, will save it to event store: event = {}, error = {}";
        LogUtils.log(logger, Level.ERROR, message, event, ex);      
        saveEvent(event, isPublished, ex.toString());
      }
      if (he != null) {
        throw he;
      } else if(se != null) {
        throw se;
      } else if(re != null) {
        throw re;
      }    
      return uuid;
    } else {
      if (topic == null) {
        uuid = pf.getQueueManager().getQueueInstance(event).publishEvent(event);
      } else {
        uuid = pf.getQueueManager().getQueueInstance(topic).publishEvent(event);
      }
    }
    return uuid;
  }
  
  private static void saveEvent(SFEvent event, boolean isPublished, String publishResultMsg) throws SEBApplicationException {
    Meta meta = event.getMeta();
    SEBEvent sebEvent = null;
    sebEvent = SEBEventStore.convert2SEBEvent(event);
    sebEvent.setProcessResult(publishResultMsg);
    if (isPublished) {
      Date publishedAt = meta.getPublishedAt() > 0 ? new Date(meta.getPublishedAt()) : new Date();
      sebEvent.setPublishedAt(publishedAt);
      sebEvent.setStatus(EventStatusEnum.PUBLISHED);
    } else {
      sebEvent.setPublishedAt(null);
      sebEvent.setStatus(EventStatusEnum.FAILED);
    }
    SEBEventStore.getInstance().saveEvent(sebEvent);
  }
  
}
