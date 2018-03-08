package com.kanyuxia.proxy;

/**
 * Created by kanyuxia on 2017/3/22.
 */
public class RealSubject implements Subject {
    @Override
    public void method() {
        System.out.println("RealSubject.method()");
    }
}
