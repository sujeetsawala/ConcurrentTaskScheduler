package models;

public class Task implements Runnable{
    private final long id;

    public Task(final long id) {
        this.id = id;
    }

    @Override
    public void run() {
        if(id == 1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Hello I am task: " + id + " executed by thread: " + Thread.currentThread().getId() + " at " + System.currentTimeMillis() );
    }
}
