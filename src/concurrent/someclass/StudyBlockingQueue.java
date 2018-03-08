package concurrent.someclass;

import java.util.concurrent.*;

/**
 * Created by kanyuxia on 2017/3/21.
 */
public class StudyBlockingQueue {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Meal> mealQueue = new LinkedBlockingDeque<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Producer(mealQueue));
        executorService.execute(new Consumer(mealQueue));

        TimeUnit.SECONDS.sleep(3);
        executorService.shutdownNow();
    }
}

class Consumer implements Runnable {
    private BlockingQueue<Meal> mealQueue;

    Consumer(BlockingQueue<Meal> mealQueue) {
        this.mealQueue = mealQueue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                Meal meal = mealQueue.take();
                System.out.println("Consumer take meal: " + meal);
            }
        } catch (InterruptedException e) {
            System.out.println("Consumer interrupted");
        }
    }
}

class Producer implements Runnable {
    private int id = 0;
    private BlockingQueue<Meal> mealQueue;

    Producer(BlockingQueue<Meal> mealQueue) {
        this.mealQueue = mealQueue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                Meal meal = new Meal(id++);
                TimeUnit.MILLISECONDS.sleep(200);
                System.out.println("Producer make meal: " + meal);
                mealQueue.put(meal);
            }
        } catch (InterruptedException e) {
            System.out.println("Producer interrupted");
        }
    }
}

class Meal {
    private final int orderNum;

    Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal: " + orderNum;
    }
}
