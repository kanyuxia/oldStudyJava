package com.kanyuxia.jdbcProxy;

/**
 * 实际被代理类
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    // 注入UserDao
    UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    // Service中saveUser方法：抛出异常表明出现错误，需要Rollback。在这里我们就抛出异常，让代理类处理。
    public boolean saveUser(User user) throws Exception {
        int countNum = userDao.saveUser(user);
        return countNum == 1;
    }
}
