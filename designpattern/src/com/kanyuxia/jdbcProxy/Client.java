package com.kanyuxia.jdbcProxy;

import java.sql.Connection;

/**
 * Created by kanyuxia on 2017/4/9.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Connection connection = JDBCUtil.getConnection();
        User user = new User("kanyuxia@outlook.com", "123456", 0, 21);

        UserDao userDao = new UserDao(connection);
        // 被代理类
        UserService userService = new UserServiceImpl(userDao);
        // 代理类
        UserService proxy = new UserServiceProxy(userService, connection).getProxy();
        // 执行saveUser方法
        proxy.saveUser(user);
    }
}
