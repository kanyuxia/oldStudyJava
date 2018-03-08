package com.kanyuxia.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanyuxia on 2017/4/3.
 */
public class FilterChain implements Filter {
    // Filter容器
    private List<Filter> filters = new ArrayList<>();
    // 索引
    private int index = 0;

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        if(index >= filters.size()) {
            return;
        }
        Filter filter = filters.get(index++);
        filter.doFilter(request, response, filterChain);
    }
}
