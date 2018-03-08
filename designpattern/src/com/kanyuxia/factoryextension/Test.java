package com.kanyuxia.factoryextension;

/**
 * Created by kanyuxia on 2017/3/14.
 */
public class Test {
    public static void main(String[] args){
        Singleton singleton = SingletonFactory.getSingleton();
        System.out.println(singleton);
    }
}
