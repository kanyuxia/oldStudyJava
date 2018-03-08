package com.kanyuxia.factorysimple;

/**
 * Created by kanyuxia on 2017/3/13.
 */
public class ProductBFactory extends Factory {
    @Override
    public Product create() {
        return new ProductB();
    }
}
