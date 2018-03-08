package concurrent;

/**
 * Created by kanyuxia on 2017/3/7.
 */
public class Test1 {
    public static void main(String[] args){
        for(int i = 0; i < 3; i++) {
            new Thread(new Run()).start();
        }
    }
}

class Run implements Runnable {
    private static int taskId = 0;
    private final int id = taskId++;
    private int i = 3;
    Run(){
        System.out.println("Run Constructor");
    }
    @Override
    public void run() {
        while (i-- > 0){
            System.out.println("id: " + id + " run: " + i);
            Thread.yield();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Run Destory");
        super.finalize();
    }
}