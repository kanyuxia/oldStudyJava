package com.kanyuxia.factoryspring;

/**
 * Created by kanyuxia on 2017/3/14.
 */
public class Plane implements Vehicle {

    @Override
    public String run() {
        return "坐飞机";
    }

    @Override
    public String toString() {
        return run();
    }

}
