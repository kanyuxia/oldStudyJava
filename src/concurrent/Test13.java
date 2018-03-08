package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kanyuxia on 2017/3/9.
 */
public class Test13 {
    public static void main(String[] args){
        AtomicityTest atomicityTest = new AtomicityTest();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0; i < 20; i++){
            executorService.execute(atomicityTest);
        }
        while (true) {
            int val = atomicityTest.getValue();
            if(val % 2 != 0){
                System.out.println(val);
                System.exit(0);
            }
        }
    }
}
class AtomicityTest implements Runnable{
    private volatile int i = 0;

    synchronized int getValue() {
        return i;
    }

    private synchronized void evenIncrement(){
        i++;
        i++;
    }

    @Override
    public void run() {
        while (true) {
            evenIncrement();
        }
    }
}
