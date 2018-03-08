package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kanyuxia on 2017/3/15.
 */
public class Test26 {
    public static void main(String[] args) {
        new RestaurantTest26();
    }
}

class RestaurantTest26 {
    boolean isBused = false;
    MealTest26 mealTest26;
    final ChefTest26 chefTest26 = new ChefTest26(this);
    final WaitPersonTest26 waitPersonTest26 = new WaitPersonTest26(this);
    final BusBoyTest26 busBoyTest26 = new BusBoyTest26(this);
    ExecutorService executorService = Executors.newCachedThreadPool();
    RestaurantTest26(){
        executorService.execute(chefTest26);
        executorService.execute(waitPersonTest26);
        executorService.execute(busBoyTest26);
    }
}

class BusBoyTest26 implements Runnable {
    private final RestaurantTest26 restaurantTest26;

    BusBoyTest26(RestaurantTest26 restaurantTest26) {
        this.restaurantTest26 = restaurantTest26;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                synchronized(this) {
                    while(!restaurantTest26.isBused || restaurantTest26.mealTest26 == null) {
                        wait();
                    }
                }
                System.out.println("BusBoy bus");
                synchronized(restaurantTest26.chefTest26) {
                    restaurantTest26.mealTest26 = null;
                    restaurantTest26.isBused = false;
                    restaurantTest26.chefTest26.notifyAll();
                }
            }
        } catch (InterruptedException e){
            System.out.println("BusBoy interrupted");
        }
    }
}

class WaitPersonTest26 implements Runnable {
    private final RestaurantTest26 restaurantTest26;

    WaitPersonTest26(RestaurantTest26 restaurantTest26) {
        this.restaurantTest26 = restaurantTest26;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                synchronized(this) {
                    while(restaurantTest26.mealTest26 == null || restaurantTest26.isBused) {
                        wait();
                    }
                }
                System.out.println("WaitPerson take " + restaurantTest26.mealTest26);
                synchronized(restaurantTest26.busBoyTest26) {
                    restaurantTest26.isBused = true;
                    restaurantTest26.busBoyTest26.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("WaitPerson interrupted");
        }
    }
}

class ChefTest26 implements Runnable {
    private final RestaurantTest26 restaurantTest26;
    private int count = 0;

    ChefTest26(RestaurantTest26 restaurantTest26) {
        this.restaurantTest26 = restaurantTest26;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                synchronized(this) {
                    while (restaurantTest26.mealTest26 != null || restaurantTest26.isBused) {
                        wait();
                    }
                }
                System.out.println("Order up");
                if(++count == 10) {
                    System.out.println("out of food. closing");
                    restaurantTest26.executorService.shutdownNow();
                    return;
                }
                synchronized(restaurantTest26.waitPersonTest26) {
                    restaurantTest26.mealTest26 = new MealTest26(count);
                    restaurantTest26.isBused = false;
                    restaurantTest26.waitPersonTest26.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Chef interrupted");
        }
    }
}

class MealTest26 {
    private final int orderNum;

    MealTest26(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal: " + orderNum;
    }
}