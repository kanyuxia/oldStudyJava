package chapter2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * Created by kanyuxia on 2017/4/19.
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
     * 关闭HttpServer字符串：与 Http请求URL做比较。
     */
    public static final String SHUT_DOWN = "/shutdown";
    /**
     * 是否关闭HttpServer
     */
    public boolean isShutDown = false;

    /**
     * 启动HttpServer服务器
     * 使用了try-with-resource: since jdk1.7
     */
    public void startServer() {
        ServerSocket serverSocket = null;
        // 创建ServerSocket
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println();
            System.exit(1);
        }
        // 等待客户端连接
        while (!isShutDown) {
            try (Socket socket = serverSocket.accept();
                 OutputStream outputStream = socket.getOutputStream();
                 InputStream inputStream = socket.getInputStream()) {
                // 创建Request对象，解析URL
                Request request = new Request(inputStream);
                request.parse();
                // 创建Response对象
                Response response = new Response(outputStream, request);
                // 请求的是Servlet资源
                if (request.getUrl().startsWith("/servlet/")) {
                    // 创建ServletProcessor对象，处理请求
                    ServletProcessor servletProcessor = new ServletProcessor();
                    servletProcessor.process(request, response);
                } else {
                    // 请求的是静态资源
                    StaticResourceProcessor resourceProcessor = new StaticResourceProcessor();
                    resourceProcessor.process(request, response);
                }
                // 判断否URL是否是是关闭服务器URL：true-->关闭服务器
                isShutDown = Objects.equals(request.getUrl(), SHUT_DOWN);
            } catch (IOException e) {
                System.out.println();
                continue;
            }
        }
        // 关闭ServerSocket
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
