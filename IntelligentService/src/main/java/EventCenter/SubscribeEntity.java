package EventCenter;

/**
 * Created by Xc on 2016/4/14.
 */
public class SubscribeEntity implements Comparable {

    private Runnable action;
    private int priority = PRIORITY_NORMAL;
    private Event event;

    public final static int PRIORITY_VERY_LOW = 1;
    public final static int PRIORITY_LOW = 2;
    public final static int PRIORITY_NORMAL = 3;
    public final static int PRIORITY_HIGH = 4;
    public final static int PRIORITY_VERY_HIGH = 5;
    public void run(){

    }

    boolean filter(Event event){
        return true;
    }

    @Override
    public int compareTo(Object o) {
        SubscribeEntity s2 = (SubscribeEntity) o;
        return this.getPriority() - s2.getPriority();
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Runnable getAction() {
        return action;
    }

    public void setAction(Runnable action) {
        this.action = action;
    }
}
