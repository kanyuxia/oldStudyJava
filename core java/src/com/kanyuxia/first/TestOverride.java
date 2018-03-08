package com.kanyuxia.first;

/**
 * Created by kanyuxia on 2017/7/1.
 */
public class TestOverride {

    public static void test() {
        Sup sub = new Sub();
        sub.test();
        sub.testReturn();
    }
}

class Sup {

    Sup testReturn() {
        System.out.println("Sup.testReturn()");
        return this;
    }

    void test() {
        System.out.println("Sup.test()");
    }

}

class Sub extends Sup {

    @Override
    void test() {
        System.out.println("Sub.test()");
    }

    @Override
    Sub testReturn() {
        System.out.println("Sub.testReturn()");
        return this;
    }
}
