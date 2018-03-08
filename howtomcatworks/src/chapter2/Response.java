package chapter2;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.Locale;

/**
 * Created by kanyuxia on 2017/4/19.
 * Response类：实现了ServletResponse接口，如果HTTP请求是Servlet，则服务器创建ServletRequest和ServletResponse对象，
 * 并传入Servlet的service()方法。
 */
public class Response implements ServletResponse {
    private final OutputStream outputStream;
    private final Request request;

    public Response(OutputStream outputStream, Request request) {
        this.outputStream = outputStream;
        this.request = request;
    }

    /**
     * 发送静态资源方法
     * @throws IOException
     */
    public void sendStaticResource() throws IOException {
        BufferedOutputStream out = new BufferedOutputStream(outputStream);
        //请求的本地静态文件地址
        String fileName = HttpServer.STATIC_RESOURCE_ROOT + request.getUrl();
        File file = new File(fileName);
        // 文件是否存在
        if (file.exists()) {
            String mimeType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
            String header = "HTTP/1.1 200 OK\r\n"
                    + "Content-Length: " + file.length() + "\r\n"
                    + "Content-Type: " + mimeType + "; charset=UTF-8\r\n"
                    + "\r\n";
            // 发送header
            out.write(header.getBytes());
            out.flush();
            // 发送content
            byte[] buffer = new byte[1024];
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
                for (int b = in.read(buffer); b != -1; b = in.read(buffer)) {
                    out.write(buffer, 0, b);
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println();
            }
            // 关闭流
            try {
                out.close();
            } catch (IOException e) {
                System.out.println();
            }
            return;
        }
        // 文件不存在
        String header = "HTTP/1.1 404 NOT FOUND\r\n"
                + "\r\n";
        String content = "<html>\r\n" +
                "<head>" +
                "</head>" +
                "<body>" +
                "File Not Found" +
                "</body>" +
                "</html>";
        // 发送数据
        try {
            out.write(header.getBytes());
            out.write(content.getBytes());
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            // 关闭流
            try {
                out.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }


    // 实现ServletResponse接口相关方法
    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(outputStream, true);
    }

    @Override
    public void setCharacterEncoding(String charset) {

    }

    @Override
    public void setContentLength(int len) {

    }

    @Override
    public void setContentLengthLong(long len) {

    }

    @Override
    public void setContentType(String type) {

    }

    @Override
    public void setBufferSize(int size) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
