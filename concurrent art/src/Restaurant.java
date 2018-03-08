import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kanyuxia on 2017/5/31.
 */
public class Restaurant {
    static AtomicInteger id = new AtomicInteger(0);
    Meal meal = null;
    final Consumer consumer = new Consumer(this);
    final Producer producer = new Producer(this);

    public static void main(String[] args) throws InterruptedException {
        Restaurant restaurant = new Restaurant();
        new Thread(new Consumer(restaurant)).start();
        new Thread(new Producer(restaurant)).start();
    }
}

/**
 * 消费者
 */
class Consumer implements Runnable {
    private final Restaurant restaurant;

    Consumer(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            synchronized (restaurant.consumer) {
                while (restaurant.meal == null) {
                    try {
                        restaurant.consumer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            synchronized (restaurant.producer) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Consumer Eat Meal: " + restaurant.meal);
                restaurant.meal = null;
                restaurant.producer.notifyAll();
            }
        }
    }
}

/**
 * 生产者
 */
class Producer implements Runnable {
    private final Restaurant restaurant;

    Producer(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            synchronized (restaurant.producer) {
                while (restaurant.meal != null) {
                    try {
                        restaurant.producer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            synchronized (restaurant.consumer) {
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                restaurant.meal = new Meal(Restaurant.id.incrementAndGet());
                System.out.println("Producer Make Meal: " + restaurant.meal);
                restaurant.consumer.notifyAll();
            }
        }
    }
}

class Meal {
    private final int id;

    Meal(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}


