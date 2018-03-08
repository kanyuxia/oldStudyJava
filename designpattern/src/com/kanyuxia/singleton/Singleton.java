package com.kanyuxia.singleton;

/**
 * Created by kanyuxia on 2017/2/27.
 */
public class Singleton {
    // 静态的实例对象
    private static Singleton instance = new Singleton();

    // 静态方法获得该类的实例对象
    public static Singleton getInstance(){
        return instance;
    }

    // 私有的构造方法，避免其他类创建该类的实例对象。
    private Singleton(){}
}

class OneSingleton {

    // 静态方法获得该类的实例对象
    public static OneSingleton getInstance(){
        return OneSingletonInstance.instance;
    }
    // 内部类初始化实例对象
    private static class OneSingletonInstance{
        static OneSingleton instance = new OneSingleton();
    }

    // 私有的构造方法，避免其他类创建该类的实例对象
    private OneSingleton(){}
}

class AnotherSingleton {
    // 静态的实例对象
    private static AnotherSingleton instance;

    // 静态方法获得该类的实例对象
    public static AnotherSingleton getInstance(){
        if(instance == null){
            instance = new AnotherSingleton();
        }
        return instance;
    }

    // 私有的构造方法，避免其他类创建该类的实例对象
    private AnotherSingleton(){}
}


