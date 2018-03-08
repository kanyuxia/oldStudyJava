package com.kanyuxia.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kanyuxia on 2017/3/29.
 */
public class FlywightFactory {
    // 定义池容器
    private static Map<String, Flyweight> map = new HashMap<>();

    public static Flyweight getFlyweight(String entrinsic) {
        Flyweight flyweight = null;
        if(map.containsKey(entrinsic)) {
            flyweight = map.get(entrinsic);
        }else {
            // 根据外部条件创建享元对象
            flyweight = new ConcreteFlyweight(entrinsic);
            map.put(entrinsic, flyweight);
        }
        return flyweight;
    }
}
