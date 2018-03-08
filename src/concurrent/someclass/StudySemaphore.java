package concurrent.someclass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static concurrent.someclass.Pool.MAX_AVAILABLE;

/**
 * Created by kanyuxia on 2017/3/22.
 */
public class StudySemaphore {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Pool<Fat> pool = new Pool<>(Fat.class);
        for(int i = 0; i < MAX_AVAILABLE; i++) {
            executorService.execute(new CheckOutTask<>(pool));
        }
        System.out.println("All CheckoutTasks Created");
        List<Fat> list = new ArrayList<>(MAX_AVAILABLE);
        for(int i = 0; i < MAX_AVAILABLE; i++) {
            Fat fat = pool.checkOut();
            System.out.println(i + ": main() thread checked out");
            list.add(fat);
        }
        Future<?> blocked = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    pool.checkOut();
                } catch (InterruptedException e) {
                    System.out.println("CheckOut() Interrupted");
                }
            }
        });
        TimeUnit.SECONDS.sleep(1);
        blocked.cancel(true);
        System.out.println("Checking in objects in " + list);
        for(Fat fat : list) {
            pool.checkIn(fat);
        }
        for(Fat fat : list) {
            pool.checkIn(fat);
        }
        executorService.shutdown();
    }
}

class CheckOutTask<T> implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private Pool<T> pool;

    CheckOutTask(Pool<T> pool){
        this.pool = pool;
    }

    @Override
    public void run() {
        try {
            T item = pool.checkOut();
            System.out.println(this + " Checked Out " + item);
            TimeUnit.SECONDS.sleep(1);
            System.out.println(this + " Checked In " + item);
            pool.checkIn(item);
        } catch (InterruptedException e) {

        }
    }

    @Override
    public String toString() {
        return "CheckOutTask Id: " + id;
    }
}

class Pool<T> {
    public static final int MAX_AVAILABLE = 25;
    private List<T> items = new ArrayList<>(MAX_AVAILABLE);
    private volatile boolean[] used = new boolean[MAX_AVAILABLE];
    private Semaphore semaphore = new Semaphore(MAX_AVAILABLE, true);

    Pool(Class<T> classObject){

        for(int i = 0; i < MAX_AVAILABLE; i++) {
            T t = null;
            try {
                t = classObject.newInstance();
            } catch (Exception e) {
                //
            }
            items.add(t);
        }
    }

    public T checkOut() throws InterruptedException {
        semaphore.acquire();
        return getItem();
    }

    public void checkIn(T item) {
        if(releaseItem(item)) {
            semaphore.release();
        }
    }

    private synchronized T getItem() {
        for(int i = 0; i < MAX_AVAILABLE; i++) {
            if(!used[i]) {
                used[i] = true;
                return items.get(i);
            }
        }
        return null;
    }

    private synchronized boolean releaseItem(T item) {
        int index = items.indexOf(item);
        if(index == -1) {
            return false;
        }
        if(used[index]) {
            used[index] = false;
            return true;
        }
        return false;
    }
}

class Fat {
    private volatile double d;
    private static int counter = 0;
    private final int id = counter++;
    Fat() {
        for(int i = 0; i < 10000; i++) {
            d += (Math.PI + Math.E) / (double) i;
        }
    }

    @Override
    public String toString() {
        return "Fat id: " + id;
    }
}
