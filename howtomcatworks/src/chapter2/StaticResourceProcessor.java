package chapter2;

import java.io.IOException;

/**
 * Created by kanyuxia on 2017/4/19.
 * HttpServer静态资源处理类
 */
public class StaticResourceProcessor {
    /**
     * 静态资源处理方法
     * @param request 请求对象
     * @param response 响应对象
     */
    public void process(Request request, Response response) {
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
