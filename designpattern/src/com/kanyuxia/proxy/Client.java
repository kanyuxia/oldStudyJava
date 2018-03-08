package com.kanyuxia.proxy;

import java.io.InputStream;

/**
 * Created by kanyuxia on 2017/3/22.
 */
public class Client {
    public static void main(String[] args) {
        Subject realSubject = new RealSubject();
        Subject proxy = new Proxy(realSubject);
        proxy.method();
    }
}
