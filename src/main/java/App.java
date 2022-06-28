import com.google.inject.Guice;
import com.google.inject.Injector;
import models.Task;
import services.TaskScheduler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) {
        final Injector injector = Guice.createInjector(new AppModule());

        final Task task1 = new Task(1);
        final Task task2 = new Task(2);
        final Task task3 = new Task(3);
        final Task task4 = new Task(4);
        final Task task5 = new Task(5);
        final Task task6 = new Task(6);

        final TaskScheduler taskScheduler = injector.getInstance(TaskScheduler.class);
        CompletableFuture.runAsync(() -> taskScheduler.registerTask(task1, TimeUnit.MILLISECONDS.toMillis(-5)));
        CompletableFuture.runAsync(() -> taskScheduler.registerTask(task2, TimeUnit.MILLISECONDS.toMillis(5)));
        CompletableFuture.runAsync(() -> taskScheduler.registerTask(task3, TimeUnit.MILLISECONDS.toMillis(5)));
        CompletableFuture.runAsync(() -> taskScheduler.registerTask(task4, TimeUnit.MILLISECONDS.toMillis(5)));
        CompletableFuture.runAsync(() -> taskScheduler.registerTask(task5, TimeUnit.MILLISECONDS.toMillis(5)));
        CompletableFuture.runAsync(() -> taskScheduler.registerTask(task6, TimeUnit.MILLISECONDS.toMillis(5)));

    }
}
