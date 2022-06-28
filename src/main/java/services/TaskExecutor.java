package services;

import models.ScheduledTask;
import models.Topic;

public class TaskExecutor implements Runnable {

    private final Topic topic;

    public TaskExecutor(final Topic topic) {
       this.topic = topic;
    }

    @Override
    public void run() {
        synchronized (this) {
            while(true) {
                try {
                    while (this.topic.getSize() == 0 || this.topic.peekTask().getExecutionTime() - System.currentTimeMillis() > 0) {
                        if(this.topic.getSize() == 0) {
                            System.out.println("Thread: " + Thread.currentThread().getId() + "with no next wakeUp");
                            this.wait();
                        }
                        else {
                            long nextWakeUp = this.topic.peekTask().getExecutionTime() - System.currentTimeMillis();
                            System.out.println("Thread: " + Thread.currentThread().getId() + " next wakeUp at: " + nextWakeUp);
                            this.wait(nextWakeUp);
                        }
                    }
                    final ScheduledTask scheduledTask = this.topic.getTask();
                    scheduledTask.getTask().run();
                    System.out.println("Task with taskId: " + scheduledTask.getId() + " and delay: " + scheduledTask.getDelay() + " scheduled with thread: " + Thread.currentThread().getId() + " is executed at " + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean wakeUp() {
        synchronized (this) {
            try {
                this.notify();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
