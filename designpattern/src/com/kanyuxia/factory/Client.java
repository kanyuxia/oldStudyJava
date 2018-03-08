package com.kanyuxia.factory;

/**
 * Created by kanyuxia on 2017/3/13.
 */
public class Client {
    public static void main(String[] args){
        ProductFactory factory = new ProductFactory();
        Product productA = factory.create(ProductA.class);
        productA.method();
        Product productB = factory.create(ProductB.class);
        productB.method();
    }
}
