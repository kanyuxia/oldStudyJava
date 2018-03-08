package note1;

/**
 * Created by kanyuxia on 2017/4/24.
 */
public class Test {
    public static void main(String[] args) {
        // 加载Servlet
        ServletContainer servletContainer = new ServletContainer();
        servletContainer.init();
        // 启动HttpServer
        HttpServer httpServer = new HttpServer();
        httpServer.start();
    }
}
