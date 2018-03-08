package com.kanyuxia.factoryabstract;

/**
 * Created by kanyuxia on 2017/3/2.
 */
public class Fish implements Food{
    @Override
    public void foodName() {
        System.out.println("我是一条小小鱼，游啊游啊游");
    }
}
