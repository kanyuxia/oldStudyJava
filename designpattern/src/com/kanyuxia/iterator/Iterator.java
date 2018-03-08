package com.kanyuxia.iterator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by kanyuxia on 2017/3/26.
 */
public interface Iterator {
    // 遍历到下一个元素
    Object next();
    // 是否遍历到尾部
    boolean hasNext();
    // 删除当前指向元素
    boolean remove();
}
