package note1;

import javax.servlet.Servlet;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kanyuxia on 2017/4/24.
 * Servlet容器：管理Servlet.
 */
public class ServletContainer {
    /**
     * 存放Servlet
     */
    public static Map<String, Servlet> container = new HashMap<>();

    /**
     * 初始化所有的Servlet
     */
    @SuppressWarnings("unchecked")
    public void init() {
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
        // 获得指定目录下的所有.class文件
        File path = new File(HttpServer.SERVLET_ROOT);
        File[] files = path.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.toString().endsWith(".class");
            }
        });
        // 加载所有的.class文件
        for (File file : files) {
            String servletName = file.getName().substring(0, file.getName().indexOf("."));
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
            ServletContainer.container.put(servletName, servlet);
        }
    }
}
