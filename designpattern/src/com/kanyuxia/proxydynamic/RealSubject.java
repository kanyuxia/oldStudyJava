package com.kanyuxia.proxydynamic;

/**
 * Created by kanyuxia on 2017/3/16.
 */
public class RealSubject implements Subject {
    @Override
    public void method(String args) {
        System.out.println("method()" + " args: " + args);
    }
}
