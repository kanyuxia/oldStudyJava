package com.kanyuxia.proxyordinary;

/**
 * Created by kanyuxia on 2017/3/16.
 */
public class RealSubject implements Subject {
    private String name;
    public RealSubject(Subject subject, String name) throws Exception {
        if(subject == null) {
            throw new Exception("不能创建真实角色");
        }
        this.name = name;
    }

    @Override
    public void method() {
        System.out.println(name + " method()");
    }
}
