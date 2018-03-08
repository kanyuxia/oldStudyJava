package com.kanyuxia.decorator;

/**
 * Created by kanyuxia on 2017/3/26.
 */
public class Client {
    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        component.operate();
        System.out.println("-------------------------------------");
        // 使用ConcreteDecoratorA装饰原对象
        Component component1 = new ConcreteDecoratorA(component);
        component1.operate();
        System.out.println("-------------------------------------");
        // 使用ConcreteDecoratorB装饰原对象
        Component component2 = new ConcreteDecoratorB(component);
        component2.operate();
        System.out.println("-------------------------------------");
        // 先使用ConcreteDecoratorA装饰对象，再使用ConcreteDecoratorB装饰对象
        Component component3 = new ConcreteDecoratorB(new ConcreteDecoratorA(component));
        component3.operate();
    }
}
