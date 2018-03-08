package com.kanyuxia.chain;

/**
 * Created by kanyuxia on 2017/4/3.
 */
public abstract class Handler {
    // 下一个处理者
    private Handler nextHandler;

    // 每个处理者对请求做出处理
    public final void handle(String str) {
        // 根据条件判断是否由自己处理
        if(true) {
            concreteHandle(str);
        }
        // 不属于自己处理，交由下一个处理者处理
        if(nextHandler != null) {
            nextHandler.handle(str);
        } else {
            return;
        }
    }

    // 设置下一个处理者
    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }

    // 具体的处理
    public abstract void concreteHandle(String str);
}
