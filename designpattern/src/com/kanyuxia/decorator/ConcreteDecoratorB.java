package com.kanyuxia.decorator;

/**
 * Created by kanyuxia on 2017/3/26.
 */
public class ConcreteDecoratorB extends Decorator {
    // 定义被装饰者
    ConcreteDecoratorB(Component component) {
        super(component);
    }
    // 重写父类的operate方法，以用来定义自己的装饰行为
    @Override
    public void operate() {
        System.out.println("ConcreteDecoratorB do some operate");
        super.operate();
    }
}
