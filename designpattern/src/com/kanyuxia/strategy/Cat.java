package com.kanyuxia.strategy;

/**
 * Created by kanyuxia on 2017/3/7.
 */
public class Cat implements Comparable<Cat>{
    private int weight;

    public int getWeight() {
        return weight;
    }

    Cat(int weight){
        this.weight = weight;
    }

    @Override
    public int compareTo(Cat cat) {
        return this.weight > cat.getWeight() ? 1 :
                (this.weight == cat.getWeight() ? 0 : -1);
    }

    @Override
    public String toString() {
        return String.valueOf(weight);
    }
}
