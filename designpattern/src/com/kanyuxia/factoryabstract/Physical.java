package com.kanyuxia.factoryabstract;

/**
 * Created by kanyuxia on 2017/3/2.
 */
public class Physical implements Book{

    @Override
    public void bookName() {
        System.out.println("物理书");
    }
}
