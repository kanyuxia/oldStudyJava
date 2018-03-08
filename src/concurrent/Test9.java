package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by kanyuxia on 2017/3/8.
 */
public class Test9 {
    public static void main(String[] args){
        ExecutorService executorService1 = Executors.newCachedThreadPool(new LowPriorityThreadFactory());
        ExecutorService executorService2 = Executors.newCachedThreadPool(new HighPriorityThreadFactory());
        for(int i = 0; i < 3; i++){
            executorService1.execute(new RunTest9());
        }
        for(int i = 0; i < 3; i++){
            executorService2.execute(new RunTest9());
        }
        executorService1.shutdown();
        executorService2.shutdown();
    }
}

class RunTest9 implements Runnable {
    private static int taskId = 0;
    private int id = taskId++;
    private int index = 3;
    @Override
    public void run() {
        while (index-- > 0) {
            System.out.println(Thread.currentThread() + " index: " + index);
        }
    }
}

class HighPriorityThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setPriority(Thread.MAX_PRIORITY);
        return thread;
    }
}

class LowPriorityThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setPriority(Thread.MIN_PRIORITY);
        return thread;
    }
}