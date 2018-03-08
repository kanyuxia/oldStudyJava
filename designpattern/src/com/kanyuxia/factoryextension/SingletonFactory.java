package com.kanyuxia.factoryextension;

import java.lang.reflect.Constructor;

/**
 * Created by kanyuxia on 2017/3/14.
 */
public class SingletonFactory {
    private static Singleton singleton;

    static {
        try {
            @SuppressWarnings("unchecked")
            Class<Singleton> c = (Class<Singleton>) Class.forName(Singleton.class.getName());
            Constructor<Singleton> constructor = c.getDeclaredConstructor();
            constructor.setAccessible(true);
            singleton = constructor.newInstance();
        } catch (Exception e){
            // 异常处理
        }
    }

    public static Singleton getSingleton(){
        return singleton;
    }

}
