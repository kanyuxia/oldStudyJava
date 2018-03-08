package com.kanyuxia.filter;

/**
 * Created by kanyuxia on 2017/4/3.
 */
public class HTMLFilter implements Filter {
    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        request.appendReq("--HTMLFilter");
        filterChain.doFilter(request, response, filterChain);
        response.appendRes("--HTMLFilter");
    }
}
