package com.kanyuxia.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanyuxia on 2017/3/28.
 */
public class Composite extends Component {
    // 容器构件
    private List<Component> componentList = new ArrayList<>();

    @Override
    public void add(Component component) throws UnsupportedOperationException {
            componentList.add(component);
    }

    @Override
    public boolean remove(Component component) throws UnsupportedOperationException {
        return componentList.remove(component);
    }

    @Override
    public List<Component> getChildren() throws UnsupportedOperationException {
        return componentList;
    }
}
