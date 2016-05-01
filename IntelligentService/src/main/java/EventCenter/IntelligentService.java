package EventCenter;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Xc on 2016/4/13.
 */
public class IntelligentService {
    private static IntelligentService defaultIS;

    ExecutorService threadPool = Executors.newFixedThreadPool(100);
    PriorityQueue<Event> eventQueue = new PriorityQueue<>(20);
    ArrayList<SubscribeEntity> subscribeList = new ArrayList<>();

    public static void main(String[] args){
        IntelligentService is = IntelligentService.getDefault();
        SubscribeEntity se = new SubscribeEntity();
        se.setAction(new Runnable() {
            @Override
            public void run() {
                System.out.print(1);
            }
        });
        is.subscribe(se);

        is.publish(new Event());

    }

    public static IntelligentService getDefault() {
        if (defaultIS == null) {
            synchronized (IntelligentService.class) {
                if (defaultIS == null) {
                    defaultIS = new IntelligentService();
                }
            }
        }
        return defaultIS;
    }

    public void subscribe(SubscribeEntity subscribeEntity) {
        subscribeList.add(subscribeEntity);
    }

    public void publish(Event event) {
        final PriorityQueue<SubscribeEntity> subscribeEntities = filter(event, subscribeList);
        while (!subscribeEntities.isEmpty()) {
            SubscribeEntity se = subscribeEntities.poll();
            if (se != null) {
                threadPool.execute(se.getAction());
            }
        }
    }

    private PriorityQueue<SubscribeEntity> filter(Event event, ArrayList<SubscribeEntity> subscribeEntities) {
        PriorityQueue<SubscribeEntity> priorityQueue = new PriorityQueue<>();
        for (SubscribeEntity subscribeEntity : subscribeEntities) {
            if (subscribeEntity.filter(event)) {
                priorityQueue.offer(subscribeEntity);
            }
        }
        return priorityQueue;
    }

    public void updateSubscribeConfiguration(String eventType,String subscribeId, boolean enabled){

    }
}
