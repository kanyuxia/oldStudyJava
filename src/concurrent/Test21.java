package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by kanyuxia on 2017/3/14.
 */
public class Test21 {
    public static void main(String[] args){
        RunnerTest21A runnerTest21A = new RunnerTest21A();
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(runnerTest21A);
        executorService.execute(new RunnerTest21B(runnerTest21A));

        executorService.shutdown();
    }
}

class RunnerTest21A implements Runnable {
    @Override
    public void run() {
        System.out.println("Runner1 going into wait");
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Runner1 exited wait");
    }
}

class RunnerTest21B implements Runnable {
    private final RunnerTest21A runnerTest21A;

    RunnerTest21B(RunnerTest21A runnerTest21A){
        this.runnerTest21A = runnerTest21A;
    }

    @Override
    public void run() {
        System.out.println("Runner2 pausing 3 seconds");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Runner2 calling notifyAll");
        synchronized (runnerTest21A) {
            runnerTest21A.notifyAll();
        }
    }
}