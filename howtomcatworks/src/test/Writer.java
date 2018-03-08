package test;

import java.util.Observable;

/**
 * Created by kanyuxia on 2017/5/3.
 */
public class Writer extends Observable {
    private String title;

    Writer(String title) {
        this.title = title;
    }

    public void write() {
        setChanged();
        notifyObservers(title);
    }

    @Override
    public String toString() {
        return "柴静";
    }
}
