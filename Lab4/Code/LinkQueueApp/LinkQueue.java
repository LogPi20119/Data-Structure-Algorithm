package LinkQueueApp;

public class LinkQueue {
    private QueueNode head;
    private QueueNode tail;
    private int size = 0;

    // For the special remove behavior
    private int removeCallCount = 0;

    public LinkQueue() {}

    // Enqueue at tail
    public synchronized void enqueue(Customer c) {
        QueueNode node = new QueueNode(c);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
        notifyAll();
    }

    // Standard dequeue (remove head)
    public synchronized Customer dequeue() {
        if (head == null) return null;
        Customer c = head.customer;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return c;
    }

    // Size method
    public synchronized int size() {
        return size;
    }

    // Peek head without removing
    public synchronized Customer peek() {
        return head == null ? null : head.customer;
    }

    /**
     * Special remove: each call increments an internal counter N.
     * This call removes the item at position N (1-based) from the queue if present.
     * Returns the removed Customer or null if N > size.
     *
     * Example:
     *  - first call removes position 1 (head)
     *  - second call removes position 2 (in the current queue at time of call)
     */
    public synchronized Customer removeNthByCall() {
        removeCallCount++;
        int target = removeCallCount;
        if (target <= 0) return null; // defensive
        if (target > size) {
            return null; // nothing to remove at that position
        }

        // remove head if target == 1
        if (target == 1) {
            return dequeue();
        }

        // find node before target
        QueueNode prev = head;
        for (int i = 1; i < target - 1; i++) {
            prev = prev.next;
        }
        QueueNode toRemove = prev.next;
        prev.next = toRemove.next;
        if (toRemove == tail) {
            tail = prev;
        }
        size--;
        return toRemove.customer;
    }

    // Wait until queue not empty and then dequeue (used by server thread)
    public synchronized Customer waitAndDequeue() throws InterruptedException {
        while (size == 0) {
            wait();
        }
        return dequeue();
    }

    // For debugging: print queue snapshot (ids)
    public synchronized String snapshot() {
        StringBuilder sb = new StringBuilder();
        QueueNode cur = head;
        while (cur != null) {
            sb.append(cur.customer.getId());
            if (cur.next != null) sb.append(" -> ");
            cur = cur.next;
        }
        return sb.toString();
    }
}
