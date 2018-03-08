package com.kanyuxia.decorator;

/**
 * Created by kanyuxia on 2017/3/26.
 */
public class ConcreteDecoratorA extends Decorator {
    // 定义被被装饰者
    ConcreteDecoratorA(Component component) {
        super(component);
    }

    // 重写父类的operate方法, 以用来定义自己的装饰行为
    @Override
    public void operate() {
        System.out.println("ConcreteDecoratorA do some operate");
        super.operate();
    }
}
