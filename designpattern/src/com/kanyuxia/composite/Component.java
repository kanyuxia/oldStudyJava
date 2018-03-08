package com.kanyuxia.composite;

import java.util.List;

/**
 * Created by kanyuxia on 2017/3/28.
 */
public abstract class Component {
    // 个体与整体都具有的行为
    public void operate(){
        System.out.println("Common operation");
    }
    // 添加一个叶子或者组合
    public abstract void add(Component component) throws UnsupportedOperationException;

    // 移除一个叶子或者组合
    public abstract boolean remove(Component component) throws UnsupportedOperationException;

    // 获得其分支下的所有叶子和组合
    public abstract List<Component> getChildren() throws UnsupportedOperationException;
}
