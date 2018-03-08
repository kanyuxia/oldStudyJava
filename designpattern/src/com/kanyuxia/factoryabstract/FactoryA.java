package com.kanyuxia.factoryabstract;

/**
 * Created by kanyuxia on 2017/3/2.
 */
public class FactoryA extends AbstracFactory{
    @Override
    public Vehicle createVehicle() {
        return new Car();
    }

    @Override
    public Food createFood() {
        return new Apple();
    }

    @Override
    public Book createBook() {
        return new English();
    }
}
