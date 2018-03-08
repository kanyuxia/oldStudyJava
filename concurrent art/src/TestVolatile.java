import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kanyuxia on 2017/5/31.
 */
public class TestVolatile {
    AtomicInteger index = new AtomicInteger(0);
    volatile int id = 0;

    public void setId(int id) {
        index.incrementAndGet();
        this.id = id;
    }

    public static void main(String[] args) throws InterruptedException {
        TestVolatile testVolatile = new TestVolatile();
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            new Thread(new Task1(start, end, testVolatile, i)).start();
        }
        start.countDown();
        end.await();
        System.out.println(testVolatile.index);
    }
}

class Task1 implements Runnable {
    private final CountDownLatch start;
    private final CountDownLatch end;
    private TestVolatile testVolatile;
    private final int index;

    public Task1(CountDownLatch start, CountDownLatch end, TestVolatile testVolatile, int index) {
        this.start = start;
        this.end = end;
        this.testVolatile = testVolatile;
        this.index = index;
    }

    @Override
    public void run() {
        try {
            start.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        testVolatile.setId(index);
        end.countDown();
    }
}
