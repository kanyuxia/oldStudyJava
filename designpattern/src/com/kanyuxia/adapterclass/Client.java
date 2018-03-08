package com.kanyuxia.adapterclass;

/**
 * Created by kanyuxia on 2017/3/25.
 */
public class Client {
    public static void main(String[] args) {
        Target target = new Adapter();
        target.request();
    }
}
