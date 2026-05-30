import java.util.Random;

class Queue {
    private int maxSize;
    private long[] queArray;
    private int front;
    private int rear;
    private int nItems;

    public Queue(int s) {
        maxSize  = s;
        queArray = new long[maxSize];
        front    = 0;
        rear     = -1;
        nItems   = 0;
    }

    // Display the raw array + front/rear indices 
    public void displayArray() {
        System.out.print("Array  : [");
        for (int i = 0; i < maxSize; i++) {
            System.out.print(queArray[i]);
            if (i < maxSize - 1) System.out.print(", ");
        }
        System.out.println("]");
        System.out.println("front  = " + front + "  |  rear = " + rear +
                           "  |  nItems = " + nItems);
        System.out.println("(wraparound: rear wraps from index " +
                           (maxSize - 1) + " back to 0)");
    }

    // Display the queue in logical order (wraparound)
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("Queue is EMPTY.");
            return;
        }
        System.out.print("Queue (front→rear): [");
        int tempFront = front;
        for (int i = 1; i <= nItems; i++) {
            System.out.print(queArray[tempFront]);
            if (i < nItems) System.out.print(", ");
            tempFront = (tempFront + 1) % maxSize;   // wraparound
        }
        System.out.println("]");
    }

    // Handy combined display
    public void display() {
        displayArray();
        displayQueue();
        System.out.println();
    }

    // Extended insert – handles full queue
    public boolean insert(long j) {
        if (isFull()) {
            System.out.println("  [!] INSERT failed – queue is FULL (maxSize=" + maxSize + ")");
            return false;
        }
        rear = (rear + 1) % maxSize;   // wraparound
        queArray[rear] = j;
        nItems++;
        return true;
    }

    // Extended remove – handles empty queue
    public long remove() {
        if (isEmpty()) {
            System.out.println("  [!] REMOVE failed – queue is EMPTY");
            return -1;
        }
        long temp = queArray[front];
        front = (front + 1) % maxSize; // wraparound
        nItems--;
        return temp;
    }

    public long peekFront() { return queArray[front]; }

    public boolean isEmpty() { return nItems == 0; }
    public boolean isFull()  { return nItems == maxSize; }
    public int     size()    { return nItems; }

    // Processing-time queue
    private int removeCallCount = 0;
    public long removeAfterN(int n) {
        removeCallCount++;
        System.out.println("  removeAfterN call #" + removeCallCount +
                           " (will remove after " + n + " calls)");
        if (removeCallCount >= n) {
            removeCallCount = 0;
            if (!isEmpty()) {
                long val = remove();
                System.out.println("  → Removed: " + val);
                return val;
            }
        }
        return -1;   // nothing removed yet
    }
}

//  Customer for simulation
class Customer {
    private int id;
    private int serviceTime;   // random time units to serve this customer
    private int timeLeft;

    public Customer(int id, int minService, int maxService, Random rnd) {
        this.id          = id;
        this.serviceTime = minService + rnd.nextInt(maxService - minService + 1);
        this.timeLeft    = serviceTime;
    }

    /** @return true when service is complete */
    public boolean serve() {
        timeLeft--;
        return timeLeft <= 0;
    }

    public int getId()          { return id; }
    public int getServiceTime() { return serviceTime; }
}

//  Customer Queue Simulation
class CustomerSimulation {

    /**
     * @param queueSize   capacity of the queue
     * @param minService  minimum service time per customer
     * @param maxService  maximum service time per customer
     * @param arrivalRate average time units between arrivals
     *                    (1 = every tick, 5 = every 5 ticks on average)
     * @param totalTicks  simulation duration
     */
    public static void run(int queueSize, int minService, int maxService,
                           int arrivalRate, int totalTicks) {
        System.out.println("---CUSTOMER QUEUE SIMULATION---");
        System.out.printf ("Queue size   : %-26d║%n", queueSize);
        System.out.printf ("Service time : %d – %-22d║%n", minService, maxService);
        System.out.printf ("Arrival rate : 1 per ~%-19d║%n", arrivalRate);
        System.out.printf ("Duration     : %-26d║%n", totalTicks);

        Queue     queue     = new Queue(queueSize);
        Random    rnd       = new Random(42);
        Customer  current   = null;   // customer being served

        int customerId  = 0;
        int served      = 0;
        int dropped     = 0;   // arrived but queue was full
        int ticksIdle   = 0;

        for (int tick = 1; tick <= totalTicks; tick++) {

            // ── Arrival ──────────────────────────────────────────────────
            // A customer arrives if a random value beats the arrival rate.
            if (rnd.nextInt(arrivalRate) == 0) {
                customerId++;
                Customer c = new Customer(customerId, minService, maxService, rnd);
                if (!queue.insert(customerId)) {
                    dropped++;
                    System.out.printf("[t=%3d] Customer %d arrived – QUEUE FULL, turned away%n",
                                      tick, customerId);
                } else {
                    System.out.printf("[t=%3d] Customer %d arrived (service=%d) – queued (size=%d)%n",
                                      tick, customerId, c.getServiceTime(), queue.size());
                    // store full Customer object — rewrite to parallel array for simplicity
                }
            }

            // Service
            if (current == null && !queue.isEmpty()) {
                long cid = queue.remove();
                current = new Customer((int) cid, minService, maxService, rnd);
                System.out.printf("[t=%3d] Server starts Customer %d (will take ~%d ticks)%n",
                                  tick, current.getId(), current.getServiceTime());
            }

            if (current != null) {
                boolean done = current.serve();
                if (done) {
                    System.out.printf("[t=%3d] Customer %d finished service.%n",
                                      tick, current.getId());
                    served++;
                    current = null;
                }
            } else {
                ticksIdle++;
            }
        }

        // Summary
        System.out.println("\n---SIMULATION SUMMARY---");
        System.out.println("Customers arrived  : " + customerId);
        System.out.println("Customers served   : " + served);
        System.out.println("Customers dropped  : " + dropped + " (queue full)");
        System.out.println("Customers waiting  : " + queue.size());
        System.out.println("Server idle ticks  : " + ticksIdle + " / " + totalTicks);
        System.out.println();
    }
}

//Main
public class QueueApp {

    public static void main(String[] args) {

        separator("3.4 ①②③  Display array, queue, front/rear (wraparound demo)");
        Queue q = new Queue(5);
        q.insert(10); q.insert(20); q.insert(30); q.insert(40); q.insert(50);
        q.display();

        // Remove two items then add two more → triggers wraparound
        System.out.println("Remove 2 items, then insert 60 and 70 (wraparound occurs):");
        q.remove(); q.remove();
        q.insert(60); q.insert(70);
        q.display();

        // Test empty / full edge cases
        separator("3.4 ④  Empty / full edge cases");
        Queue q2 = new Queue(3);
        System.out.println("-- Try to remove from empty queue:");
        q2.remove();

        q2.insert(1); q2.insert(2); q2.insert(3);
        System.out.println("-- Try to insert into full queue:");
        q2.insert(4);
        q2.display();

        System.out.println("-- Remove all items then try again:");
        q2.remove(); q2.remove(); q2.remove();
        q2.remove();   // empty!

        // Extended insert/remove already shown above

        // removeAfterN
        separator("3.4 ⑥  Processing time – removeAfterN(3)");
        Queue q3 = new Queue(5);
        q3.insert(100); q3.insert(200); q3.insert(300);
        q3.displayQueue();
        System.out.println("Calling removeAfterN(3) five times:");
        for (int i = 0; i < 5; i++) q3.removeAfterN(3);
        q3.displayQueue();

        // Customer simulation
        separator("3.4 ⑦  Customer Simulation – investigating parameters");

        System.out.println(">>> Scenario A: Small queue, fast arrivals (expect many drops)");
        CustomerSimulation.run(3, 2, 4, 2, 30);

        System.out.println(">>> Scenario B: Large queue, slow arrivals (expect low wait)");
        CustomerSimulation.run(10, 2, 4, 5, 30);

        System.out.println(">>> Scenario C: Large service range (high variance)");
        CustomerSimulation.run(5, 1, 10, 3, 40);
    }

    private static void separator(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  " + title);
        System.out.println("=".repeat(60));
    }
}