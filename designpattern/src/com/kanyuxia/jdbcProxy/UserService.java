package com.kanyuxia.jdbcProxy;

/**
 * 被代理接口：用户业务类
 */
public interface UserService {
    boolean saveUser(User user) throws Exception;
}
