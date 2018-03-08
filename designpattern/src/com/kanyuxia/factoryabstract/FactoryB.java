package com.kanyuxia.factoryabstract;

/**
 * Created by kanyuxia on 2017/3/2.
 */
public class FactoryB extends AbstracFactory{
    @Override
    public Vehicle createVehicle() {
        return new Plane();
    }

    @Override
    public Food createFood() {
        return new Fish();
    }

    @Override
    public Book createBook() {
        return new Physical();
    }
}
