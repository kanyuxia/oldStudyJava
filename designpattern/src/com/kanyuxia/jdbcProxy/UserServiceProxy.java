package com.kanyuxia.jdbcProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * 代理类
 */
public class UserServiceProxy implements InvocationHandler {
    // 被代理类
    private UserService userService;

    // 该数据库Connection,因为只有Conenction才能开启事务
    private Connection connection;

    // 注入被代理类及其使用的Connection
    UserServiceProxy(UserService userService, Connection connection) {
        this.userService = userService;
        this.connection = connection;
    }

    @Override
    // 实际代理逻辑
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 如果方法名是savaUser,则需要开启事务
        if(method.getName().equalsIgnoreCase("saveUser")) {
            // 设置Connection不能自动提交
            connection.setAutoCommit(false);
            // 调用真正的saveUser方法：若抛出异常就Rollback, 否则Commit
            try {
                Object object = method.invoke(userService, args);
                connection.commit();
                return object;
            } catch (Exception e) {
                connection.rollback();
                throw new Exception("出现错误, 错误信息是：" + e);
            }
        }
        return method.invoke(userService, args);
    }

    // 实现代理: 通过JDK自带的动态代理方法生成代理对象
    public UserService getProxy() {
        ClassLoader classLoader = userService.getClass().getClassLoader();
        Class<?>[] interfaces = userService.getClass().getInterfaces();
        return (UserService) Proxy.newProxyInstance(classLoader, interfaces, this);
    }
}
