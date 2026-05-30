package LinkQueueApp;

public class QueueNode {
    Customer customer;
    QueueNode next;

    public QueueNode(Customer customer) {
        this.customer = customer;
    }
}
