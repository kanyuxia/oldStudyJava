package com.kanyuxia.proxydynamic;


/**
 * Created by kanyuxia on 2017/3/16.
 */
public class Test {
    public static void main(String[] args) {
        Subject subject = new RealSubject();
        SubjectInvocationHandle handler = new SubjectInvocationHandle(subject);
        Subject proxy = (Subject) handler.getProxy();
        proxy.method("hello");
    }
}
