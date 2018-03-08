package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kanyuxia on 2017/3/9.
 */
public class StudyLock {
    public static void main(String[] args) throws InterruptedException {
//        test(new MutexEventGenerator(), 10);
//        test(new SynchronizeGenerator(), 10);
        test(new CiticalSectionGenerator(), 10);
//        testTryLock();
    }

    private static void test(IntGenerator generator, int count){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0; i < count; i++){
            executorService.execute(new LockEvenChecker(generator, i));
        }
        executorService.shutdown();
    }

    private static void testTryLock() throws InterruptedException {
        AttemptLocking attemptLocking = new AttemptLocking();
        attemptLocking.untimed();
        attemptLocking.timed();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                attemptLocking.getLock().lock();
            }
        });
        thread.setDaemon(true);
        thread.start();

        TimeUnit.SECONDS.sleep(1);
        attemptLocking.untimed();
        attemptLocking.timed();
    }
}

class AttemptLocking {
    private ReentrantLock lock = new ReentrantLock();

    void untimed(){
        boolean flag = lock.tryLock();
        try {
            System.out.println("tryLock(): " + flag);
        } finally {
            if(flag){
                lock.unlock();
            }
        }
    }

    void timed(){
        boolean flag = false;
        try {
            flag = lock.tryLock(1, TimeUnit.SECONDS);
            System.out.println("tryLock(1, TimeUnit.SECONDS): " + flag);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (flag) {
                lock.unlock();
            }
        }
    }

    public ReentrantLock getLock() {
        return lock;
    }
}

class SynchronizeGenerator extends IntGenerator {
    private int currentEventValue = 0;
    @Override
    synchronized int next() {
        currentEventValue++;
        Thread.yield();
        currentEventValue++;
        return currentEventValue;
    }
}

class CiticalSectionGenerator extends IntGenerator {
    private int currentEventValue = 0;
    @Override
    int next() {
        synchronized (this) {
            currentEventValue++;
            Thread.yield();
            currentEventValue++;
            return currentEventValue;
        }
    }
}

class MutexEventGenerator extends IntGenerator {
    private int currentEventValue = 0;
    private Lock lock = new ReentrantLock();
    @Override
    int next() {
        lock.lock();
        try {
            currentEventValue++;
            Thread.yield();
            currentEventValue++;
            return currentEventValue;
        } finally {
            lock.unlock();
        }
    }
}

class LockEvenChecker implements Runnable {
    private final int id;
    private IntGenerator generator;
    LockEvenChecker(IntGenerator generator, int id){
        this.id = id;
        this.generator = generator;
    }
    @Override
    public void run() {
        while (!generator.isCanceled()) {
            int val = generator.next();
            System.out.println("val: " + val);
            if(val % 2 != 0){
                System.out.println("current thread: " + Thread.currentThread() + val + " not even!");
                generator.cancel();
            }
        }
    }
}