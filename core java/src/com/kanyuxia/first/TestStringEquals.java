package com.kanyuxia.first;

/**
 * Created by kanyuxia on 2017/7/1.
 */
public class TestStringEquals {

    public static void test() {
        String s1 = "hello";
        System.out.println(s1 == "hello");
        String s2 = "world";
        System.out.println((s2 + "!") == "world!");
    }
}
