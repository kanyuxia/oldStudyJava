package concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kanyuxia on 2017/3/10.
 */
public class CtiticalSection {
    public static void main(String[] args){
        testTwoApproaches(new PairManager1(), new PairManager2(), new PairManager3());
    }

    private static void testTwoApproaches(PairManager pman1, PairManager pman2, PairManager pman3){
        ExecutorService executorService = Executors.newCachedThreadPool();

        PairManipulator pm1 = new PairManipulator(pman1);
        PairManipulator pm2 = new PairManipulator(pman2);
        PairManipulator pm3 = new PairManipulator(pman3);
        PairChecker pcheck1 = new PairChecker(pman1);
        PairChecker pcheck2 = new PairChecker(pman2);
        PairChecker pcheck3 = new PairChecker(pman3);

        executorService.execute(pm1);
        executorService.execute(pm2);
        executorService.execute(pm3);
        executorService.execute(pcheck1);
        executorService.execute(pcheck2);
        executorService.execute(pcheck3);

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("pm1: " + pm1 + "\npm2: " + pm2 + "\npm3: " + pm3);
        System.exit(0);
    }
}

class PairChecker implements Runnable {
    private PairManager pm;

    PairChecker(PairManager pm){
        this.pm = pm;
    }

    @Override
    public void run() {
        while (true) {
            pm.checkCounter.incrementAndGet();
            pm.getPair().checkState();
        }
    }
}


class PairManipulator implements Runnable {

    private PairManager pm;

    PairManipulator(PairManager pm) {
        this.pm = pm;
    }

    @Override
    public void run() {
        while (true) {
            pm.increment();
        }
    }

    @Override
    public String toString() {
        return "Pair: " + pm.getPair() + " checkCounter = " + pm.checkCounter.get();
    }
}

// Use lock
// TODO: 2017/3/10 有问题！！！！！！！！！！！不能解决
class PairManager3 extends PairManager {
    private Lock lock = new ReentrantLock();

    @Override
    public void increment() {
        lock.lock();
        try {
            p.incrementX();
            p.incrementY();
            store(getPair());
        } finally {
            lock.unlock();
        }
    }
}

// Use a critical section
class PairManager2 extends PairManager {

    @Override
    public void increment() {
        synchronized (this) {
            p.incrementY();
            p.incrementX();
            store(getPair());
        }
    }
}

// Synchronize the entire method
class PairManager1 extends PairManager {

    @Override
    public synchronized void increment() {
        p.incrementX();
        p.incrementY();
        store(getPair());
    }
}

abstract class PairManager {
    AtomicInteger checkCounter = new AtomicInteger(0);
    protected Pair p = new Pair();
    private List<Pair> storage = Collections.synchronizedList(new ArrayList<>());

    public synchronized Pair getPair() {
        return new Pair(p.getX(), p.getY());
    }

    protected void store(Pair p){
        storage.add(p);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void increment();
}

class Pair {
    private int x;
    private int y;
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Pair(){
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void incrementX(){
        x++;
    }

    public void incrementY(){
        y++;
    }

    @Override
    public String toString() {
        return "x: " + x + ", y: " + y ;
    }

    class PairValuesNotEqualException extends RuntimeException {
        PairValuesNotEqualException(){
            super("Pair values not equal: " + Pair.this);
        }
    }

    public void checkState(){
        if(x != y){
            throw new PairValuesNotEqualException();
        }
    }
}
