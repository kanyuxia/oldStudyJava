package com.kanyuxia.template;

/**
 * Created by kanyuxia on 2017/3/30.
 */
public abstract class AbstractClass {
    // 基本的方法(子类实现)
    protected abstract void primitiveOperation1();
    protected abstract void primitiveOperation2();

    // 模板方法(调用基本方法完成算法)
    public final void templateMethod(){
        /**
         * 调用基本方法，完成相关逻辑
         */
        primitiveOperation1();
        primitiveOperation2();
    }
}
