package com.kanyuxia.filter;

/**
 * Created by kanyuxia on 2017/4/3.
 */
public class Client {
    public static void main(String[] args) {
        FilterChain filterChain = new FilterChain();
        filterChain.addFilter(new HTMLFilter());
        filterChain.addFilter(new SensitiveFilter());
        Request request = new Request();
        Response response = new Response();

        filterChain.doFilter(request, response, filterChain);

        System.out.println(request.getRequestStr());
        System.out.println(response.getResponseStr());

    }
}
