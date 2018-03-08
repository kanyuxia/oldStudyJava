import java.util.concurrent.CountDownLatch;

/**
 * Created by kanyuxia on 2017/5/31.
 */
public class TestCountDownLatch {
    static final int THTEAD_SIZE = 10;
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(THTEAD_SIZE);

        for (int i = 0; i < THTEAD_SIZE; i++) {
            new Thread(new Task(start, end)).start();
        }

        start.countDown();
        end.await();
    }
}

class Task implements Runnable {
    private final CountDownLatch start;
    private final CountDownLatch end;

    public Task(CountDownLatch start, CountDownLatch end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            start.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        end.countDown();
    }
}
