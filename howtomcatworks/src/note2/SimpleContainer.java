package note2;

import note1.HttpServer;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * Created by kanyuxia on 2017/5/3.
 */
public class SimpleContainer implements Container {

    @SuppressWarnings("unchecked")
    public void invoke(HttpRequest request, HttpResponse response) throws IOException, ServletException {
        String servletName = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
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
        Class<Servlet> servletClass = null;
        try {
            servletClass = (Class<Servlet>) classLoader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Servlet servlet = null;
        try {
            servlet = servletClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        servlet.service(request.getRequest(), response.getResponse());
    }
}
