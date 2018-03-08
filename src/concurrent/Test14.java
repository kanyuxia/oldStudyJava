package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by kanyuxia on 2017/3/9.
 */
public class Test14 {
    public static void main(String[] args) throws InterruptedException {
        List<Timer> timers = new ArrayList<>(50);
        for(int i = 0; i < 50; i++){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("hello");
                }
            }, 1000);
            timers.add(timer);
        }
        TimeUnit.MILLISECONDS.sleep(2000);
        System.exit(0);
    }
}
