package concurrent.simulation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by kanyuxia on 2017/3/24.
 */
public class Test35 {
    static final int MAX_LINE_SIZE = 50;
    static final int NUM_OF_SERVER = 3;
    static final int ADJUSTMENT_PERIOD = 1000;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ClientLine line = new ClientLine(MAX_LINE_SIZE);
        ClientGenerator generator = new ClientGenerator(line);
        executorService.execute(generator);
        executorService.execute(new SimulationManager(executorService, line, generator,
                ADJUSTMENT_PERIOD, NUM_OF_SERVER));


        TimeUnit.SECONDS.sleep(10);
        executorService.shutdownNow();
    }
}

class Client {
    private final int serviceTime;

    Client(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public String toString() {
        return "[" + serviceTime + "]";
    }
}

class ClientLine extends ArrayBlockingQueue<Client> {

    public ClientLine(int capacity) {
        super(capacity);
    }

    @Override
    public String toString() {
        if(this.size() == 0) {
            return "[empty]";
        }
        StringBuilder result = new StringBuilder();
        result.append("[");
        for(Client client : this) {
            result.append(client);
        }
        result.append("]");
        return result.toString();
    }
}

class ClientGenerator implements Runnable {
    private ClientLine line;
    volatile int loadFactory = 1;
    private Random random = new Random(47);

    ClientGenerator(ClientLine line) {
        this.line = line;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                Client client = new Client(random.nextInt(1000));
                line.put(client);
                TimeUnit.MILLISECONDS.sleep(1000 / loadFactory);
            }
        } catch (InterruptedException e) {
            System.out.println("ClientGenerator interrupted");
        }
        System.out.println("ClientGenerator termination");
    }
}

class Server implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private ClientLine line;

    Server(ClientLine line) {
        this.line = line;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                Client client = line.take();
                TimeUnit.MILLISECONDS.sleep(client.getServiceTime());
            }
        } catch (InterruptedException e) {
            System.out.println("Server interrupted");
        }
        System.out.println("Server termination");
    }

    @Override
    public String toString() {
        return "Server " + id + " ";
    }

    public String shortString() {
        return "S" + id;
    }
}

class SimulationManager implements Runnable {
    private ExecutorService executorService;
    private ClientLine line;
    private ClientGenerator generator;
    private Queue<Server> servers = new LinkedList<>();
    private int adjustmentPeriod;
    // Indicates whether the queue is stable
    private boolean stable = true;
    // The client number of previous
    private int prevSize;

    SimulationManager(ExecutorService executorService, ClientLine line,
                      ClientGenerator generator, int adjustmentPeriod, int num) {
        this.executorService = executorService;
        this.line = line;
        this.generator = generator;
        this.adjustmentPeriod = adjustmentPeriod;
        for(int i = 0; i < num; i++) {
            Server server = new Server(line);
            executorService.execute(server);
            servers.add(server);
        }
    }

    public void adjustLoadFactory() {
        // This is actually a control system, By adjusting the numbers of client,
        // you can reveal stability issues in the control mechanism.
        // If line is stable, increase the 'adjustLoadFactory'
        if(line.size() > prevSize) {
            if(stable) {  // Was stable previous time
                stable = false;
            }
            else if(!stable) {  // Not stable for a second time
                System.out.println("Peak the adjustLoadFactory: ~" + generator.loadFactory);
                executorService.shutdownNow();
            }
        } else {
            System.out.println("New adjustLoadFactory: " + ++generator.loadFactory);
            stable = true;
        }
        prevSize = line.size();
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
                System.out.print(line + "{");
                for(Server server : servers) {
                    System.out.print(server.shortString() + " ");
                }
                System.out.print("}");
                adjustLoadFactory();
            }
        } catch (InterruptedException e) {
            System.out.println("SimulationManager interrupted");
        }
        System.out.println("SimulationManager termination");

    }

    @Override
    public String toString() {
        return "SimulationManager ";
    }
}

