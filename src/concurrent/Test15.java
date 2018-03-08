package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kanyuxia on 2017/3/10.
 */
public class Test15 {
    public static void main(String[] args){
        ExecutorService executorService = Executors.newCachedThreadPool();
        SynchronizeObject object = new SynchronizeObject1();
        SynchronizeObject object1 = new SynchronizeObject2();
        LockObject object2 = new LockObject();

        executorService.execute(new RunnerFunc1(object2));
        executorService.execute(new RunnerFunc2(object2));
        executorService.execute(new RunnerFunc3(object2));

        try {
            TimeUnit.SECONDS.sleep(3);
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class RunnerFunc1 implements Runnable {
    private SynchronizeObject object;

    RunnerFunc1(SynchronizeObject object){
        this.object = object;
    }

    @Override
    public void run() {
        while (true) {
            object.func1();
        }
    }
}

class RunnerFunc2 implements Runnable {
    private SynchronizeObject object;

    RunnerFunc2(SynchronizeObject object){
        this.object = object;
    }

    @Override
    public void run() {
        while (true) {
            object.func2();
        }
    }
}

class RunnerFunc3 implements Runnable {
    private SynchronizeObject object;

    RunnerFunc3(SynchronizeObject object){
        this.object = object;
    }

    @Override
    public void run() {
        while (true) {
            object.func3();
        }
    }
}

class LockObject extends SynchronizeObject {
    private Lock lock = new ReentrantLock();
    @Override
    void func1() {
        lock.lock();
        try {
            System.out.println("func1");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    void func2() {
        lock.lock();
        try {
            System.out.println("func2");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    void func3() {
        lock.lock();
        try {
            System.out.println("func3");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


class SynchronizeObject1 extends SynchronizeObject {

    @Override
    void func1() {
        synchronized (this) {
            System.out.println(Thread.currentThread() + " func1");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    void func2() {
        synchronized (this) {
            System.out.println(Thread.currentThread() + " func2");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    void func3() {
        synchronized (this) {
            System.out.println(Thread.currentThread() + " func3");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class SynchronizeObject2 extends SynchronizeObject {
    private final Object object = new Object();
    private final Object object1 = new Object();
    @Override
    void func1() {
        synchronized (this) {
            System.out.println(Thread.currentThread() + " func11");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    void func2() {
        synchronized (object) {
            System.out.println(Thread.currentThread() + " func22");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    void func3() {
        synchronized (object1) {
            System.out.println(Thread.currentThread() + " func33");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

abstract class SynchronizeObject {
    abstract void func1();
    abstract void func2();
    abstract void func3();
}





