package container;

import java.util.LinkedList;

/**
 * 自定义Stack
 * Created by kanyuxia on 2017/1/14.
 */
public class DefineStack<T> {
    private LinkedList<T> linkedList = new LinkedList<T>();
    // 压入栈
    public void push(T e){
        linkedList.addFirst(e);
    }

    // 弹出栈
    public T peek(){
        return linkedList.getFirst();
    }

    // 返回栈顶元素
    public T pop(){
        return linkedList.removeFirst();
    }

    // 是否为空
    public boolean isEmpty(){
        return linkedList.isEmpty();
    }

    @Override
    public String toString() {
        return linkedList.toString();
    }
}
