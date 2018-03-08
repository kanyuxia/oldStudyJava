package com.kanyuxia.jdbcProxy;

/**
 * Entity类
 */
public class User {
    private long id;

    private String email;

    private String password;

    private int sex;

    private int age;

    public User() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User(String email, String password, int sex, int age) {
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.age = age;
    }
}
