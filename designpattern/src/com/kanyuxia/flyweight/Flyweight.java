package com.kanyuxia.flyweight;

/**
 * Created by kanyuxia on 2017/3/29.
 */
public abstract class Flyweight {
    // 内部状态
    private String intrinsic;
    // 外部状态
    private final String extrinsic;
    // 享元角色必须接受外部状态
    public Flyweight(String extrinsic) {
        this.extrinsic = extrinsic;
    }

    // 定义业务逻辑操作
    public abstract void operate();

    // 内部状态的getter/setter方法
    public void setIntrinsic(String intrinsic) {
        this.intrinsic = intrinsic;
    }

    public String getIntrinsic() {
        return intrinsic;
    }
}
