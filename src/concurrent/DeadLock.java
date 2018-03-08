package concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by kanyuxia on 2017/3/15.
 */
public class DeadLock {
    public static void main(String[] args) throws InterruptedException {
        Chopstick[] chopsticks = new Chopstick[5];
        for(int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new Chopstick();
        }
        Philosopher[] philosophers = new Philosopher[5];
        for(int i = 0; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher(chopsticks[i],
                    chopsticks[(i + 1) % philosophers.length], i, 0);
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        for(Philosopher philosopher : philosophers) {
            executorService.execute(philosopher);
        }

        TimeUnit.SECONDS.sleep(15);
        executorService.shutdownNow();
    }
}

class Philosopher implements Runnable {
    private Chopstick left;
    private Chopstick right;
    private final int id;
    private final int ponderFactor;
    private Random random = new Random();

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                System.out.println(this + " " + "thinking");
                pause();
                // Philosopher becomes hungry
                System.out.println(this + " " + "grabbing right");
                right.take();
                System.out.println(this + " " + "grabbing left");
                left.take();
                System.out.println(this + " " + "eating");
                pause();
                right.drop();
                left.drop();
            }
        } catch (InterruptedException e) {
            System.out.println(this + " " + "exiting via interrupt");
        }
    }

    Philosopher(Chopstick left, Chopstick right, int id, int ponderFactor) {
        this.left = left;
        this.right = right;
        this.id = id;
        this.ponderFactor = ponderFactor;
    }
    // 思考
    private void pause() throws InterruptedException {
        if(ponderFactor == 0) {
            return;
        }
        TimeUnit.MILLISECONDS.sleep(random.nextInt(ponderFactor * 250));
    }

    @Override
    public String toString() {
        return "Philosopher " + id;
    }
}

class Chopstick {
    private boolean taken = false; // 该筷子是否被拿

    synchronized void take() throws InterruptedException {
        while(taken) {
            wait();
        }
        taken = true;
    }

    synchronized void drop() {
        taken = false;
        notifyAll();
    }
}
