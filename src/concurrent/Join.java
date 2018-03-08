package concurrent;

import java.util.concurrent.TimeUnit;

/**
 * Created by kanyuxia on 2017/3/8.
 */
public class Join {
    public static void main(String[] args) throws InterruptedException {
        Sleeper sleeper1 = new Sleeper("sleeper1", 2000);
        Sleeper sleeper2 = new Sleeper("sleeper2", 2000);
        Joiner joiner1 = new Joiner("joiner1", sleeper1);
        Joiner joiner2 = new Joiner("joiner2", sleeper2);
        sleeper1.interrupt();
    }
}

class Sleeper extends Thread {
    private int duration;
    Sleeper(String name, int duration){
        super(name);
        this.duration = duration;
        start();
    }
    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(duration);
        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupted. " +
                    "isInterrupted: " + isInterrupted());
        }
        System.out.println(getName() + " has awakened");
    }
}

class Joiner extends Thread {
    private Sleeper sleeper;
    Joiner(String name, Sleeper sleeper){
        super(name);
        this.sleeper = sleeper;
        start();
    }

    @Override
    public void run() {
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println(getName() + " join completed");
    }
}
