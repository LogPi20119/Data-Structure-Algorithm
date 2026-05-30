import java.util.*;

public class MapApp {
    public static void main(String[] args) {

        // TASK 3 — Build graph Figure 1
        Graph graph = new Graph();

        Node A = graph.addNode("A");
        Node B = graph.addNode("B");
        Node C = graph.addNode("C");
        Node D = graph.addNode("D");
        Node E = graph.addNode("E");
        Node F = graph.addNode("F");
        Node G = graph.addNode("G");
        Node H = graph.addNode("H");
        Node I = graph.addNode("I");
        Node J = graph.addNode("J");
        Node K = graph.addNode("K");
        Node L = graph.addNode("L");
        Node two = graph.addNode("2"); // node "2" in Figure 1

        // Add all edges according to figure 1.
        graph.addEdge(A,   B,   6);
        graph.addEdge(A,   two, 10);
        graph.addEdge(B,   C,   11);
        graph.addEdge(B,   D,   14);
        graph.addEdge(C,   two, 12);
        graph.addEdge(C,   F,   3);
        graph.addEdge(C,   E,   6);
        graph.addEdge(D,   E,   4);
        graph.addEdge(D,   H,   6);
        graph.addEdge(D,   K,   15);
        graph.addEdge(E,   H,   12);
        graph.addEdge(E,   I,   16);
        graph.addEdge(F,   two, 8);
        graph.addEdge(F,   I,   6);
        graph.addEdge(G,   two, 16);
        graph.addEdge(G,   I,   8);
        graph.addEdge(H,   K,   12);
        graph.addEdge(H,   I,   13);
        graph.addEdge(H,   J,   18);
        graph.addEdge(I,   L,   17);
        graph.addEdge(J,   L,   20);
        graph.addEdge(K,   J,   9);

        System.out.println("Graph built successfully with "
                + graph.getAllNodes().size() + " nodes.\n");

        // TASK 4 — DFS: Find all paths from A to K
        System.out.println("---TASK 4: All paths from A to K---");

        List<List<Node>> paths = graph.findAllPaths(A, K);
        System.out.println("Total number of paths: " + paths.size() + "\n");

        // Find the path with the fewest nodes and the most nodes.
        List<Node> shortest = null;
        List<Node> longest  = null;

        for (List<Node> path : paths) {
            System.out.println(graph.pathToString(path));
            if (shortest == null || path.size() < shortest.size()) shortest = path;
            if (longest  == null || path.size() > longest.size())  longest  = path;
        }

        System.out.println("\n--- Path with fewest nodes ---");
        System.out.println(graph.pathToString(shortest));

        System.out.println("\n--- Path with most nodes ---");
        System.out.println(graph.pathToString(longest));

        // TASK 5 — Dijkstra
        System.out.println("---TASK 5: Dijkstra shortest paths---");

        graph.dijkstra(A, J); // A -> J
        graph.dijkstra(B, L); // B -> L
    }
}