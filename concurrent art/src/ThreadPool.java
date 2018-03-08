import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class ThreadPool<Job extends Runnable> {
    static final int THREAD_SIZE = 20;
    private List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
    private final LinkedList<Job> jobs = new LinkedList<>();

    ThreadPool() {
        this(THREAD_SIZE);
    }

    ThreadPool(int initialSize) {
        initWorker(initialSize);
    }

    /**
     * 初始化所有的线程
     */
    private void initWorker(int initialSize) {
        for (int i = 0; i < initialSize; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker);
            worker.setCurrentThread(thread);
            thread.start();
        }
    }

    /**
     * 执行任务
     * @param job 任务
     */
    public void execute(Job job) {
        if (job != null) {
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notifyAll();
            }
        }
    }

    /**
     * 关闭线程池
     */
    public void shutDown() {
        for (Worker worker : workers) {
            worker.cancel();
        }

    }

    /**
     * Worker：执行任务
     */
    private class Worker implements Runnable {

        private Thread currentThread;

        public void setCurrentThread(Thread currentThread) {
            this.currentThread = currentThread;
        }

        @Override
        public void run() {
            while (true) {
                Job job = null;
                synchronized (jobs) {
                    // 任务队列中没有任务，则等待
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }
                // 执行任务
                job.run();
            }
        }

        /**
         * 结束该Worker
         */
        public void cancel() {
            currentThread.interrupt();
        }
    }

}