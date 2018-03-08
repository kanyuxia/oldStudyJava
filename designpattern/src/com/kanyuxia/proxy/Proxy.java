package com.kanyuxia.proxy;

/**
 * Created by kanyuxia on 2017/3/22.
 */
public class Proxy implements Subject {
    private Subject realSubject;

    // 通过构造函数传递被代理者
    Proxy(Subject realSubject){
        this.realSubject = realSubject;
    }

    // 真正实现代理，在这里我们可以加入一些行为。
    public void method() {
        System.out.println("Proxy do something");
        realSubject.method();
    }
}
