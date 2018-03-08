package concurrent.someclass;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by kanyuxia on 2017/3/22.
 */
public class StudyPriorityBlockingQueue {
    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<MealPriority> queue = new PriorityBlockingQueue<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new ProducerPriority(queue));
        TimeUnit.SECONDS.sleep(1);
        for(int i = 0; i < 3; i++) {
            executorService.execute(new ConsumerPriority(queue));
        }

        TimeUnit.SECONDS.sleep(2);
        executorService.shutdownNow();
    }
}

class ConsumerPriority implements Runnable {
    private PriorityBlockingQueue<MealPriority> queue;

    ConsumerPriority(PriorityBlockingQueue<MealPriority> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                MealPriority meal = queue.take();
                System.out.println("Consumer take " + meal);
            }
        } catch (InterruptedException e) {

        }
    }
}

class ProducerPriority implements Runnable {
    private final Random random = new Random();
    private PriorityBlockingQueue<MealPriority> queue;

    ProducerPriority(PriorityBlockingQueue<MealPriority> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                MealPriority meal = new MealPriority(random.nextInt(10));
                TimeUnit.MILLISECONDS.sleep(200);
                System.out.println("Producer make " + meal);
                queue.put(meal);
            }
        } catch (InterruptedException e) {
        }
    }
}

class MealPriority implements Comparable<MealPriority> {
    private final int priority;

    MealPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(MealPriority o) {
        MealPriority meal = (MealPriority) o;
        return priority > meal.priority ? -1 : (priority == meal.priority ? 0 : 1);
    }

    @Override
    public String toString() {
        return "Meal Priority: " + priority;
    }
}
