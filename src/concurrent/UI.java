package concurrent;

import java.io.IOException;

/**
 * Created by kanyuxia on 2017/3/8.
 */
public class UI {
    public static void main(String[] args) throws IOException {
        new RespnsiveUI();
        int i = System.in.read();
        System.out.println("read from console: " + i);
        System.out.println("Daemon thread completed :" + RespnsiveUI.d);
    }
}

class RespnsiveUI extends Thread {
    static volatile double d = 1;
    RespnsiveUI(){
        setDaemon(true);
        start();
    }
    @Override
    public void run() {
        while (true) {
            d = d + (Math.PI + Math.E) / d;
        }
    }
}
