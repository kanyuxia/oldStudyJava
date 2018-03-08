package com.kanyuxia.iterator;

/**
 * Created by kanyuxia on 2017/3/26.
 */
public interface Aggregate {
    // 元素的添加
    boolean add(Object object);
    // 元素的减少
    boolean remove(Object object);
    // 有迭代器遍历所有元素
    Iterator iretator();
}
