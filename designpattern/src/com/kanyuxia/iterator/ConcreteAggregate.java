package com.kanyuxia.iterator;

import java.util.Vector;

/**
 * Created by kanyuxia on 2017/3/26.
 */
public class ConcreteAggregate implements Aggregate {
    private Vector vector = new Vector();

    @Override
    @SuppressWarnings("unchecked")
    public boolean add(Object object) {
        return vector.add(object);
    }

    @Override
    public boolean remove(Object object) {
        return vector.remove(object);
    }

    @Override
    public Iterator iretator() {
        return new ConcreteIterator(vector);
    }
}
