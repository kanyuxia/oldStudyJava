package chapter2;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * Created by kanyuxia on 2017/4/19.
 * HttpServer Servelt请求处理类
 */
public class ServletProcessor {
    /**
     * 处理Servlet请求方法
     * @param request 请求对象
     * @param response 响应对象
     */
    public void process(Request request, Response response) {
        String url = request.getUrl();
        String servletName =  url.substring(url.lastIndexOf("/") + 1);
        // 创建URLClassLoader
        URLClassLoader classLoader = null;
        try {
            // 创建URL
            URL[] urls = new URL[1];
            File classPath = new File(HttpServer.SERVLET_ROOT);
            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
            URLStreamHandler streamHandler = null;
            urls[0] = new URL(null, repository, streamHandler);
            classLoader = new URLClassLoader(urls);
        } catch (IOException e) {
            System.out.println();
        }
        // 加载Servlet
        Class servletClass = null;
        try {
            servletClass = classLoader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 创建Servlet对象
        Servlet servlet = null;
        try {
            servlet = (Servlet) servletClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // 执行Servlet的service()方法
        // 使用门面类，安全-->避免ServletRequest向下转型为Request，暴露不必要的接口。
        RequestFacade requestFacade = new RequestFacade(request);
        ResponseFacade responseFacade = new ResponseFacade(response);
        try {
            servlet.service(requestFacade, responseFacade);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
