package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by kanyuxia on 2017/3/7.
 */
public class Test3 {
    public static void main(String[] args){
//        func1();
        func2();
//        func3();
    }

    private static void func1(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0; i < 3; i++){
            executorService.execute(new RunTest3());
        }
        executorService.shutdown();
    }

    private static void func2(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for(int i = 0; i < 3; i++){
            executorService.execute(new RunTest3());
        }
        executorService.shutdown();
    }

    private static void func3(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for(int i = 0; i < 3; i++){
            executorService.execute(new RunTest3());
        }
        executorService.shutdown();
    }
}

class RunTest3 implements Runnable {
    private int index = 3;
    private static int taskId = 0;
    private final int id = taskId++;
    @Override
    public void run() {
        while (index-- > 0){
            System.out.println("id: " + id + " run: " + index);
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.yield();
        }
    }
}
