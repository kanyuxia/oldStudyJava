package com.kanyuxia.observer;

/**
 * Created by kanyuxia on 2017/3/30.
 */
public class ConcreteObserver implements Observer {
    @Override
    public void update(Object arg) {
        System.out.println("I accept arg: " + arg);
    }
}
