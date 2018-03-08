package concurrent.someclass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * Created by kanyuxia on 2017/3/21.
 */
public class DelayQueueClass {
    public static void main(String[] args) {
        Random random = new Random(47);
        ExecutorService executorService = Executors.newCachedThreadPool();
        DelayQueue<DelayTask> queue = new DelayQueue<>();

        for(int i = 0; i < 20; i++) {
            queue.put(new DelayTask(random.nextInt(5000)));
        }
        queue.add(new DelayTask.EndSentinel(5000, executorService));

        executorService.execute(new DelayTaskConsumer(queue));
    }
}

class DelayTaskConsumer implements Runnable {
    private DelayQueue<DelayTask> queue;

    DelayTaskConsumer(DelayQueue<DelayTask> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                queue.take().run();
            }
        } catch (InterruptedException e) {

        }
    }
}

class DelayTask implements Runnable, Delayed {
    private static int counter = 0;
    static List<DelayTask> sequence = new ArrayList<>();
    private final int id = counter++;
    private final int delta;
    private final long trigger;

    DelayTask(int delayInMilliseconds) {
        delta = delayInMilliseconds;
        trigger = System.nanoTime() + NANOSECONDS.convert(delta, MILLISECONDS);
        sequence.add(this);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayTask that = (DelayTask) o;
        return trigger < that.trigger ? -1 : (trigger == that.trigger ? 0 : 1);
    }

    @Override
    public void run() {
        System.out.println(this + " ");
    }

    @Override
    public String toString() {
        return String.format("[%1$-4d]", delta) + " Task" + id;
    }

    public String summary() {
        return "(" + id + ":" + delta + ")";
    }

    public static class EndSentinel extends DelayTask {
        private ExecutorService executorService;

        EndSentinel(int delayInMilliseconds, ExecutorService e) {
            super(delayInMilliseconds);
            executorService = e;
        }

        @Override
        public void run() {
            for(DelayTask pt : sequence) {
                System.out.println(pt.summary() + " ");
            }
            System.out.println(this + " Calling shutdownNow()");
            executorService.shutdownNow();
        }
    }
}

