import java.util.Random;

//  Person class (reused from ReverseApp concept)
class Person {
    private String name;
    private int    age;

    public Person(String name, int age) {
        this.name = name;
        this.age  = age;
    }

    public String getName() {
        return name;
    }
    public int    getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("%-15s (age %2d)", name, age);
    }
}

class PriorityQueue {
    private long[] queArray;
    private int    maxSize;
    private int    nItems;

    public PriorityQueue(int s) {
        maxSize  = s;
        queArray = new long[maxSize];
        nItems   = 0;
    }

    // Display

    public void display() {
        System.out.print("  PriorityQueue [front→rear, ascending key]: [");
        for (int i = 0; i < nItems; i++) {
            System.out.print(queArray[i]);
            if (i < nItems - 1) System.out.print(", ");
        }
        System.out.println("]  (nItems=" + nItems + ")");
    }

    // Insert at correct sorted position (O(n))
    public boolean insert(long value) {
        if (isFull()) {
            System.out.println("  [!] INSERT failed – PriorityQueue FULL");
            return false;
        }

        System.out.println("  INSERT(" + value + "):");
        int j;
        // Shift elements that are SMALLER than value one position toward rear
        // so the new item slots in at the correct sorted position.
        for (j = nItems - 1; j >= 0; j--) {
            if (queArray[j] < value) {
                System.out.println("    slot[" + j + "]=" + queArray[j] +
                                   " < " + value + " → shift right to slot[" + (j+1) + "]");
                queArray[j + 1] = queArray[j];
            } else {
                System.out.println("    slot[" + j + "]=" + queArray[j] +
                                   " >= " + value + " → stop shifting");
                break;
            }
        }
        // j+1 is now the correct insertion index
        queArray[j + 1] = value;
        System.out.println("    Placed " + value + " at slot[" + (j+1) + "]");
        nItems++;
        return true;
    }

    // Remove minimum (front)
    public long remove() {
        if (isEmpty()) {
            System.out.println("  [!] REMOVE failed – PriorityQueue EMPTY");
            return -1;
        }
        return queArray[--nItems];   // highest-priority = smallest key at rear
    }

    public long peekMin() {
        return queArray[nItems - 1];
    }

    public boolean isEmpty() { return nItems == 0; }
    public boolean isFull()  { return nItems == maxSize; }
    public int     size()    { return nItems; }
}

//  Ordinary Queue (circular, from QueueApp) for comparison
class OrdinaryQueue {
    private long[] queArray;
    private int    maxSize, front, rear, nItems;

    public OrdinaryQueue(int s) {
        maxSize  = s;
        queArray = new long[maxSize];
        front    = 0;
        rear     = -1;
        nItems   = 0;
    }

    public boolean insert(long j) {
        if (isFull()) return false;
        rear = (rear + 1) % maxSize;
        queArray[rear] = j;
        nItems++;
        return true;
    }

    public long remove() {
        if (isEmpty()) return -1;
        long temp = queArray[front];
        front = (front + 1) % maxSize;
        nItems--;
        return temp;
    }

    public boolean isEmpty() { return nItems == 0; }
    public boolean isFull()  { return nItems == maxSize; }
    public int     size()    { return nItems; }
}

//  Customer for simulation
class Customer {
    private int id;
    private int priority;      // lower number = higher priority (e.g. VIP=1, normal=5)
    private int serviceTime;
    private int timeLeft;

    public Customer(int id, int minSvc, int maxSvc, int minPri, int maxPri, Random rnd) {
        this.id          = id;
        this.serviceTime = minSvc + rnd.nextInt(maxSvc - minSvc + 1);
        this.priority    = minPri + rnd.nextInt(maxPri - minPri + 1);
        this.timeLeft    = serviceTime;
    }

    public boolean serve() { return --timeLeft <= 0; }

    public int getId()          { return id; }
    public int getPriority()    { return priority; }
    public int getServiceTime() { return serviceTime; }

    @Override
    public String toString() {
        return "Customer#" + id + "[pri=" + priority + ", svc=" + serviceTime + "]";
    }
}

//  Priority-Queue-based Customer Simulation
class PrioritySimulation {

    public static void run(int queueSize, int minSvc, int maxSvc,
                           int arrivalRate, int totalTicks) {
        System.out.println("---PRIORITY QUEUE CUSTOMER SIMULATION---");
        System.out.printf ("Queue size   : %-28d║%n", queueSize);
        System.out.printf ("Service time : %d – %-24d║%n", minSvc, maxSvc);
        System.out.printf ("Priority     : 1 (VIP) – 5 (normal)         ║%n");
        System.out.printf ("Arrival rate : 1 per ~%-21d║%n", arrivalRate);
        System.out.printf ("Duration     : %-28d║%n", totalTicks);

        PriorityQueue pq      = new PriorityQueue(queueSize);
        // Parallel array to hold full Customer objects by priority key
        Customer[]    waiting = new Customer[queueSize];
        int           wCount  = 0;

        Random   rnd     = new Random(42);
        Customer current = null;
        int customerId = 0, served = 0, dropped = 0, ticksIdle = 0;

        for (int tick = 1; tick <= totalTicks; tick++) {

            // Arrival
            if (rnd.nextInt(arrivalRate) == 0) {
                customerId++;
                Customer c = new Customer(customerId, minSvc, maxSvc, 1, 5, rnd);
                if (pq.isFull()) {
                    dropped++;
                    System.out.printf("[t=%3d] %s arrived – QUEUE FULL, turned away%n",
                                      tick, c);
                } else {
                    // Insert priority key; store Customer in parallel waiting list
                    // (simple insertion to keep parallel array sorted by priority too)
                    pq.insert(c.getPriority());
                    // Insert Customer into sorted waiting list (by priority ascending)
                    int pos = wCount;
                    while (pos > 0 && waiting[pos-1].getPriority() < c.getPriority()) {
                        waiting[pos] = waiting[pos-1];
                        pos--;
                    }
                    waiting[pos] = c;
                    wCount++;
                    System.out.printf("[t=%3d] %s arrived – queued (size=%d)%n",
                                      tick, c, pq.size());
                }
            }

            // Serve highest-priority customer
            if (current == null && !pq.isEmpty()) {
                pq.remove();                    // pops min priority key
                current = waiting[--wCount];    // pop highest-priority Customer
                System.out.printf("[t=%3d] Server starts %s%n", tick, current);
            }

            if (current != null) {
                if (current.serve()) {
                    System.out.printf("[t=%3d] %s finished service.%n", tick, current);
                    served++;
                    current = null;
                }
            } else {
                ticksIdle++;
            }
        }

        System.out.println("\n---PRIORITY SIMULATION SUMMARY---");
        System.out.println("Customers arrived  : " + customerId);
        System.out.println("Customers served   : " + served);
        System.out.println("Customers dropped  : " + dropped);
        System.out.println("Customers waiting  : " + wCount);
        System.out.println("Server idle ticks  : " + ticksIdle + " / " + totalTicks);
    }
}

//  Main
public class PriorityQApp {

    static void separator(String title) {
        System.out.println("\n" + "=".repeat(62));
        System.out.println("  " + title);
        System.out.println("=".repeat(62));
    }

    public static void main(String[] args) {

        // Display() – trace queue state 
        separator("Display() – trace queue operation");
        PriorityQueue pq = new PriorityQueue(7);
        System.out.println(">> Inserting: 30, 50, 10, 40, 20");
        pq.insert(30); pq.display();
        pq.insert(50); pq.display();
        pq.insert(10); pq.display();
        pq.insert(40); pq.display();
        pq.insert(20); pq.display();

        System.out.println("\n>> Removing min (highest priority) one by one:");
        while (!pq.isEmpty()) {
            long v = pq.remove();
            System.out.println("  Removed: " + v);
            pq.display();
        }

        // Insert at REAR – comparison with QueueApp
        separator("Efficiency: PriorityQueue vs OrdinaryQueue (QueueApp)");

        // Concrete timing demo
        System.out.println(">> Concrete insert-time demo (10 items each):");
        long[] items = {55, 12, 88, 3, 67, 34, 91, 7, 45, 23};

        PriorityQueue pqDemo = new PriorityQueue(10);
        OrdinaryQueue oqDemo = new OrdinaryQueue(10);

        long t1 = System.nanoTime();
        for (long v : items) pqDemo.insert(v);
        long pqTime = System.nanoTime() - t1;

        t1 = System.nanoTime();
        for (long v : items) oqDemo.insert(v);
        long oqTime = System.nanoTime() - t1;

        System.out.println("  PriorityQueue insert time : " + pqTime + " ns  (O(n) – shifts needed)");
        System.out.println("  OrdinaryQueue insert time : " + oqTime + " ns  (O(1) – no shifts)");

        System.out.print("\n  PriorityQueue remove order: ");
        while (!pqDemo.isEmpty()) System.out.print(pqDemo.remove() + " ");
        System.out.println("  ← sorted (highest priority first)");

        System.out.print("  OrdinaryQueue remove order: ");
        while (!oqDemo.isEmpty()) System.out.print(oqDemo.remove() + " ");
        System.out.println("  ← arrival order (FIFO)");

        // Simulation with PriorityQueue
        separator("Customer Simulation with PriorityQueue");

        System.out.println(">>> Scenario A: Small queue, fast arrivals");
        PrioritySimulation.run(3, 2, 4, 2, 30);

        System.out.println(">>> Scenario B: Large queue, slow arrivals");
        PrioritySimulation.run(10, 2, 4, 5, 30);

        System.out.println(">>> Scenario C: High service-time variance");
        PrioritySimulation.run(5, 1, 10, 3, 40);
    }
}