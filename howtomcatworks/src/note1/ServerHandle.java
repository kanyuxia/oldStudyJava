package note1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by kanyuxia on 2017/4/24.
 * ServerHandle服务器处理器，处理HTTP请求
 */
public class ServerHandle implements Runnable {
    private final Socket socket;

    ServerHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            // 创建Request对象，解析URL
            Request request = new Request(inputStream);
            request.parse();
            // 创建Response对象
            Response response = new Response(request, outputStream);
            // 请求的是Servlet资源
            if (request.getUrl().startsWith("/servlet/")) {
                // 创建ServletProcessor对象，处理请求
                ServletProcessor servletProcessor = new ServletProcessor();
                servletProcessor.process(request, response);
            } else {
                // 请求的是静态资源
                StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
                staticResourceProcessor.process(request, response);
            }
            // 关闭socket
            socket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
