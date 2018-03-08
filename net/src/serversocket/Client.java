package serversocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kanyuxia on 2017/4/13.
 */
public class Client {
    public static final String HOST = "127.0.0.1";
    public static final int SERVER_PORT = 10086;
    public static final int CLIENT_NUM = 20;

    public static void main(String[] args) {
        Client client = new Client();
        client.startClient();
    }

    public void startClient() {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < CLIENT_NUM; i++) {
            Socket connection;
            try {
                connection = new Socket(HOST, SERVER_PORT);
                executorService.execute(new ClientTask(connection));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ClientTask implements Runnable {
    private static int count = 0;
    private final Socket connection;
    private final int id = count++;

    ClientTask(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String read = reader.readLine();
            connection.close();
            System.out.println("ClientTask " + id + " read: " + read);
        } catch (Exception e) {
            System.out.println("Exeception1: " + e);
        }
    }
}
