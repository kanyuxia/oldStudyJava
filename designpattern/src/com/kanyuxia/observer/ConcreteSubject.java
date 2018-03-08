package com.kanyuxia.observer;

/**
 * Created by kanyuxia on 2017/3/30.
 */
public class ConcreteSubject extends Subject {
    // 定义自己的业务逻辑并通知观察者
    public void operate() {
        System.out.println("I do some operations and notify observes");
        notifyObservers(null);
    }
}
