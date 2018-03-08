import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kanyuxia on 2017/6/1.
 */
public class ConnectionManager {
    private static ConnectionPool connectionPool = new ConnectionPool();

    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            return connectionPool.fetchConnection(1000);
        }
    };

    public static Connection getConnection() {
        return connectionHolder.get();
    }

    public static void releaseConnection() {
        connectionPool.releaseConnection(connectionHolder.get());
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Connection connection = ConnectionManager.getConnection();
                    System.out.println(connection);
                    releaseConnection();
                }
            });
        }
    }
}

/**
 * 连接池
 */
class ConnectionPool {
    private final LinkedList<Connection> pool = new LinkedList<>();

    ConnectionPool() {
        this(10);
    }

    ConnectionPool(int initialSize) {
        initConnection(initialSize);
    }

    /**
     * 从连接池中获取Connection：超时等待
     * @param mills 等待时间：单位毫秒
     */
    Connection fetchConnection(long mills) {
        long future = System.currentTimeMillis() + mills;
        long remaining = mills;
        synchronized (pool) {
            while (pool.isEmpty() && remaining > 0) {
                try {
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Connection result = null;
            if (!pool.isEmpty()) {
                result = pool.removeFirst();
            }
            return result;
        }
    }

    /**
     * 把该Connection放回连接池中
     * @param connection connection
     */
    void releaseConnection(Connection connection) {
        synchronized (pool) {
            pool.addLast(connection);
            pool.notifyAll();
        }
    }

    /**
     * 初始化连接池
     * @param initialSize 连接池大小
     */
    private void initConnection(int initialSize) {
        for (int i = 0; i < initialSize; i++) {
            pool.addLast(JDBCUtil.createConnection());
        }
    }
}

/**
 * JDBC工具类：创建Connection
 */
class JDBCUtil {
    static Connection createConnection() {
        Connection connection = null;
        String driverClass = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/hibernate?useUnicode=true&characterEncoding=utf8";
        String user = "root";
        String password = "";
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
