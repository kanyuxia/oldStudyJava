package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kanyuxia on 2017/3/15.
 */
public class Test27 {
    public static void main(String[] args) {
        new RestaurantTest27();
    }
}

class RestaurantTest27 {
    MealTest27 mealTest27;
    final ChefTest27 chefTest27 = new ChefTest27(this);
    final WaitPersonTest27 waitPersonTest27 = new WaitPersonTest27(this);
    ExecutorService executorService = Executors.newCachedThreadPool();
    RestaurantTest27(){
        executorService.execute(chefTest27);
        executorService.execute(waitPersonTest27);
    }
}

class WaitPersonTest27 implements Runnable {
    private final RestaurantTest27 restaurantTest27;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    WaitPersonTest27(RestaurantTest27 restaurantTest27) {
        this.restaurantTest27 = restaurantTest27;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                lock.lock();
                try {
                    while(restaurantTest27.mealTest27 == null) {
                        condition.await();
                    }
                } finally {
                    lock.unlock();
                }
                System.out.println("WaitPerson take " + restaurantTest27.mealTest27);
                restaurantTest27.chefTest27.lock.lock();
                try {
                    restaurantTest27.mealTest27 = null;
                    restaurantTest27.chefTest27.condition.signalAll();
                } finally {
                    restaurantTest27.chefTest27.lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("WaitPerson interrupted");
        }
    }
}

class ChefTest27 implements Runnable {
    private final RestaurantTest27 restaurantTest27;
    private int count = 0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    ChefTest27(RestaurantTest27 restaurantTest27) {
        this.restaurantTest27 = restaurantTest27;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                lock.lock();
                try {
                    while(restaurantTest27.mealTest27 != null) {
                        condition.await();
                    }
                } finally {
                    lock.unlock();
                }
                System.out.println("Order up");
                if(++count == 10) {
                    System.out.println("out of food. closing");
                    restaurantTest27.executorService.shutdownNow();
                    return;
                }
                restaurantTest27.waitPersonTest27.lock.lock();
                try {
                    restaurantTest27.mealTest27 = new MealTest27(count);
                    restaurantTest27.waitPersonTest27.condition.signalAll();
                } finally {
                    restaurantTest27.waitPersonTest27.lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Chef interrupted");
        }
    }
}

class MealTest27 {
    private final int orderNum;

    MealTest27(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal: " + orderNum;
    }
}