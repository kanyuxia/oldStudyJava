package com.kanyuxia.template;

import java.util.AbstractList;

/**
 * Created by kanyuxia on 2017/3/30.
 */
public class Client {
    public static void main(String[] args) {
        AbstractClass class1 = new ConcreteClass1();
        AbstractClass class2 = new ConcreteClass2();
        // 调用模板方法
        class1.templateMethod();
        class2.templateMethod();

    }
}
