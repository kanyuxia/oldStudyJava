package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kanyuxia on 2017/3/8.
 */
public class SharedResource {
    public static void main(String[] args){
        test(new EvenGenerator(), 10);
    }
    private static void test(IntGenerator gp, int count){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0; i < count; i++){
            executorService.execute(new EvenChecker(gp, i));
        }
        executorService.shutdown();
    }
}

class EvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;
    @Override
    int next() {
        currentEvenValue++;
        Thread.yield();
        currentEvenValue++;
        return currentEvenValue;
    }
}

class EvenChecker implements Runnable {
    private IntGenerator generator;
    private final int id;

    EvenChecker(IntGenerator generator, int ident) {
        this.generator = generator;
        this.id = ident;
    }

    @Override
    public void run() {
        while (!generator.isCanceled()) {
            int val = generator.next();
            if(val % 2 != 0) {
                System.out.println(val + " not even!");
                generator.cancel();  // Cancels all EvenCheckers
            }
        }
    }
}

abstract class IntGenerator {
    private volatile boolean canceled = false;
    abstract int next();
    // Allow this to be canceled:
    void cancel(){
        canceled = true;
    }
    boolean isCanceled(){
        return canceled;
    }
}
