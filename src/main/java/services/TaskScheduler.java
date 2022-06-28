package services;

import com.google.inject.Singleton;
import models.Topic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class TaskScheduler {
    private final int POOL_SIZE = 2;
    private volatile int counter = 0;
    private final ExecutorService[] dispatchPool;
    private final Map<Integer, Topic> dispatcherTopicMap;
    private final Map<Integer, TaskExecutor> dispatcherExecutorMap;

    public TaskScheduler() {
        this.dispatcherExecutorMap = new HashMap<>();
        this.dispatcherTopicMap = new HashMap<>();
        this.dispatchPool = new ExecutorService[POOL_SIZE];
        for (int i = 0; i < POOL_SIZE; i++) {
            this.dispatchPool[i] = Executors.newSingleThreadExecutor();
            final Topic topic = new Topic();
            final TaskExecutor taskExecutor = new TaskExecutor(topic);
            new Thread(taskExecutor).start();
            this.dispatcherExecutorMap.put(i, taskExecutor);
            this.dispatcherTopicMap.put(i, topic);
        }
    }

    public void registerTask(final Runnable task, final long delay) {
        final int dispatcherIndex = (counter % POOL_SIZE);
        counter++;
        System.out.println("Dispatcher Index: " + dispatcherIndex);
        final Topic topic = dispatcherTopicMap.get(dispatcherIndex);
        topic.registerTask(task, delay);
        this.dispatchPool[dispatcherIndex].execute(() -> {
            final TaskExecutor taskExecutor = dispatcherExecutorMap.get(dispatcherIndex);
            taskExecutor.wakeUp();
        });
    }
}
