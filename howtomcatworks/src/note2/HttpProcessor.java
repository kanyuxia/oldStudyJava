package note2;

import javax.servlet.ServletException;
import java.io.*;
import java.net.Socket;

/**
 * Created by kanyuxia on 2017/4/26.
 * Http处理器
 */
public class HttpProcessor implements Runnable {

    /**
     * The HttpConnector with which this processor is associated.
     */
    private HttpConnector connector = null;

    /**
     * Is there a new socket available?
     * 是否有新的socket可用
     */
    private boolean available = false;

    /**
     * The socket we are currently processing a request for.  This object
     * is used for inter-thread communication only.
     */
    private Socket socket = null;

    /**
     * The identifier of this processor, unique per connector.
     */
    private int id = 0;

    /**
     * The HTTP request object we will pass to our associated container.
     */
    private HttpRequest httpRequest = null;

    /**
     * The HTTP response object we will pass to our associated container.
     */
    private HttpResponse httpResponse = null;


    /**
     * Construct a new HttpProcessor associated with the specified connector.
     *
     * @param connector HttpConnector that owns this processor
     * @param id Identifier of this HttpProcessor (unique per connector)
     */
    public HttpProcessor(HttpConnector connector, int id) {
        this.connector = connector;
        this.id = id;
        this.httpRequest = (HttpRequest) connector.createRequest();
        this.httpResponse = (HttpResponse) connector.createResponse();
    }


    /**
     * Start the background thread we will use for request processing.
     * 启动一个后台线程运行HttpProcessor
     */
    public void start() {
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }


    /**
     * The background thread that listens for incoming TCP/IP connections and
     * hands them off to an appropriate processor.
     * 等待HttpConnector分配socket，然后处理该scoket，最后通知HttpConnector回收该HttpProcessor
     */
    public void run() {
        while (true) {
            // Wait for the next socket to be assigned
            Socket socket = await();
            if (socket == null) {
                continue;
            }
            System.out.println(Thread.currentThread().getName());
            // Process the request from this socket
            process(socket);

            // Finish up this request
            connector.recycle(this);
        }
    }

    /**
     * Process an incoming HTTP request on the Socket that has been assigned
     * to this Processor.  Any exceptions that occur during processing must be
     * swallowed and dealt with.
     * 处理HttpConector分配的socket：1. 解析请求  2. 处理请求(调用容器处理)
     * @param socket The socket on which we are connected to the client
     */
    private void process(Socket socket) {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpRequest.setInput(input);
        httpResponse.setOutput(output);
        httpResponse.setHttpRequest(httpRequest);
        // Parse the incoming request
        parseConnection(socket);
        parseRequest(input, output);
        parseHeader(input, output);

        // Ask our Container to process this request
        try {
            connector.getContainer().invoke(httpRequest, httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        //
        httpRequest.recycle();
        httpResponse.recycle();
    }

    /**
     * Parse and record the connection parameters related to this request.
     * 解析连接
     * @param socket The socket on which we are connected
     */
    private void parseConnection(Socket socket) {
    }

    /**
     * Parse the incoming HTTP request and set the corresponding HTTP request
     * properties.
     * 解析请求行
     * @param input The input stream attached to our socket
     * @param output The output stream of the socket
     */
    private void parseRequest(InputStream input, OutputStream output) {
        // 在这里仅仅解析了requestURI
        BufferedInputStream inputStream = new BufferedInputStream(input);
        StringBuilder stringBuilder = new StringBuilder(1024);
        byte[] buffer = new byte[1024];
        int b = 0;
        try {
            b = inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < b; i++) {
            stringBuilder.append((char) buffer[i]);
        }
        int begin = stringBuilder.indexOf(" ") + 1;
        int end = stringBuilder.indexOf(" ", begin);
        String requestURI = stringBuilder.substring(begin, end);
        httpRequest.setRequestURI(requestURI);
    }

    /**
     * Parse the incoming HTTP request headers, and set the appropriate
     * request headers.
     * 解析请求头
     * @param input The input stream connected to our socket
     */
    private void parseHeader(InputStream input, OutputStream output) {

    }

    /**
     * Process an incoming TCP/IP connection on the specified socket.  Any
     * exception that occurs during processing must be logged and swallowed.
     * <b>NOTE</b>:  This method is called from our Connector's thread.  We
     * must assign it to our own thread so that multiple simultaneous
     * requests can be handled.
     * HttpConnector分配一个新的socket
     * @param socket TCP socket to process
     */
    public void assign(Socket socket) {
        synchronized (this) {
            // Wait for the Processor to get the previous Socket
            while (available) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Store the newly available Socket and notify our thread
            this.socket = socket;
            available = true;
            notifyAll();
        }
    }

    /**
     * Await a newly assigned Socket from our Connector, or <code>null</code>
     * if we are supposed to shut down.
     * 等待HttpConenctor分配一个新的socket
     */
    private Socket await() {
        synchronized (this) {
            // Wait for the Connector to provide a new Socket
            while (!available) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Notify the Connector that we have received this Socket
            available = false;
            notifyAll();
            return socket;
        }
    }
}
