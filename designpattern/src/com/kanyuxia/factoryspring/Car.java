package com.kanyuxia.factoryspring;

/**
 * Created by kanyuxia on 2017/3/14.
 */
public class Car implements Vehicle {
    private People people;

    @Override
    public String run() {
        return "开车";
    }

    @Override
    public String toString() {
        return people.sayName() + run();
    }
}
