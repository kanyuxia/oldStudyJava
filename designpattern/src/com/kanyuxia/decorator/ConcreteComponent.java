package com.kanyuxia.decorator;

/**
 * Created by kanyuxia on 2017/3/26.
 */
public class ConcreteComponent extends Component {
    @Override
    public void operate() {
        System.out.println("ConcreteComponent.operate()");
    }
}
