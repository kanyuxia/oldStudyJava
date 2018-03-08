package com.kanyuxia.filter;

/**
 * Created by kanyuxia on 2017/4/3.
 */
public class Request {
    private String requestStr;

    public void appendReq(String str) {
        requestStr += str;
    }

    public String getRequestStr() {
        return requestStr;
    }
}
