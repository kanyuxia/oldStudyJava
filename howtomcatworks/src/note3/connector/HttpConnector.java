package note3.connector;

import note3.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;
import java.util.Vector;

/**
 * Created by kanyuxia on 2017/5/6.
 */
public class HttpConnector implements Runnable, Connector, Lifecycle {
    /**
     * The lifecycle event support for this component.
     */
    private LifecycleSupport lifecycleSupport = new LifecycleSupport(this);

    /**
     * The Container used for processing requests received by this Connector.
     * Servlet容器
     */
    private Container container = null;

    /**
     * The current number of processors that have been created.
     * 当前已经创建的HttpProcessor
     */
    private int curProcessors = 0;

    /**
     * The minimum number of processors to start at initialization time.
     * HttpProcessor对象池最小对象数
     */
    private int minProcessors = 5;

    /**
     * The maximum number of processors allowed, or <0 for unlimited.
     * HttpProcessor对象池最大对象数
     */
    private int maxProcessors = 20;

    /**
     * The port number on which we listen for HTTP requests.
     * 服务器端口号
     */
    private int port = 10086;

    /**
     * The set of processors that have been created but are not currently
     * being used to process a request.
     * 存放已经创建但未被使用的Http处理器对象
     */
    private final Stack<HttpProcessor> processors = new Stack<>();

    /**
     * The set of processors that have ever been created.
     * 存放已经创建的Http处理器
     */
    private Vector<HttpProcessor> created = new Vector<>();

    /**
     * The request scheme that will be set on all requests received
     * through this connector.
     * 服务器处理请求模式(协议)
     */
    private String scheme = "http";

    /**
     * The server socket through which we listen for incoming TCP connections.
     * 服务器ServerSocket
     */
    private ServerSocket serverSocket = null;

    /**
     * 是否启动
     */
    private boolean started = false;

    /**
     * 当前线程
     */
    private Thread thread = null;

    /**
     * The background thread that listens for incoming TCP/IP connections and
     * hands them off to an appropriate processor.
     * 启动Http连接器，监听Http请求，把socket分发给HttpProcessor进行处理。
     */
    public void run() {
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpProcessor processor = createProcessor();
            if (processor != null) {
                processor.assign(socket);
            }
        }
    }

    /**
     * Create (or allocate) and return an available processor for use in
     * processing a specific HTTP request, if possible.  If the maximum
     * allowed processors have already been created and are in use, return
     * <code>null</code> instead.
     * 创建HttpProcessor：1. 从栈中拿 2. new一个 3. 返回null
     */
    public HttpProcessor createProcessor() {
        synchronized (processors) {
            if (processors.size() > 0) {
                return processors.pop();
            }
            if (maxProcessors > 0 && curProcessors < maxProcessors) {
                return newProcessor();
            } else {
                if (maxProcessors < 0) {
                    return newProcessor();
                }
                return null;
            }
        }
    }

    /**
     * Initialize this connector (create ServerSocket here!)
     * 创建ServerSocket，在原HttpConnector中使用ServerSocketFactory创建，这里就直接创建
     */
    public void initialize() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Create and return a new processor suitable for processing HTTP
     * requests and returning the corresponding responses.
     * 创建HttpProcessor对象，并使用运行它(它运行在一个单独的后台线程中)
     */
    private HttpProcessor newProcessor() {
        HttpProcessor processor = new HttpProcessor(this, curProcessors++);
        created.addElement(processor);
        if (processor instanceof Lifecycle) {
            ((Lifecycle) processor).start();
        }
        processor.start();
        return processor;
    }

    /**
     * Recycle the specified Processor so that it can be used again.
     * 回收已经没有使用的HttpProcessor
     * @param processor The processor to be recycled
     */
    void recycle(HttpProcessor processor) {
        processors.push(processor);
    }

    @Override
    public Request createRequest() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setConnector(this);
        return httpRequest;
    }

    @Override
    public Response createResponse() {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setConnector(this);
        return httpResponse;
    }

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public String getScheme() {
        return scheme;
    }

    @Override
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public void setMaxProcessors(int maxProcessors) {
        this.maxProcessors = maxProcessors;
    }

    public int getMaxProcessors() {
        return maxProcessors;
    }

    public void setMinProcessors(int minProcessors) {
        this.minProcessors = minProcessors;
    }

    public int getMinProcessors() {
        return minProcessors;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        lifecycleSupport.addLifecycleListener(listener);
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {
        lifecycleSupport.removeLifecycleListener(listener);
    }

    @Override
    public void stop() {
        if (!started) {
            return;
        }

        lifecycleSupport.fireLifecycleListener(STOP_EVENT, null);
        started = false;

        // Gracefully shut down all processors we have created
        for (HttpProcessor processor : created) {
            if (processor instanceof Lifecycle) {
                ((Lifecycle) processor).stop();
            }
        }
        // Close the server socket we were using
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println();
            }
        }
        // Stop our background thread
        thread = null;
        serverSocket = null;
    }

    /**
     * Begin processing requests via this Connector.
     * 启动Http连接器，并创建HttpProcessor线程对象池
     */
    public void start() {
        if (started) {
            return;
        }
        lifecycleSupport.fireLifecycleListener(BEFORE_START_EVENT, null);
        lifecycleSupport.fireLifecycleListener(START_EVENT, null);
        started = true;
        // 启动Http连接器
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
        // 创建最小HttpProcessor线程对象池
        while (curProcessors < minProcessors) {
            if (maxProcessors > 0 && curProcessors >= maxProcessors) {
                break;
            }
            HttpProcessor processor = newProcessor();
            recycle(processor);
        }
    }
}
