package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by kanyuxia on 2017/3/12.
 */
public class WaxOMatic {
    public static void main(String[] args) throws InterruptedException {
        Car car = new Car();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new WaxOff(car));
        executorService.execute(new WaxOn(car));
        TimeUnit.SECONDS.sleep(5);
        executorService.shutdownNow();
    }
}

class WaxOff implements Runnable {
    private Car car;
    WaxOff (Car car){
        this.car = car;
    }

    /**
     * 抛光
     */
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                car.waitForWaxing();
                System.out.println("Wax Off");
                TimeUnit.MILLISECONDS.sleep(200);
                car.buffed();
            }
        } catch (InterruptedException e){
            System.out.println("Exiting via interrupt");
        }
        System.out.println("Ending Buff On task");
    }
}
class WaxOn implements Runnable {
    private Car car;
    WaxOn(Car car){
        this.car = car;
    }

    /**
     * 打蜡
     */
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println("Wax On!");
                TimeUnit.MILLISECONDS.sleep(200);
                car.waxed();
                car.waitForBuffing();
            }
        } catch (InterruptedException e){
            System.out.println("Exiting via interrupt");
        }
        System.out.println("Ending Wax On task");
    }
}

class Car {
    /**
     * 能否抛光
     */
    private boolean waxOn = false;

    /**
     * 凃蜡完成
     */
    public synchronized void waxed(){
        waxOn = true; // Ready to buff
        notifyAll();
    }

    /**
     * 抛光完成
     */
    public synchronized void buffed(){
        waxOn = false; // Ready for another coat of wax
        notifyAll();
    }

    /**
     * 等待凃蜡
     * @throws InterruptedException
     */
    public synchronized void waitForWaxing() throws InterruptedException {
        while (waxOn == false) {
            wait();
        }
    }

    /**
     * 等待抛光
     */
    public synchronized void waitForBuffing() throws InterruptedException {
        while (waxOn == true) {
            wait();
        }
    }
}
