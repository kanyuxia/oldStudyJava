package com.kanyuxia.first;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by kanyuxia on 2017/7/6.
 */
public class TestArrayReflect {
    public void test() {
        Integer[] ints = {1, 2, 3, 4, 5, 6};
        Integer[] newArray = copyOf(ints, 10);
        System.out.println(Arrays.toString(newArray));
    }

    @SuppressWarnings("unchecked")
    public <T> T[] copyOf(T[] array, int newLength) {
        if (array == null) {
            throw new NullPointerException();
        }
        Class c = array.getClass();
        Class componentType = c.getComponentType();
        Object[] newArray = (Object[]) Array.newInstance(componentType, newLength);
        System.arraycopy(array, 0, newArray, 0, Math.min(newLength, array.length));
        return (T[]) newArray;
    }
}
