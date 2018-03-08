package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by kanyuxia on 2017/3/7.
 */
public class Test5 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> futures = new ArrayList<>(10);
        Future<String> future;
        for(int i = 0; i < 10; i++){
            future = executorService.submit(new CallTest());
            futures.add(future);
        }
        for(Future<String> future1 : futures){
            System.out.println(future1.get());
        }
        executorService.shutdown();
    }
}

class CallTest implements Callable<String> {
    private static int taskId = 0;
    private int id = taskId++;
    @Override
    public String call() throws Exception {
//        TimeUnit.MILLISECONDS.sleep(1000);
        return "currentThread: " + Thread.currentThread() + " result of CallTest task is " + id;
    }
}
