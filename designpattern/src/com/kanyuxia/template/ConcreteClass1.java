package com.kanyuxia.template;

/**
 * Created by kanyuxia on 2017/3/30.
 */
public class ConcreteClass1 extends AbstractClass {
    @Override
    protected void primitiveOperation1() {
        System.out.println("ConcreteClass1.primitiveOperation1");
    }

    @Override
    protected void primitiveOperation2() {
        System.out.println("ConcreteClass1.primitiveOperateion2");
    }
}
