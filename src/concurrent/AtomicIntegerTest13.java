package concurrent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kanyuxia on 2017/3/9.
 */
public class AtomicIntegerTest13 {
    public static void main(String[] args){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.err.println("Aborting");
                System.exit(0);
            }
        }, 1000);
        AtomicIntegerRunner atomicInteger = new AtomicIntegerRunner();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0; i < 20; i++){
            executorService.execute(atomicInteger);
        }
        while (true) {
            int val = atomicInteger.getAtomicInteger();
            if(val % 2 != 0){
                System.out.println(val);
                System.exit(0);
            }
        }
    }
}

class AtomicIntegerRunner implements Runnable {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    int getAtomicInteger() {
        return atomicInteger.get();
    }

    private void evenIncrement(){
        atomicInteger.addAndGet(2);
    }

    @Override
    public void run() {
        while (true) {
            evenIncrement();
        }
    }
}
