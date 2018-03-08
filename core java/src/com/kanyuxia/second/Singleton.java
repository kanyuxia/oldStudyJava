package com.kanyuxia.second;

/**
 * Created by kanyuxia on 2017/7/8.
 */
public class Singleton {

    private Singleton() {}

    public static Singleton getInstance() {
       return SingletonInstance.singleton;
    }

    private static class SingletonInstance {
        private static Singleton singleton = new Singleton();
    }
}
