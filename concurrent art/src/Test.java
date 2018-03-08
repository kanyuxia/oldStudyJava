import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kanyuxia on 2017/5/13.
 */
public class Test {
    static AtomicInteger id = new AtomicInteger(0);
    static int SIZE = 100;
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch isStarted = new CountDownLatch(1);
        CountDownLatch isEnded = new CountDownLatch(SIZE);
        ThreadPool<Task> threadPool = new ThreadPool<>();
        for (int i = 0; i < SIZE; i++) {
            threadPool.execute(new Task(isStarted, isEnded));
        }
        long startTime = System.currentTimeMillis();
        isStarted.countDown();
        isEnded.await();
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
        threadPool.shutDown();
    }

    static class Task implements Runnable {
        private final CountDownLatch isStarted;
        private final CountDownLatch isEnded;

        Task(CountDownLatch isStarted, CountDownLatch isEnded) {
            this.isStarted = isStarted;
            this.isEnded = isEnded;
        }


        @Override
        public void run() {
            try {
                isStarted.await();
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isEnded.countDown();
        }
    }
}







