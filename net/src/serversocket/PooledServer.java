package serversocket;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kanyuxia on 2017/4/13.
 */
public class PooledServer {
    public static final int POOL_MAX_NUM = 50;
    public static final int SERVER_PORT = 10086;

    public static void main(String[] args) {
        PooledServer server = new PooledServer();
        server.start();
    }

    public void start() {
        ExecutorService threadPool = Executors.newFixedThreadPool(POOL_MAX_NUM);
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                Socket connection = serverSocket.accept();
                ServerTask task = new ServerTask(connection);
                threadPool.execute(task);
            }
        } catch (Exception e) {
            System.out.println("Exception4: " + e);
        }
    }

}

class ServerTask implements Runnable {
    private final Socket connection;

    ServerTask(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(connection.getOutputStream()))) {
            writer.write("hello client" + "\r\n");
        } catch (Exception e) {
            System.out.println("Exeception3: " + e);
        }
    }
}
