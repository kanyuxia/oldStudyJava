package com.kanyuxia.chain;

/**
 * Created by kanyuxia on 2017/4/3.
 */
public class Client {
    public static void main(String[] args) {
        // 声明所有处理者
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        // 设置处理链顺序
        handler1.setNext(handler2);
        // 处理
        handler1.handle("hello");
    }
}
