package com.kanyuxia.flyweight;

/**
 * Created by kanyuxia on 2017/3/29.
 */
public class ConcreteFlyweight extends Flyweight {
    // 接收外部状态
    ConcreteFlyweight(String extrinsic) {
        super(extrinsic);
    }

    @Override
    public void operate() {
        // 定义自己的业务逻辑
    }
}
