package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by kanyuxia on 2017/3/10.
 */
public class OrnamentalGarden {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i++){
            executorService.execute(new Entrance(i));
        }

        TimeUnit.SECONDS.sleep(3);
        Entrance.cancel();
        TimeUnit.SECONDS.sleep(1);
        executorService.shutdown();
        if(!executorService.awaitTermination(250, TimeUnit.MICROSECONDS)){
            System.out.println("Some task were not terminated!");
        }
        System.out.println("Total: " + Entrance.getTotalCount());
        System.out.println("Sum of Entrances: " + Entrance.sumEntrance());
    }
}

class Entrance implements Runnable {
    private static Count count = new Count();
    private static List<Entrance> entrances = new ArrayList<>(10);
    private int number = 0;
    private final int id;
    private static volatile boolean canceled = false;
    public static void cancel(){
        canceled = true;
    }
    Entrance(int id) {
        this.id = id;
        entrances.add(this);
    }
    @Override
    public void run() {
        while (!canceled) {
            synchronized (this) {
                ++number;
            }
            System.out.println(this + " Total: " + count.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("sleep interrupted");
            }
        }
        System.out.println("Stopping " + this);
    }

    public synchronized int getValue(){
        return number;
    }

    @Override
    public String toString() {
        return "Entrance " + id + ": " + getValue();
    }

    public static int getTotalCount(){
        return count.value();
    }

    public static int sumEntrance(){
        int sum = 0;
        for(Entrance entrance : entrances){
            sum = sum + entrance.getValue();
        }
        return sum;
    }
}

class Count {
    private int count = 0;
    private Random random = new Random();

    public synchronized int increment(){
        int temp = count;
        if(random.nextBoolean()) {
            Thread.yield();
        }
        return (count = ++temp);
    }

    public synchronized int value(){
        return count;
    }
}

