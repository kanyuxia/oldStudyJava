package chapter1;

import java.io.*;
import java.net.URLConnection;

/**
 * Created by kanyuxia on 2017/4/16.
 */
public class Response {
    private final Request request;

    private final OutputStream outputStream;

    public Response(Request request, OutputStream outputStream) {
        this.request = request;
        this.outputStream = outputStream;
    }

    /**
     * 响应请求
     */
    public void handle() {
        BufferedOutputStream out = new BufferedOutputStream(outputStream);
        // 请求的本地静态文件地址
        String fileName = HttpServer.WEB_ROOT + request.getUrl();
        File file = new File(fileName);
        // 文件是否存在
        if(file.exists()) {
            String mimeType = URLConnection.getFileNameMap().getContentTypeFor(file.getName());
            // 发送header
            try {
                String header = "HTTP/1.1 200 OK\r\n"
                        + "HttpServer: 2.0\r\n"
                        + "Content-Length: " + file.length() + "\r\n"
                        + "Content-Type: " + mimeType + "; charset=UTF-8"  + "\r\n"
                        + "\r\n";
                out.write(header.getBytes());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] bytes = new byte[1024];
            // 发送文件
            try (BufferedInputStream in = new BufferedInputStream(
                    new FileInputStream(file))) {
                for (int b = in.read(bytes); b != -1; b = in.read(bytes)) {
                    out.write(bytes, 0 , b);
                    out.flush();
                }
            } catch (IOException e) {
                System.out.println();
            }
            // 关闭流
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        // 文件不存在,
        String header = "HTTP/1.1 404 Not Found" + "\r\n"
                + "\r\n";
        String content = "<html>\r\n" +
                "<head>" +
                "</head>" +
                "<body>" +
                "File Not Found" +
                "</body>" +
                "</html>";
        try {
            // 发送数据
            out.write(header.getBytes());
            out.write(content.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
