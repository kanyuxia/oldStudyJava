package com.kanyuxia.filter;

/**
 * Created by kanyuxia on 2017/4/3.
 */
public interface Filter {
    void doFilter(Request request, Response response, FilterChain filterChain);
}
