package http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kanyuxia on 2017/4/14.
 */
public class SingleFileHTTPClient {
    public static void main(String[] args) {
        String encoding = "UTF-8";
        String host = "127.0.0.1";
        int port = 10086;

        Client client = new Client(encoding, host, port);
        client.start();
    }
}

class Client {
    // 客户端数目
    private static final int CLIENT_NUM = 20;
    // Request header
    private final byte[] header;
    // Request content
    private final byte[] content;
    // 数据编码方式
    private final String encoding;
    // 服务器地址
    private final String host;
    // 服务器端口
    private final int port;
    // 客户端线程池
    private final ExecutorService threadPools;

    public Client(String encoding, String host, int port) {
        this.threadPools = Executors.newFixedThreadPool(CLIENT_NUM);
        this.content = new byte[0];
        this.encoding = encoding;
        this.host = host;
        this.port = port;
        String header = "GET " + host + "HTTP/1.1\t\n"
                + "\t\n";
        this.header = header.getBytes(Charset.forName("US-ASCII"));
    }

    /**
     * 启动所有的客户端，以连接服务器
     */
    public void start() {
        for (int i = 0; i < CLIENT_NUM; i++) {
            Socket conection = null;
            try {
                conection = new Socket(host, port);
                threadPools.execute(new ClientHander(i, conection));
            } catch (IOException e) {
                System.out.println("Client" + i + " Connection Exeception: " + e);
            }
        }
    }

    /**
     * ClientHander: 客户端处理器，处理 Server的响应。
     */
    private class ClientHander implements Runnable {
        private final int id;
        private final Socket connection;

        public ClientHander(int id, Socket connection) {
            this.id = id;
            this.connection = connection;
        }

        @Override
        public void run() {
            BufferedInputStream in = null;
            BufferedOutputStream out = null;
            try {
                in = new BufferedInputStream(connection.getInputStream());
                out = new BufferedOutputStream(connection.getOutputStream());
                StringBuilder response = new StringBuilder();
                // 向Server发送Reqeust
                out.write(header);
                out.write(content);
                out.flush();
                // 接受Server响应
                for (int b = in.read(); b != -1; b = in.read()) {
                    response.append((char) b);
                }
                System.out.println("Client " + id + " read:\r\n" + response);
            } catch (IOException e) {
                System.out.println("Client " + id + " Socket Get InputStream/OuputStream Exeception: \" + e");
            } finally {
                // Close InputStream
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // Close OutputStream
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // Close Client-Server Socket
                try {
                    this.connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
