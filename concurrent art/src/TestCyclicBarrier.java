import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kanyuxia on 2017/6/2.
 */
public class TestCyclicBarrier {
    static AtomicInteger id = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(101, new Task3());
        for (int i = 0; i < 100; i++) {
            new Thread(new Task2(cyclicBarrier)).start();
        }
        System.out.println("wait 0.5s util task successed");
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println(TestCyclicBarrier.id.get());

        cyclicBarrier.await();
        System.out.println("wait 1s util cyclicBarrier");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(TestCyclicBarrier.id.get());
    }
}

class Task2 implements Runnable {
    private final CyclicBarrier cyclicBarrier;

    Task2(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {

        TestCyclicBarrier.id.incrementAndGet();
        try {
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Task3 implements Runnable {

    @Override
    public void run() {
        System.out.println("Task2 run");
        TestCyclicBarrier.id.incrementAndGet();
    }
}
