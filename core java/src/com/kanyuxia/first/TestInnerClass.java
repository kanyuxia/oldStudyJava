package com.kanyuxia.first;

/**
 * Created by kanyuxia on 2017/7/6.
 */
public class TestInnerClass {
    private String name;

    public void test() {
        new Inner().test();
    }

    class Inner {
        public void test() {
            System.out.println(TestInnerClass.this.name);
        }
    }
}
