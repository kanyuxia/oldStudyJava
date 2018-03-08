package http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kanyuxia on 2017/4/14.
 */
public class SingleFileHTTPServer {
   public static void main(String[] args) throws UnsupportedEncodingException {
       String data = "<html>\r\n" +
               "<head>" +
               "</head>" +
               "<body>" +
               "hello world" +
               "</body>" +
               "</html>";
       String encoding = "UTF-8";
       int port = 10086;
       String mimeType = "text/html";

       Server server = new Server(data, encoding, mimeType, port);
       server.start();

   }
}

class Server {
    // 处理器线程池线程数量
    private static final int THREAD_POOL_SIZE = 50;
    // Response header
    private final byte[] header;
    // Response content
    private final byte[] content;
    // 服务器端口
    private final int port;
    // 数据编码方式
    private final String encoding;
    // 处理器线程池
    private final ExecutorService threadPools;

    public Server(String data, String encoding, String mimeType, int port) throws UnsupportedEncodingException {
        this(data.getBytes(encoding), encoding, mimeType, port);
    }

    public Server(byte[] data, String encoding, String mimeType, int port) throws UnsupportedEncodingException {
        this.content = data;
        this.encoding = encoding;
        this.port = port;
        String header = "HTTP/1.1 200 OK\r\n"
                + "Server: OneFile 2.0\r\n"
                + "Content-Length: " + content.length + "\r\n"
                + "Content-Type: " + mimeType + "; charset=" + encoding + "\r\n"
                + "\r\n";
        this.header = header.getBytes(Charset.forName("US-ASCII"));
        this.threadPools = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    /**
     * 启动 SingleFileHTTPServer，等待Client的连接
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket connetion = serverSocket.accept();
                threadPools.execute(new ServerHander(connetion));
            }
        } catch (Exception e) {
            System.out.println("ServerSocket Create Exception: " + e);
        }
    }

    /**
     * ServerHander: 服务器处理器，处理 Client的请求。
     */
    private class ServerHander implements Runnable {
        private final Socket connection;

        ServerHander(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            BufferedOutputStream out = null;
            BufferedInputStream in = null;
            try {
                out = new BufferedOutputStream(connection.getOutputStream());
                in = new BufferedInputStream(connection.getInputStream());
                StringBuilder request = new StringBuilder(80);
                // 读取Reqeust中读取第一行数据
                while (true) {
                    int b = in.read();
                    if (b == '\r' || b == '\n' || b == -1) {
                        break;
                    }
                    request.append((char) b);
                }
                // 若第一行数据有HTTP版本号，则代表该请求为HTTP请求，就需要 write HTTP Reponse header
                if (request.toString().contains("HTTP/")) {
                    out.write(header);
                }
                // write HTTP Reponse contet
                out.write(content);
                out.flush();
            } catch (IOException e) {
                System.out.println("Server Socket Get InputStream/OuputStream Exeception: " + e);
            } finally {
                // 关闭OuputStream
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // 关闭InputSteam
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // 关闭Server-Client Socket
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


