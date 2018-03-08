package com.kanyuxia.composite;

import java.util.List;

/**
 * Created by kanyuxia on 2017/3/28.
 */
public class Leaf extends Component {
    @Override
    public void add(Component component) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Component component) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Component> getChildren() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
