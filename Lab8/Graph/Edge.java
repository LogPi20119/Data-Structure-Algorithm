public class Edge {
    public Node source;
    public Node destination;
    public int weight;

    public Edge(Node source, Node destination, int weight) {
        this.source      = source;
        this.destination = destination;
        this.weight      = weight;
    }
}