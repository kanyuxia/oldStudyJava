package com.kanyuxia.strategy;

/**
 * Created by kanyuxia on 2017/3/7.
 */
public class Test {
    public static void main(String[] args){
        Cat[] catArray = new Cat[]{new Cat(1),
                new Cat(8), new Cat(9), new Cat(6), new Cat(12), new Cat(15)};
        DefineArrays.sort(catArray, new CatComparator());
        DefineArrays.toStrings(catArray);
    }
}

