package test;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by kanyuxia on 2017/5/3.
 */
public class ReaderA implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(o + "Write: " + arg);
    }
}
