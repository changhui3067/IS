package EventCenter;

import org.omg.CORBA.Object;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xc on 2016/4/13.
 */
public class Event implements Comparable {


    private String id;
    private String type;
    private String topic;
    private Map<String,Object> params = new HashMap<>();
    private int priority = PRIORITY_NORMAL;

    public final static int PRIORITY_VERY_LOW = 1;
    public final static int PRIORITY_LOW = 2;
    public final static int PRIORITY_NORMAL = 3;
    public final static int PRIORITY_HIGH = 4;
    public final static int PRIORITY_VERY_HIGH = 5;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(java.lang.Object o) {
        Event e2 = (Event) o;
        return this.getPriority() - e2.getPriority();
    }
}
