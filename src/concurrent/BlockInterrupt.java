package concurrent;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kanyuxia on 2017/3/10.
 */
public class BlockInterrupt {
    public static void main(String[] args) throws Exception {
//        testCancel();
//        testCloseIO();
//        testNIO();
        testLock();
    }

    /**
     * 尝试调用future.cancel() or executor.shutdownNow()方法中断线程阻塞状态：
     * sleep可以中断，抛出InterruptedException
     * wait、IO、synchronized不能中断
     */
    private static void testCancel(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("Interrupting SleepBlocked");
        Future<?> future = executorService.submit(new SleepBlocked());
        System.out.println("Interrupting WaitBlocked");
        Future<?> future1 = executorService.submit(new WaitBlocked());
        System.out.println("Interrupting IOBlocked");
        Future<?> future2 = executorService.submit(new IOBlocked(System.in));
        System.out.println("Interrupting SynchronizeBlocked");
        Future<?> future3 = executorService.submit(new SynchronizeBlocked());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("invoke future.cancel()");
        future.cancel(true);
        future1.cancel(true);
        future2.cancel(true);
        future3.cancel(true);
//        executorService.shutdownNow();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    /**
     * 关闭影响线程阻塞的底层资源
     * wait-->notify() or notifyAll()
     * IO--> close inputStream or outputStream
     * synchronized-->
     */
    private static void testCloseIO() throws Exception {
        ServerSocket server = new ServerSocket(8000);
        InputStream socketInput = new Socket("localhost", 8000).getInputStream();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<?> future = executorService.submit(new IOBlocked(socketInput));

        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Shuting down all threads");
        executorService.shutdownNow();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("Closing " + socketInput.getClass().getName());
        socketInput.close();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Closing " + System.in.getClass().getName());
        System.out.println(future.isDone());
    }

    /**
     * nio类更为人性化的I/O中断
     * nio可以直接调用future.cancel() or executor.shutdownNow()方法中断线程
     */
    private static void testNIO() throws IOException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket server = new ServerSocket(8000);
        InetSocketAddress address = new InetSocketAddress("localhost", 8000);
        SocketChannel socketChannel = SocketChannel.open(address);
        SocketChannel socketChannel1 = SocketChannel.open(address);

        Future<?> future = executorService.submit(new NIOBlocked(socketChannel));
        executorService.execute(new NIOBlocked(socketChannel1));
        executorService.shutdown();

        TimeUnit.SECONDS.sleep(1);
        future.cancel(true);

        TimeUnit.SECONDS.sleep(1);
        socketChannel1.close();
    }

    /**
     * ReentrantLock上阻塞的任务能够被中断
     */
    private static void testLock() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<?> future = executorService.submit(new LockBlocked());
        TimeUnit.SECONDS.sleep(1);
        future.cancel(true);
        executorService.shutdown();
    }
}

class LockBlocked implements Runnable {
    private Lock lock = new ReentrantLock();

    LockBlocked(){
        lock.lock();
    }

    @Override
    public void run() {
        System.out.println("start f()");
        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
        System.out.println("Exit f()");
    }
}

class NIOBlocked implements Runnable {
    private final SocketChannel channel;

    NIOBlocked(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            System.out.println("Waiting for read() in " + this);
            channel.read(ByteBuffer.allocate(5));
        } catch (ClosedByInterruptException e) {
            System.out.println("ClosedByInterruptException");
        } catch (AsynchronousCloseException e) {
            System.out.println("AsynchronousCloseException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
        System.out.println("Exiting NIOBlocked.run() " + this);
    }
}

class SleepBlocked implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
        System.out.println("Exiting SleepBlocked.run()");
    }
}

class WaitBlocked implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.wait();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
        System.out.println("Exiting WaitBlocked.run()");
    }
}

class IOBlocked implements Runnable {
    private InputStream inputStream;

    IOBlocked(InputStream inputStream){
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        try {
            int i = inputStream.read();
        } catch (IOException e) {
            if(Thread.currentThread().isInterrupted()){
                System.out.println("Interrupted from blocked I/O");
            } else {
                System.out.println("Other RuntimeException");
            }
        }
        System.out.println("Exiting IOBlocked.run()");
    }
}

class SynchronizeBlocked implements Runnable {

    synchronized void f(){
        while (true) {
            Thread.yield();
        }
    }

    SynchronizeBlocked(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                f();
            }
        }).start();
    }

    @Override
    public void run() {
        System.out.println("Trying to call f()");
        f();
        System.out.println("Exiting SynchronizeBlocked.run()");
    }
}
