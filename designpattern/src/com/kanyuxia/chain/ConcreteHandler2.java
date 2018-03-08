package com.kanyuxia.chain;

/**
 * Created by kanyuxia on 2017/4/3.
 */
public class ConcreteHandler2 extends Handler {
    @Override
    public void concreteHandle(String str) {
        System.out.println("ConcreteHandle handle " + str);
    }
}
