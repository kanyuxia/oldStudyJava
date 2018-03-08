package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by kanyuxia on 2017/3/8.
 */
public class CatchException {
    public static void main(String[] args){
        ExecutorService executorService = Executors.newCachedThreadPool(new ExceptionThreadFactory());
        executorService.execute(new ExecutionThread());
        executorService.shutdown();
    }
}

class ExecutionThread implements Runnable {
    @Override
    public void run() {
        System.out.println("run");
        throw new RuntimeException();
    }
}

class ExceptionThreadFactory implements ThreadFactory  {

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Current Thread: " + t + " Exception: " + e);
            }
        });
        return thread;
    }
}
