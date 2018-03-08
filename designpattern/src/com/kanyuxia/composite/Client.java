package com.kanyuxia.composite;

/**
 * Created by kanyuxia on 2017/3/28.
 */
public class Client {
    public static void main(String[] args) {
        Component root = new Composite();
        Component brance = new Composite();
        Component leaf = new Leaf();
        root.add(brance);
        brance.add(leaf);
    }
    // 递归遍历该树
    public static void display(Component root) {
        for(Component component : root.getChildren()) {
            // 树枝节点
            if(component instanceof Composite) {
                display(component);
            }else {
                // 叶子节点
                component.operate();
            }
        }
    }
}
