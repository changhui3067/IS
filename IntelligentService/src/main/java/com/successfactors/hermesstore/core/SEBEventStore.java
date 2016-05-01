package com.successfactors.hermesstore.core;

import com.successfactors.hermes.core.Meta;
import com.successfactors.hermes.core.SEBApplicationException;
import com.successfactors.hermes.core.SFEvent;
import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.hermesstore.service.AddEventCmd;
import com.successfactors.hermesstore.util.LogUtils;
import com.successfactors.logging.api.Level;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.service.ServiceCommandHandlerFactory;

import javax.transaction.Synchronization;
import java.io.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SEB event store to save the events produced by business modules.
 *
 * @author Roman.Li(I322223) Success Factors
 */
public class SEBEventStore {

    // Event status
    public static final String EVENT_STATUS_INITIAL = "Initial";
    public static final String EVENT_STATUS_IN_PROGRESS = "In Progress";
    public static final String EVENT_STATUS_PUBLISHED = "Published";
    public static final String EVENT_STATUS_FAILED = "Failed";
    public static final String GLOBAL_SYS_CONFIG_KEY = "hermes.event.store.company.list.key";
    public static final String GLOBAL_SYS_CONFIG_TYPE = "hermes.event.store.company.list.type";
    private static final Logger logger = LogManager.getLogger(SEBEventStore.class);
    // Event store related configurations as server startup arguments
    private static final String KEY_SEB_EVENT_STORE_ENABLED = "com.successfactors.hermes.eventStore.enabled";
    //private static final String KEY_SEB_EVENT_STORE_COMPANY_LIST = "com.successfactors.hermes.eventStore.company.list";
    private static final String KEY_SEB_EVENT_STORE_FETCH_SIZE = "com.successfactors.hermes.eventStore.fetchSize";
    //private static final String KEY_SEB_EVENT_STORE_DAYS_TO_KEEP = "com.successfactors.hermes.eventStore.daysToKeep";

    // Default values of event store related configurations
    private static final int DEFAULT_POOL_SIZE = 30;
    private static final String DEFAULT_ENABLED = "false";
    private static final String DEFAULT_FETCH_SIZE = "500";
    private static final String DEFAULT_DAYS_TO_KEEP = "90";
    private static final SEBEventStore instance = new SEBEventStore();
    private static final Map<String, Synchronization> synchronizationMap = new ConcurrentHashMap<String, Synchronization>();
    private static final ExecutorService pool = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);
    private static boolean isEnabled;
    private static int fetchSize;
    private static int daysToKeep;
    private static Set<String> enabledCompanySet = new HashSet<String>();

    // Initialization of event store related configurations
    static {
        LogUtils.log(logger, Level.INFO, KEY_SEB_EVENT_STORE_ENABLED + ": {}", System.getProperty(KEY_SEB_EVENT_STORE_ENABLED));
        //LogUtils.log(logger, Level.INFO, KEY_SEB_EVENT_STORE_COMPANY_LIST + ": {}", System.getProperty(KEY_SEB_EVENT_STORE_COMPANY_LIST));
        //LogUtils.log(logger, Level.INFO, KEY_SEB_EVENT_STORE_FETCH_SIZE + ": {}", System.getProperty(KEY_SEB_EVENT_STORE_FETCH_SIZE));
        //LogUtils.log(logger, Level.INFO, KEY_SEB_EVENT_STORE_DAYS_TO_KEEP + ": {}", System.getProperty(KEY_SEB_EVENT_STORE_DAYS_TO_KEEP));

        isEnabled = Boolean.parseBoolean(System.getProperty(KEY_SEB_EVENT_STORE_ENABLED, DEFAULT_ENABLED));
        fetchSize = Integer.parseInt(System.getProperty(KEY_SEB_EVENT_STORE_FETCH_SIZE, DEFAULT_FETCH_SIZE));
        //daysToKeep = Integer.parseInt(System.getProperty(KEY_SEB_EVENT_STORE_DAYS_TO_KEEP, DEFAULT_DAYS_TO_KEEP));

    /*
    String companies = System.getProperty(KEY_SEB_EVENT_STORE_COMPANY_LIST, EMPTY_STRING);
    if (StringUtils.isNotBlank(companies)) {
      StringTokenizer st = new StringTokenizer(companies, COMMA);
      while (st.hasMoreTokens()) {
        enabledCompanySet.add(st.nextToken());
      }
    }
    */
    }

    private SEBEventStore() {

    }

    public static SEBEventStore getInstance() {
        return instance;
    }

    public static boolean isEnabled() {
        return isEnabled;
    }

    public static boolean isEnabled(String company) {
        return true;
    }

    public static int getFetchSize() {
        return fetchSize;
    }

    public static int getDaysToKeep() {
        return daysToKeep;
    }

    public static ExecutorService getPool() {
        return pool;
    }

    public static Synchronization getSynchronization(String transactionId) {
        return synchronizationMap.get(transactionId);
    }

    public static Synchronization removeSynchronization(String transactionId) {
        return synchronizationMap.remove(transactionId);
    }

    public static synchronized Synchronization createSynchronization(String transactionId) {
        Synchronization synchronization = synchronizationMap.get(transactionId);
        if (synchronization == null) {
            synchronization = new SEBPublishSynchronization(transactionId);
            synchronizationMap.put(transactionId, synchronization);
        }
        return synchronization;
    }

    /**
     * Converts an SFEvent object to an SEBEvent object.
     *
     * @param sfEvent The SFEvent object to convert.
     * @return An SEBEvent object.
     * @throws SEBApplicationException If failed to do the conversion.
     */
    public static SEBEvent convert2SEBEvent(SFEvent sfEvent) throws SEBApplicationException {
        Date now = new Date();
        SEBEvent sebEvent = new SEBEvent();
        Meta meta = sfEvent.getMeta();
        sebEvent.setEventId(meta.getEventId());
        sebEvent.setCompanyId(meta.getCompanyId());
        sebEvent.setUserId(meta.getPublishedBy());
        sebEvent.setEventType(meta.getType());
        sebEvent.setTopic(meta.getTopic());
        sebEvent.setPublishedAt(meta.getPublishedAt() > 0 ? new Date(meta.getPublishedAt()) : null);
        sebEvent.setCreatedBy(meta.getPublishedBy());
        sebEvent.setLastUpdatedBy(meta.getPublishedBy());
        sebEvent.setCreatedDate(now);
        sebEvent.setLastUpdatedDate(now);
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(sfEvent);
            sebEvent.setEvent(baos.toByteArray());
        } catch (IOException e) {
            String message = "Failed to serialize SFEvent object, error = {}";
            LogUtils.log(logger, Level.ERROR, message, e);
            throw new SEBApplicationException("Failed to serialize SFEvent object", e);
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                String message = "Could not close SFEvent ObjectOutputStream: error = {}";
                LogUtils.log(logger, Level.WARN, message, e);
            }
        }
        return sebEvent;
    }

    /**
     * Extracts related SFEvent object from an SEBEvent object.
     *
     * @param sebEvent The SEBEvent object to extract from.
     * @return The SFEvent object extracted.
     * @throws SEBApplicationException If failed to do the extraction.
     */
    public static SFEvent extractSFEvent(SEBEvent sebEvent) throws SEBApplicationException {
        SFEvent sfEvent = new SFEvent();
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(sebEvent.getEvent()));
            sfEvent = (SFEvent) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            String message = "Failed to deserialize SFEvent object, error = {}";
            LogUtils.log(logger, Level.ERROR, message, e);
            throw new SEBApplicationException("Failed to deserialize SFEvent object", e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    String message = "Could not close SFEvent ObjectInputStream: error = {}";
                    LogUtils.log(logger, Level.WARN, message, e);
                }
            }
        }
        return sfEvent;
    }

    /**
     * Saves an SEBEvent to event store.
     *
     * @param sebEvent The SEBEvent to save.
     * @throws SEBApplicationException If failed to save the event.
     */
    public static void saveEvent(SEBEvent sebEvent) throws SEBApplicationException {
        try {
            ServiceCommandHandlerFactory.getSCAHandler().execute(new AddEventCmd(sebEvent));
            String message = "Successfully saved event: event = {}";
            LogUtils.log(logger, Level.INFO, message, sebEvent);
        } catch (ServiceApplicationException e) {
            String message = "Failed to save event: event = {}, error = {}";
            LogUtils.log(logger, Level.ERROR, message, sebEvent, e);
            throw new SEBApplicationException("Failed to save SEBEvent", e);
        }
    }

//  private static Set<String> getEnabledCompanySet() {
//    Set<String> enableCompanySet = new HashSet<String>();
//    try {
//      GlobalSysConfigBean sysBean = ServiceCommandHandlerFactory.getSCAHandler()
//        .execute(new GetGlobalSysConfigByKeyAndTypeWithoutCompany(GLOBAL_SYS_CONFIG_KEY, GLOBAL_SYS_CONFIG_TYPE));
//      if (sysBean != null) {
//        String companies = sysBean.getSysString();
//        if (StringUtils.isNotBlank(companies)) {
//          StringTokenizer st = new StringTokenizer(companies, COMMA);
//          while (st.hasMoreTokens()) {
//            enableCompanySet.add(st.nextToken());
//          }
//        }
//        LogUtils.log(logger, Level.INFO, "Global sys config for SEB event store: companyIDs = {}", companies);
//      } else {
//        LogUtils.log(logger, Level.INFO, "T here is not company configured for event store feature");
//      }
//    } catch (ServiceApplicationException e) {
//      String message = "Failed to get enabled company list: error = {}";
//      LogUtils.log(logger, Level.ERROR, message, e);
//    }
//    return enableCompanySet;
//  }

}
