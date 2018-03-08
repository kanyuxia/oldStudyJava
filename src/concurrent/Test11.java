package concurrent;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kanyuxia on 2017/3/8.
 */
public class Test11 {
    public static void main(String[] args){
        ExecutorService executorService = Executors.newCachedThreadPool();
        Shared shared = new Shared();
        for(int i = 0; i < 10; i++){
            executorService.execute(new RunTest11(shared));
        }
        executorService.shutdown();
    }
}

class Shared {
    private int a = 5;
    private volatile double z = 10.5;

    synchronized double doFields(){
        a++;
        Thread.yield();
        z++;
        return a + z;
    }
}

class RunTest11 implements Runnable {
    private Shared shared;

    RunTest11(Shared shared){
        this.shared = shared;
    }

    @Override
    public void run() {
        System.out.println( "current thread: " + Thread.currentThread() + " shared: " + shared.doFields());
    }
}