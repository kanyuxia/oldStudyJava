package com.kanyuxia.decorator;

/**
 * Created by kanyuxia on 2017/3/26.
 */
public abstract class Decorator extends Component {
    private Component component;

    // 通过构造函数传递被装饰者
    Decorator(Component component) {
        this.component = component;
    }

    // 委托被装饰者执行
    @Override
    public void operate() {
        component.operate();
    }
}
