package concurrent.someclass;

import java.util.concurrent.*;

/**
 * Created by kanyuxia on 2017/3/22.
 */
public class StudyDelayQueue {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<MealDelayed> queue = new DelayQueue<>();
        ExecutorService executorService = Executors.newCachedThreadPool();

        for(int i = 0; i < 10; i++) {
            executorService.execute(new ProducerDelayed(queue));
        }
        TimeUnit.MILLISECONDS.sleep(200);
        executorService.execute(new ConsumerDelayed(queue));

        TimeUnit.SECONDS.sleep(1);
        executorService.shutdownNow();
    }
}

class ConsumerDelayed implements Runnable {
    private DelayQueue<MealDelayed> queue;

    ConsumerDelayed(DelayQueue<MealDelayed> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                MealDelayed meal = queue.take();
                System.out.println("Consumer take " + meal);
            }
        } catch (InterruptedException e) {

        }
    }
}

class ProducerDelayed implements Runnable {
    private DelayQueue<MealDelayed> queue;

    ProducerDelayed(DelayQueue<MealDelayed> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                MealDelayed meal = new MealDelayed(System.nanoTime());
                TimeUnit.MILLISECONDS.sleep(200);
                System.out.println("Producer make " + meal);
                queue.put(meal);
            }
        } catch (InterruptedException e) {

        }
    }
}

class MealDelayed implements Delayed {
    private final long orderTime;

    MealDelayed(long orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return orderTime - System.nanoTime();
    }

    @Override
    public int compareTo(Delayed o) {
        MealDelayed meal = (MealDelayed) o;
        return meal.orderTime > orderTime ? 1 : (meal.orderTime == orderTime ? 0 : -1);
    }

    @Override
    public String toString() {
        return "Meal: " + orderTime;
    }
}
