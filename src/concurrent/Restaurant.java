package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by kanyuxia on 2017/3/14.
 */
public class Restaurant {
    Meal meal;
    final WaitPerson waitPerson = new WaitPerson(this);
    final Chef chef = new Chef(this);
    ExecutorService executorService = Executors.newCachedThreadPool();

    Restaurant(){
        executorService.execute(chef);
        executorService.execute(waitPerson);
    }


    public static void main(String[] args){
        new Restaurant();
    }
}

class Chef implements Runnable {
    private final Restaurant restaurant;
    private int count = 0;

    Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized(this) {
                    while(restaurant.meal != null) {
                        System.out.println("Chef wait()");
                        wait();
                    }
                }
                if(++count == 10) {
                    System.out.println("Out of food, closing");
                    restaurant.executorService.shutdownNow();
                }
                System.out.println("Order up!");
                synchronized(restaurant.waitPerson) {
                    restaurant.meal = new Meal(count);
                    restaurant.waitPerson.notifyAll();
                }
//                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("chef interrupted");
        }
    }
}

class WaitPerson implements Runnable {
    private final Restaurant restaurant;

    WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized(this) {
                    while(restaurant.meal == null) {
                        System.out.println("WaitPerson wait()");
                        wait();
                    }
                }
                System.out.println("WaitPerson take meal: " + restaurant.meal);
                synchronized(restaurant.chef) {
                    restaurant.meal = null;
                    restaurant.chef.notifyAll();
                }
            }
        } catch (InterruptedException e){
            System.out.println("waitPerson interrupted");
        }
    }
}

class Meal {
    private final int orderNum;

    Meal(int orderNum){
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal " + orderNum;
    }
}
