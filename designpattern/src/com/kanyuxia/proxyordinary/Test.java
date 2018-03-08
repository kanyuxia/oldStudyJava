package com.kanyuxia.proxyordinary;

/**
 * Created by kanyuxia on 2017/3/16.
 */
public class Test {
    public static void main(String[] args) {
        Subject proxy = new Proxy("Tom");
        proxy.method();
    }
}
