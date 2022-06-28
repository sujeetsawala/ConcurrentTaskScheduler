package models;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ScheduledTask {
    private final String id;
    private final long delay;
    private final Runnable task;
    private final long executionTime;

    public ScheduledTask(final Runnable task, final long delay) {
        this.id = UUID.randomUUID().toString();
        this.delay = delay;
        this.task = task;
        this.executionTime = System.currentTimeMillis() + delay;
    }
}
