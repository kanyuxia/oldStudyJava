package concurrent;

import java.util.concurrent.TimeUnit;

/**
 * Created by kanyuxia on 2017/3/8.
 */
public class Test7 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Daemon());
        thread.setDaemon(true);
        thread.start();
        TimeUnit.SECONDS.sleep(1);
    }
}

class Daemon implements Runnable {
    @Override
    public void run() {
        Thread[] threads = new Thread[5];
        for(int i = 0; i < threads.length; i++){
            threads[i] = new Thread(new DaemonSpawn());
            threads[i].start();
            System.out.println("DaemonSpawn " + i + " started, ");
        }
        for(int i = 0; i < threads.length; i++){
            System.out.println("t[" + i + "].isDaemon() = " + threads[i].isDaemon());
        }
    }
}

class DaemonSpawn implements Runnable {
    @Override
    public void run() {
        while (true) {
            Thread.yield();
        }
    }
}
