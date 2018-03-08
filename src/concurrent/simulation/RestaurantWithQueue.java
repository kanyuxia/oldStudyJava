package concurrent.simulation;

/**
 * Created by kanyuxia on 2017/3/24.
 */
public class RestaurantWithQueue {
    public static void main(String[] args) {

    }
}

class Order {
    private static int counter = 0;
    private final int id = counter++;
    private final Consumer consumer;
    private final WaitPerson waitPerson;

    Order(Consumer consumer, WaitPerson waitPerson) {
        this.consumer = consumer;
        this.waitPerson = waitPerson;
    }
}

class Consumer implements Runnable {
    @Override
    public void run() {

    }
}

class WaitPerson implements Runnable {
    @Override
    public void run() {

    }
}

class Chef implements Runnable {
    @Override
    public void run() {

    }
}

class Restaurant implements Runnable {
    @Override
    public void run() {

    }
}