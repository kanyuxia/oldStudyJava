package com.kanyuxia.observer;

import java.util.Vector;

/**
 * Created by kanyuxia on 2017/3/30.
 */
public abstract class Subject {
    // 定义一个观察者数组(Vector线程安全)
    private Vector<Observer> vector = new Vector<>();

    // 添加观察者
    public synchronized void addObserver(Observer observer) {
        vector.addElement(observer);
    }

    // 删除一个观察者
    public synchronized void removeObserver(Observer observer) {
        vector.removeElement(observer);
    }

    // 清空观察者
    public synchronized void clear(Observer observer) {
        vector.removeAllElements();
    }

    // 通知观察者
    public synchronized void notifyObservers(Object arg) {
        for(Observer observer : vector) {
            observer.update(arg);
        }
    }
}
