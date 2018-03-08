package com.kanyuxia.jdbcProxy;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * JDBC工具类
 */
public class JDBCUtil {
    /**
     * 返回一个Connection
     */
    public static Connection getConnection() {
        Connection connection = null;
        String driverClass = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/hibernate?useUnicode=true&characterEncoding=utf8";
        String user = "root";
        String password = "";
        try {
            // 加载mysql驱动
            Class.forName(driverClass);
            // 获得Connection
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
