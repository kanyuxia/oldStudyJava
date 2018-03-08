package com.kanyuxia.strategy;

/**
 * Created by kanyuxia on 2017/3/7.
 */
public class CatComparator implements Comparator<Cat>{
    @Override
    public int compare(Cat t1, Cat t2) {
        return t1.getWeight() > t2.getWeight() ? -1 : (t1.getWeight() == t2.getWeight() ? 0 : 1);
    }
}
