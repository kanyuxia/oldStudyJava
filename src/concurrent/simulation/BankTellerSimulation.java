package concurrent.simulation;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by kanyuxia on 2017/3/22.
 */
public class BankTellerSimulation {
    static final int MAX_LINE_SIZE = 50;
    static final int ASJUSTMENT_PERIOD = 1000;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // If line is too long, customers will leave:
        CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);
        executorService.execute(new CustomerGenerator(customers));
        // Manager will add and remove tellers as necessary:
        executorService.execute(new TellerManager(executorService, customers, ASJUSTMENT_PERIOD));

        TimeUnit.SECONDS.sleep(10);
        executorService.shutdownNow();
    }
}

// Read-only objects do not require synchronization
class Customer {
    private final int serviceTime;

    Customer(int serviceTime) {
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

// Teach the customer line to display itself;
class CustomerLine extends ArrayBlockingQueue<Customer> {

    public CustomerLine(int maxLineSize) {
        super(maxLineSize);
    }

    @Override
    public String toString() {
        if(this.size() == 0) {
            return "[empty]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(Customer customer : this) {
            stringBuilder.append(customer);
        }
        return stringBuilder.toString();
    }
}

// Randomly add customers to a queue
class CustomerGenerator implements Runnable {
    private CustomerLine customers;
    private static Random random = new Random(47);

    CustomerGenerator(CustomerLine customers) {
        this.customers = customers;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(300));
                customers.put(new Customer(random.nextInt(1000)));
            }
        } catch (InterruptedException e) {
            System.out.println("CustomerGenerator interrupted");
        }
        System.out.println("CustomerGenerator terminating");
    }
}

class Teller implements Runnable, Comparable<Teller> {
    private static int counter = 0;
    private final int id = counter++;

    //Customers served during this shift:
    private int customersServed = 0;
    private CustomerLine customers;
    private boolean servingCustomerLine = true;

    Teller(CustomerLine customers) {
        this.customers = customers;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                Customer customer = customers.take();
                TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
                // 出纳员服务该顾客，有人则等待
                synchronized(this) {
                    customersServed++;
                    while(!servingCustomerLine) {
                        wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println(this + "interrupted");
        }
        System.out.println(this + "termination");
    }

    public synchronized void doSomethingElse() {
        customersServed = 0;
        servingCustomerLine = false;
    }

    public synchronized void serveCustomerLine() {
        // assert相当于if, 测试使用。
        /**
         * assert相当于if, 测试使用
         * assert <boolean表达式> : <错误信息表达式>
         *     如果<boolean表达式>为true，则程序继续执行。
         *     如果为false，则程序抛出java.lang.AssertionError，并输出<错误信息表达式>。
         */
        assert !servingCustomerLine:"Already serving: " + this;
        servingCustomerLine = true;
        notifyAll();
    }

    @Override
    public String toString() {
        return "Teller " + id + " ";
    }

    public String shortString() {
        return "T" + id;
    }

    @Override
    // Used by priority queue
    public int compareTo(Teller o) {
        return customersServed < o.customersServed ? -1 :
                (customersServed == o.customersServed ? 0 : 1);
    }
}

class TellerManager implements Runnable {
    private ExecutorService executorService;
    private CustomerLine customers;
    private PriorityBlockingQueue<Teller> workingTellers = new PriorityBlockingQueue<>();
    private Queue<Teller> tellersDoingOtherThings = new LinkedBlockingDeque<>();
    private int adjsutmentPeriod;
    private static Random random = new Random(47);

    TellerManager(ExecutorService executorService,
                  CustomerLine customers, int adjsutmentPeriod){
        this.executorService = executorService;
        this.customers = customers;
        this.adjsutmentPeriod = adjsutmentPeriod;
        // Start with a single teller
        Teller teller = new Teller(customers);
        executorService.execute(teller);
        workingTellers.put(teller);
    }
    // adjust-->调整
    public void adjustTellerNumber() {
        // This is actually a control system, By adjusting
        // the numbers, you can reveal stability issues in th control mechanism
        // if line is too long, add another teller:
        if(customers.size() / workingTellers.size() > 2) {
            // If tellers are on break or doing
            // another job, breaking one back:
            if(tellersDoingOtherThings.size() > 0) {
                Teller teller = tellersDoingOtherThings.remove();
                teller.serveCustomerLine();
                workingTellers.offer(teller);
                return;
            }
            // Else create (hire) a new teller
            Teller teller = new Teller(customers);
            executorService.execute(teller);
            workingTellers.add(teller);
            return;
        }
        // If line is short enough, remove a teller
        if(workingTellers.size() > 1 && customers.size() / workingTellers.size() < 2) {
            reassignOneTeller();
        }
        // If there is no line, wo only need one teller:
        if(customers.size() == 0) {
            while(workingTellers.size() > 1) {
                reassignOneTeller();
            }
        }
    }

    // Give a teller a different job or a break
    private void reassignOneTeller() {
        Teller teller = workingTellers.poll();
        teller.doSomethingElse();
        tellersDoingOtherThings.add(teller);

    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(adjsutmentPeriod);
                adjustTellerNumber();
                System.out.print(customers + " { ");
                for(Teller teller : workingTellers) {
                    System.out.print(teller.shortString() + " ");
                }
                System.out.println("}");
            }
        } catch (InterruptedException e) {
            System.out.println(this + "interrupted");
        }
        System.out.println(this + "termination");
    }

    @Override
    public String toString() {
        return "TellerManger ";
    }
}
