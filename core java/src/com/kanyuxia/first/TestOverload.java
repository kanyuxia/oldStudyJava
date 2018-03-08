package com.kanyuxia.first;

/**
 * Created by kanyuxia on 2017/7/1.
 */
public class TestOverload {

    public void test(int i) {
        System.out.println("test(int i)");
    }

    public void test() {
        System.out.println("test()");
    }

    public void test(long l) {
        System.out.println("test(long l)");
    }

    public void testTwo(int i, float f) {
        System.out.println("testTwo(int i, float f)");
    }

    public void testTwo(int i, double d) {
        System.out.println("testTwo(int i, double d)");
    }

}
