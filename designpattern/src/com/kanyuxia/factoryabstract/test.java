package com.kanyuxia.factoryabstract;

/**
 * Created by kanyuxia on 2017/3/2.
 */
public class test {
    public static void main(String[] args){
        AbstracFactory factoryA = new FactoryA();
        Vehicle vehicle = factoryA.createVehicle();
        Food food = factoryA.createFood();
        Book book = factoryA.createBook();

        AbstracFactory factoryB = new FactoryB();
        Vehicle vehicle1 = factoryB.createVehicle();
        Food food1 = factoryB.createFood();
        Book book1 = factoryB.createBook();

        vehicle.vehicleName();
        vehicle1.vehicleName();
        food.foodName();
        food1.foodName();
        book.bookName();
        book1.bookName();
    }
}
