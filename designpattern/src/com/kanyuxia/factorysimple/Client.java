package com.kanyuxia.factorysimple;

/**
 * Created by kanyuxia on 2017/3/13.
 */
public class Client {
    public static void main(String[] args){
        Factory factoryA = new ProductAFactory();
        Product productA = factoryA.create();
        productA.method();

        Factory factoryB = new ProductBFactory();
        Product productB = factoryB.create();
        productB.method();
    }
}
