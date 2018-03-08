package concurrent;

/**
 * Created by kanyuxia on 2017/3/7.
 */
public class CreateThread {
    public static void main(String[] args){
        PrimeThread1 primeThread1 = new PrimeThread1();
        Thread primeThread2 = new Thread(new PrimeThread2());
        primeThread2.start();
        primeThread1.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("I am Thread3");
            }
        }).start();
    }
}

class PrimeThread1 extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("I am PrimeThread1");
    }
}

class PrimeThread2 implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("I am PrimeThread2");
    }
}
