package com.kanyuxia.adapterclass;

/**
 * Created by kanyuxia on 2017/3/25.
 */
public class Adapter extends Source implements Target {
    @Override
    public void request() {
        super.doSomething();
    }
}
