package models;

import java.util.PriorityQueue;

public class Topic {
    private PriorityQueue<ScheduledTask> scheduledTasks;

    public Topic() {
        this.scheduledTasks = new PriorityQueue<>((a, b) -> (a.getExecutionTime() > b.getExecutionTime()) ? 1: -1);
    }

    public synchronized ScheduledTask getTask() {
        return this.scheduledTasks.poll();
    }

    public synchronized ScheduledTask peekTask() {
        return this.scheduledTasks.peek();
    }

    public synchronized void registerTask(final Runnable task, final long delay) {
        final ScheduledTask scheduledTask = new ScheduledTask(task, delay);
        System.out.println("Task with taskId: " + scheduledTask.getId() + " and " + delay + " added at time: " + System.currentTimeMillis());
        this.scheduledTasks.add(scheduledTask);
    }

    public synchronized int getSize() {
      return this.scheduledTasks.size();
    }
}
