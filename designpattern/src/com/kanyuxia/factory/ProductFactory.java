package com.kanyuxia.factory;

/**
 * Created by kanyuxia on 2017/3/13.
 */
public class ProductFactory {
    /**
     * 通过接受参数创建相应的产品
     * 参数可以为 Class、String、Enum
     */
    public <T extends Product> T create(Class<T> c) {
        T t = null;
        try {
            t = (T) Class.forName(c.getName()).newInstance();
        } catch (Exception e){
            // 异常处理
        }
        return t;
    }
}
