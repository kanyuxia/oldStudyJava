package concurrent;

/**
 * Created by kanyuxia on 2017/3/7.
 */
public class LiftOff implements Runnable{
    private int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++;

    @Override
    public void run() {
        while (countDown-- > 0){
            System.out.print(status());
            Thread.yield();
        }
    }

    public String status(){
        return "#" + id + "(" + (countDown > 0 ? countDown : "Liftoff!") + "), ";
    }

    LiftOff(){}

    LiftOff(int countDown){
        this.countDown = countDown;
    }
}
