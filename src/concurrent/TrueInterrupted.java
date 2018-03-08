package concurrent;

import java.util.concurrent.TimeUnit;

/**
 * Created by kanyuxia on 2017/3/11.
 */
public class TrueInterrupted {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runner1());
        thread.start();

        TimeUnit.MILLISECONDS.sleep(2000);
        thread.interrupt();
    }
}

class Runner1 implements Runnable {
    private volatile double d = 0.0;
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                System.out.println("block operation");
                NeedsCleanup n1 = new NeedsCleanup(1);
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("no-block operation");
                    NeedsCleanup n2 = new NeedsCleanup(2);
                    try {
                        for(int i = 1; i < 25000000; i ++) {
                            d = d + (Math.PI + Math.E) / d;
                        }
                        System.out.println("finished no-block operation");
                    } finally {
                        n2.cleanup();
                    }
                } finally {
                    n1.cleanup();
                }
            }
        } catch (InterruptedException e){
            System.out.println("InterruptedException");
        }
    }
}

class NeedsCleanup{
    private final int id;

    NeedsCleanup(int id){
        this.id = id;
        System.out.println("NeedsCleanup: " + id);
    }

    void cleanup(){
        System.out.println("Cleanup" + id);
    }
}


