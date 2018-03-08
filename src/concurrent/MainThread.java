package concurrent;

/**
 * Created by kanyuxia on 2017/3/7.
 */
public class MainThread {
    public static void main(String[] args){
        for(int i = 0; i < 3; i++){
            new Thread(new LiftOff()).start();
        }
        System.out.println("Waiting for LifiOff");
    }
}
