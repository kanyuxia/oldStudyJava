package com.kanyuxia.proxydynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Created by kanyuxia on 2017/3/16.
 */
public class SubjectInvocationHandle implements InvocationHandler {
    private Object target;

    SubjectInvocationHandle(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Object proxy: " + proxy.getClass().getName());
        System.out.println("Method: " + method);
        System.out.println("args: " + Arrays.toString(args));
        return method.invoke(target, args);
    }

    public Object getProxy() {
        ClassLoader loader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(loader, interfaces, this);
    }
}
