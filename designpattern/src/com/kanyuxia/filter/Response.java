package com.kanyuxia.filter;

/**
 * Created by kanyuxia on 2017/4/3.
 */
public class Response {
    private String responseStr;

    public void appendRes(String str) {
        responseStr += str;
    }

    public String getResponseStr() {
        return responseStr;
    }
}
