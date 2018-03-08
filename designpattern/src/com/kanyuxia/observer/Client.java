package com.kanyuxia.observer;

import java.util.Observable;

/**
 * Created by kanyuxia on 2017/3/30.
 */
public class Client {
    public static void main(String[] args) {
        // 创建观察者
        Observer observer = new ConcreteObserver();
        Observer observer1 = new ConcreteObserver();

        // 创建被观察者并添加观察者
        ConcreteSubject subject = new ConcreteSubject();
        subject.addObserver(observer);
        subject.addObserver(observer1);

        // 被观察者活动并通知观察者
        subject.operate();


    }
}
