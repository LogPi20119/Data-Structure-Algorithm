package LinkQueueApp;

public class Customer {
    private final int id;
    private final long arrivalTimeMillis;
    private final long serviceTimeMillis; // how long the server will take to serve this customer

    public Customer(int id, long arrivalTimeMillis, long serviceTimeMillis) {
        this.id = id;
        this.arrivalTimeMillis = arrivalTimeMillis;
        this.serviceTimeMillis = serviceTimeMillis;
    }

    public int getId() {
        return id;
    }

    public long getArrivalTimeMillis() {
        return arrivalTimeMillis;
    }

    public long getServiceTimeMillis() {
        return serviceTimeMillis;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", arrival=" + arrivalTimeMillis + ", service=" + serviceTimeMillis + "ms}";
    }
}

