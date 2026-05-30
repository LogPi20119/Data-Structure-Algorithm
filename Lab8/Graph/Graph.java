import java.util.*;

public class Graph {
    private Map<Node, List<Edge>> adjacencyList;
    private List<Node> nodes;

    public Graph() {
        adjacencyList = new LinkedHashMap<>();
        nodes         = new ArrayList<>();
    }

    // Add node into graph
    public Node addNode(String name) {
        Node node = new Node(name);
        nodes.add(node);
        adjacencyList.put(node, new ArrayList<>());
        return node;
    }

    // Add undirected edge
    public void addEdge(Node u, Node v, int weight) {
        adjacencyList.get(u).add(new Edge(u, v, weight));
        adjacencyList.get(v).add(new Edge(v, u, weight));
    }

    public List<Edge> getNeighbors(Node node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }

    public List<Node> getAllNodes() {
        return nodes;
    }

    // Find Node by name
    public Node getNode(String name) {
        for (Node n : nodes)
            if (n.name.equals(name)) return n;
        return null;
    }

    // TASK 4 — DFS: Count and list all paths from start to end.

    // allPaths Save the results: each element is a line (List<Node>)
    private List<List<Node>> allPaths;

    public List<List<Node>> findAllPaths(Node start, Node end) {
        allPaths = new ArrayList<>();
        boolean[] visited = new boolean[nodes.size()];
        dfs(start, end, new ArrayList<>(), visited);
        return allPaths;
    }

    private void dfs(Node current, Node end,
                     List<Node> currentPath, boolean[] visited) {
        int idx = nodes.indexOf(current);
        visited[idx] = true;
        currentPath.add(current);

        if (current == end) {
            allPaths.add(new ArrayList<>(currentPath));
        } else {
            for (Edge edge : getNeighbors(current)) {
                int neighborIdx = nodes.indexOf(edge.destination);
                if (!visited[neighborIdx]) {
                    dfs(edge.destination, end, currentPath, visited);
                }
            }
        }

        // backtrack
        currentPath.remove(currentPath.size() - 1);
        visited[idx] = false;
    }

    // Calculate the weighted sum of a line
    public int pathCost(List<Node> path) {
        int cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Node u = path.get(i);
            Node v = path.get(i + 1);
            for (Edge e : getNeighbors(u)) {
                if (e.destination == v) {
                    cost += e.weight;
                    break;
                }
            }
        }
        return cost;
    }

    // Print the line as "A -> B -> C (cost=25)"
    public String pathToString(List<Node> path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i).name);
            if (i < path.size() - 1) sb.append(" -> ");
        }
        sb.append(" (cost=").append(pathCost(path)).append(")");
        return sb.toString();
    }

    // TASK 5 — Dijkstra: shortest path from source to target
    public void dijkstra(Node source, Node target) {
        Map<Node, Integer>  dist   = new HashMap<>();
        Map<Node, Node>     prev   = new HashMap<>();
        PriorityQueue<Node> pq     = new PriorityQueue<>(
                Comparator.comparingInt(n -> dist.getOrDefault(n, Integer.MAX_VALUE)));

        // Initialize the distance.
        for (Node n : nodes) dist.put(n, Integer.MAX_VALUE);
        dist.put(source, 0);
        pq.add(source);

        System.out.println("--- Dijkstra: " + source + " -> " + target + " ---");

        while (!pq.isEmpty()) {
            Node u = pq.poll();

            if (u == target) break; // Find the way to the destination

            for (Edge edge : getNeighbors(u)) {
                Node   v      = edge.destination;
                int    newDist = dist.get(u) + edge.weight;

                if (newDist < dist.get(v)) {
                    dist.put(v, newDist);
                    prev.put(v, u);
                    pq.remove(v); // Update priorities
                    pq.add(v);
                }
            }
        }

        // Reconstruct path
        List<Node> path = new ArrayList<>();
        Node step = target;
        while (step != null) {
            path.add(0, step);
            step = prev.get(step);
        }

        if (path.get(0) != source) {
            System.out.println("No path found.");
        } else {
            System.out.println("Shortest path : " + pathToString(path));
            System.out.println("Total distance: " + dist.get(target));
        }
        System.out.println();
    }
}