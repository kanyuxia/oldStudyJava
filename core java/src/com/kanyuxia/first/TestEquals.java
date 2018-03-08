package com.kanyuxia.first;

import java.util.Objects;

/**
 * Created by kanyuxia on 2017/7/6.
 */
public class TestEquals {
    private String name;
    private int age;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof TestEquals) {
            TestEquals that = (TestEquals) obj;
            return Objects.equals(name, that.name) && Objects.equals(age, that.age);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(age);
        return result;
    }
}
