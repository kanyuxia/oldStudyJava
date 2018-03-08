package note1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kanyuxia on 2017/4/24.
 * HttpServer是模拟HTTP服务器：接受HTTP请求，响应静态资源或者Servlet资源
 */
public class HttpServer {
    /**
     * HttpServer端口号
     */
    public static final int PORT = 10086;
    /**
     * Http静态文件根目录
     */
    public static final String STATIC_RESOURCE_ROOT = "E:/java/HttpServer/staticresource";
    /**
     * Http中Servlet文件根目录
     */
    public static final String SERVLET_ROOT = "E:/java/HttpServer/servlet";
    /**
     * 线程池
     */
    private ExecutorService executorService;
    /**
     * 线程池大小
     */
    public static final int THREAD_POOL_SIZE = 50;

    HttpServer() {
        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    /**
     * 启动HttpServer服务器
     * 使用了try-with-resource: since jdk1.7
     */
    public void start() {
        // 创建ServerSocket
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // 客户端连接
                Socket socket = serverSocket.accept();
                // 使用线程池处理该socket
                ServerHandle serverHandle = new ServerHandle(socket);
                executorService.execute(serverHandle);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
