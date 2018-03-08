package com.kanyuxia.iterator;

import java.util.Vector;

/**
 * Created by kanyuxia on 2017/3/26.
 */
public class ConcreteIterator implements Iterator {
    private Vector vector;
    // 定义当前游标
    private int cursor = 0;
    ConcreteIterator(Vector vector) {
        this.vector = vector;
    }

    @Override
    public Object next() {
        if(hasNext()) {
            return vector.get(cursor++);
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        if(cursor >= vector.size()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean remove() {
        vector.remove(cursor);
        return true;
    }
}
