package EventCenter;

/**
 * Created by Xc on 2016/4/16.
 */
public abstract class EventMeta {
    private String type;
    private String localizedTypeKey;
    private String descriptionMessageKey;
    private String entity;
    private String topic;

    private int maxEventsGrouped = 50;
    private String[] filterParameters = new String[0];
    private boolean effectiveDated;

    //optional
    private String ruleCode;
    private String featureEnum;
    private boolean bulkEventType;
    private boolean smartSuiteEvent;
    private boolean externallyAllowed;

//    private EntityMetadata[] entityKeys = new EntityMetadata[0];
//    private ParamMetadata[] params = new ParamMetadata[0];
}
