package LinkQueueApp;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class LinkQueueApp {

    // Simulation parameters (tweak to investigate behavior)
    private static final int SIMULATION_SECONDS = 20;
    private static final double MEAN_INTERARRIVAL_MS = 500.0; // average time between arrivals (ms)
    private static final double MEAN_SERVICE_MS = 700.0; // average service time per customer (ms)
    private static final int MAX_CUSTOMERS_TO_GENERATE = 1000;

    public static void main(String[] args) throws InterruptedException {
        LinkQueue queue = new LinkQueue();
        Random rng = new Random();

        // Statistics
        AtomicLong totalWaitMillis = new AtomicLong(0);
        AtomicLong servedCount = new AtomicLong(0);
        AtomicLong maxQueueSize = new AtomicLong(0);

        AtomicBoolean producing = new AtomicBoolean(true);

        // Arrival thread (producer)
        Thread producer = new Thread(() -> {
            int id = 1;
            long start = System.currentTimeMillis();
            while (producing.get() && id <= MAX_CUSTOMERS_TO_GENERATE) {
                long now = System.currentTimeMillis();
                // sample interarrival time using exponential distribution with mean MEAN_INTERARRIVAL_MS
                double u = rng.nextDouble();
                double interArrival = -Math.log(1 - u) * MEAN_INTERARRIVAL_MS;
                try {
                    Thread.sleep((long) interArrival);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                long arrivalTime = System.currentTimeMillis();
                // sample service time (exponential with mean MEAN_SERVICE_MS)
                double v = rng.nextDouble();
                long serviceTime = Math.max(1, (long) (-Math.log(1 - v) * MEAN_SERVICE_MS));
                Customer c = new Customer(id++, arrivalTime, serviceTime);
                queue.enqueue(c);
                long qsize = queue.size();
                maxQueueSize.updateAndGet(prev -> Math.max(prev, qsize));
                // stop producing after simulation time
                if (System.currentTimeMillis() - start > SIMULATION_SECONDS * 1000L) {
                    producing.set(false);
                    break;
                }
            }
            producing.set(false);
        }, "Producer");

        // Server thread (consumer)
        Thread server = new Thread(() -> {
            while (producing.get() || queue.size() > 0) {
                try {
                    Customer c = queue.waitAndDequeue();
                    if (c == null) continue;
                    long wait = System.currentTimeMillis() - c.getArrivalTimeMillis();
                    totalWaitMillis.addAndGet(wait);
                    servedCount.incrementAndGet();
                    // simulate service
                    try {
                        Thread.sleep(c.getServiceTimeMillis());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Server");

        // Start simulation
        System.out.println("Starting simulation: " + SIMULATION_SECONDS + "s, mean interarrival(ms)="
                + MEAN_INTERARRIVAL_MS + ", mean service(ms)=" + MEAN_SERVICE_MS);
        producer.start();
        server.start();

        // Optionally: demonstrate removeNthByCall() being called periodically
        Thread removerDemo = new Thread(() -> {
            // call removeNthByCall every 2 seconds to show special removal behavior
            int calls = 0;
            while (producing.get() || queue.size() > 0) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                calls++;
                Customer removed = queue.removeNthByCall();
                System.out.println("[RemoverDemo] call #" + calls + " removed: " + (removed == null ? "none" : removed));
            }
        }, "RemoverDemo");
        removerDemo.setDaemon(true);
        removerDemo.start();

        // Wait for producer and server to finish
        producer.join();
        server.join();

        // Print statistics
        long served = servedCount.get();
        double avgWait = served == 0 ? 0.0 : (double) totalWaitMillis.get() / served;
        System.out.println("Simulation finished.");
        System.out.println("Total served: " + served);
        System.out.printf("Average wait time: %.2f ms%n", avgWait);
        System.out.println("Max queue size observed: " + maxQueueSize.get());
        System.out.println("Final queue snapshot: " + queue.snapshot());
    }
}
