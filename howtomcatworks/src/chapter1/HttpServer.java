package chapter1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * Created by kanyuxia on 2017/4/16.
 * HttpServer是模拟的Http服务器：接受Http请求静态资源，响应静态资源。
 */
public class HttpServer {
    /**
     * HttpServer端口号
     */
    public static final int PORT = 10086;
    /**
     * HttpServer静态文件根目录
     */
    public static final String WEB_ROOT = "E:/java/HttpServer";
    /**
     * 关闭HttpServer字符串：与 Http请求URL做比较。
     */
    public static final String SHUT_DOWN = "/shutdown";
    /**
     * 是否关闭HttpServer
     */
    public boolean isShutDown = false;


    /**
     * 启动 HttpServer
     * 使用了try-with-Resource：since jdk1.7
     */
    public void startServer() {
        // 创建ServerSocket
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println();
            System.exit(1);
        }
        // 等待客户端连接
        while (!isShutDown) {
            try (Socket socket = serverSocket.accept();
                 InputStream inputStream = socket.getInputStream();
                 OutputStream outputStream = socket.getOutputStream()) {
                // 创建Request对象，解析URL
                Request request = new Request(inputStream);
                request.parse();
                // 创建Response对象，调用响应方法
                Response response = new Response(request, outputStream);
                response.handle();
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
