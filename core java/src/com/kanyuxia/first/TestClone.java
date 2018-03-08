package com.kanyuxia.first;

import java.util.Arrays;

/**
 * Created by kanyuxia on 2017/7/6.
 */
public class TestClone {

    public void testCloneObject() throws CloneNotSupportedException {
        People people = new People();
        People clonePeople = people.clone();
        System.out.println(clonePeople);
    }

    public void testCloneArray() {
        People[] peoples = {new People(), new People()};
        People[] clonePeoples = peoples.clone();
        System.out.println(peoples);
        System.out.println(clonePeoples);
        System.out.println(Arrays.toString(peoples));
        System.out.println(Arrays.toString(clonePeoples));
    }

    static class People implements Cloneable{
        private String name = "hello";
        private int age = 1;
        private Object object = new Object();
        private int[] array = {1, 2};

        @Override
        public String toString() {
            return "name: " + name + " age: " + age + " object: " + object + " array:" + array;
        }

        @Override
        public People clone() throws CloneNotSupportedException {
            return  (People) super.clone();
        }

    }
}
