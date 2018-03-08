package com.kanyuxia.proxyordinary;

/**
 * Created by kanyuxia on 2017/3/16.
 */
public class Proxy implements Subject {
    // 要代理的实现类
    private Subject subject;

    // 通过构造方法创建被代理者
    public Proxy(String name) {
        try {
            subject = new RealSubject(this, name);
        } catch (Exception e) {
            // 异常处理
        }
    }

    @Override
    public void method() {
        before();
        subject.method();
        after();
    }

    // 预处理
    private void before() {
        System.out.println("before()");
    }

    // 善后处理
    private void after() {
        System.out.println("after()");
    }
}
