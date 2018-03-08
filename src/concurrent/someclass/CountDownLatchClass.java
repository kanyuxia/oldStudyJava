package concurrent.someclass;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by kanyuxia on 2017/3/21.
 */
public class CountDownLatchClass {
    static final int SIZE = 100;
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // All must share a single CountDownLatch object
        CountDownLatch latch = new CountDownLatch(SIZE);
        for(int i = 0; i < 10; i++) {
            executorService.execute(new WaitingTack(latch));
        }
        for(int i = 0; i < SIZE; i++) {
            executorService.execute(new TaskPortion(latch));
        }
        System.out.println("Launched all taskes");
        executorService.shutdown();
    }
}

class TaskPortion implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private static Random ramdom = new Random(47);
    private final CountDownLatch latch;

    TaskPortion(CountDownLatch latch) {

        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            doWork();
            latch.countDown();
        } catch (InterruptedException e) {
            // Acceptable way to exit
        }
    }

    public void doWork() throws InterruptedException{
        TimeUnit.MILLISECONDS.sleep(TaskPortion.ramdom.nextInt(2000));
        System.out.println(this + "completed");
    }

    @Override
    public String toString() {
        return String.format("%1$-3d", id);
    }
}

class WaitingTack implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final CountDownLatch latch;

    WaitingTack(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            latch.await();
            System.out.println("Latch barrier passed for " + this);
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
    }

    @Override
    public String toString() {
        return String.format("WaitingTask %1$-3d ", id);
    }
}

