package com.kanyuxia.jdbcProxy;

import java.sql.*;

/**
 * 用户DAO类
 */
public class UserDao {
    private Connection connection;

    // 注入其需要的Connection
    UserDao(Connection connection) {
        this.connection = connection;
    }

    // DAO中saveUser方法：抛出异常表明出现错误，需要Rollback。在这里我们就抛出异常，让Service处理。
    public int saveUser(User user) throws Exception{
        String sql = "INSERT INTO USER(email, password, sex, age) VALUES (?,?,?,?)";
        // 获得PreparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        // 预编译
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setInt(3, user.getSex());
        preparedStatement.setInt(4, user.getAge());
        // 插入数据库
        return preparedStatement.executeUpdate();
    }
}
