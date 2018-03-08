import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kanyuxia on 2017/6/1.
 */
public class Restaurant1 {
    static AtomicInteger id = new AtomicInteger(0);
    final Lock lock = new ReentrantLock();
    final Condition eat = lock.newCondition();
    final Condition make = lock.newCondition();
    Meal meal = null;

    public static void main(String[] args) {
        Restaurant1 restaurant1 = new Restaurant1();
        new Thread(new Consumer1(restaurant1)).start();
        new Thread(new Producer1(restaurant1)).start();
    }
}

class Producer1 implements Runnable {

    private final Restaurant1 restaurant1;

    Producer1(Restaurant1 restaurant1) {
        this.restaurant1 = restaurant1;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            restaurant1.lock.lock();
            try {
                while (restaurant1.meal == null) {
                    try {
                        restaurant1.eat.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Consumer Eat Meal: " + restaurant1.meal);
                restaurant1.meal = null;
                restaurant1.make.signalAll();
            } finally {
                restaurant1.lock.unlock();
            }
        }
    }
}

class Consumer1 implements Runnable {
    private final Restaurant1 restaurant1;

    Consumer1(Restaurant1 restaurant1) {
        this.restaurant1 = restaurant1;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            restaurant1.lock.lock();
            try {
                while (restaurant1.meal != null) {
                    try {
                        restaurant1.make.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                restaurant1.meal = new Meal(Restaurant1.id.incrementAndGet());
                System.out.println("Producer Make Meal: " + restaurant1.meal);
                restaurant1.eat.signalAll();
            } finally {
                restaurant1.lock.unlock();
            }
        }
    }
}

class Meal1 {
    private final int id;

    Meal1(int id) {
        this.id = id;

    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
