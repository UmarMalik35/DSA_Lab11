/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Umar Malik
 */
import java.util.*;
public class WeightedGraph {
 
    private int[][] adjacencyMatrix;
    private int vertices;

    public WeightedGraph(int vertices) {
        this.vertices = vertices;
        adjacencyMatrix = new int[vertices][vertices];
        for (int i = 0; i < vertices; i++) {
            Arrays.fill(adjacencyMatrix[i], Integer.MAX_VALUE); // Initialize with "infinity"
        }
    }

    
    
    // Task 1: Add a weighted edge to the graph
    public void addEdge(int u, int v, int weight) {
        adjacencyMatrix[u - 1][v - 1] = weight;
    }

    // Task 2: Display the weighted adjacency matrix
    public void displayMatrix() {
        System.out.println("Weighted Adjacency Matrix:");
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                System.out.print((adjacencyMatrix[i][j] == Integer.MAX_VALUE ? "∞" : adjacencyMatrix[i][j]) + " ");
            }
            System.out.println();
        }
    }

    
    
    // Task 3: Find the shortest path using Dijkstra's algorithm
    public void findShortestPath(int start, int end) {
        boolean[] visited = new boolean[vertices];
        int[] distance = new int[vertices];
        int[] parent = new int[vertices];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        distance[start - 1] = 0;

        for (int count = 0; count < vertices - 1; count++) {
            int u = getMinDistanceVertex(distance, visited);
            if (u == -1) break;
            visited[u] = true;

            for (int v = 0; v < vertices; v++) {
                if (!visited[v] && adjacencyMatrix[u][v] != Integer.MAX_VALUE &&
                        distance[u] + adjacencyMatrix[u][v] < distance[v]) {
                    distance[v] = distance[u] + adjacencyMatrix[u][v];
                    parent[v] = u;
                }
            }
        }

        if (distance[end - 1] == Integer.MAX_VALUE) {
            System.out.println("No path exists.");
        } else {
            System.out.println("Shortest Path Length: " + distance[end - 1]);
            System.out.print("Path: ");
            printPath(parent, end - 1);
            System.out.println();
        }
    }

    private int getMinDistanceVertex(int[] distance, boolean[] visited) {
        int minDistance = Integer.MAX_VALUE, minIndex = -1;
        for (int i = 0; i < vertices; i++) {
            if (!visited[i] && distance[i] < minDistance) {
                minDistance = distance[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private void printPath(int[] parent, int vertex) {
        if (vertex == -1) return;
        printPath(parent, parent[vertex]);
        System.out.print((vertex + 1) + " ");
    }

    
    // Task 4: Find all paths using DFS
    public void findAllPaths(int start, int end) {
        boolean[] visited = new boolean[vertices];
        List<Integer> path = new ArrayList<>();
        System.out.println("All Paths:");
        dfsFindPaths(start - 1, end - 1, visited, path, 0);
    }

    private void dfsFindPaths(int current, int end, boolean[] visited, List<Integer> path, int currentWeight) {
        visited[current] = true;
        path.add(current);

        if (current == end) {
            printPathList(path, currentWeight);
        } else {
            for (int i = 0; i < vertices; i++) {
                if (adjacencyMatrix[current][i] != Integer.MAX_VALUE && !visited[i]) {
                    dfsFindPaths(i, end, visited, path, currentWeight + adjacencyMatrix[current][i]);
                }
            }
        }

        path.remove(path.size() - 1);
        visited[current] = false;
    }

    private void printPathList(List<Integer> path, int weight) {
        for (int i = 0; i < path.size(); i++) {
            System.out.print((path.get(i) + 1) + (i == path.size() - 1 ? "" : " -> "));
        }
        System.out.println(" (Weight: " + weight + ")");
    }

    
    
    // Task 5: Detect connected components for weighted undirected graph
    public void findConnectedComponents() {
        boolean[] visited = new boolean[vertices];
        List<List<Integer>> components = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                dfsFindComponent(i, visited, component);
                components.add(component);
            }
        }

        System.out.println("Connected Components:");
        for (int i = 0; i < components.size(); i++) {
            System.out.println("Component " + (i + 1) + ": " + components.get(i));
        }
    }

    private void dfsFindComponent(int current, boolean[] visited, List<Integer> component) {
        visited[current] = true;
        component.add(current + 1);
        for (int i = 0; i < vertices; i++) {
            if (adjacencyMatrix[current][i] != Integer.MAX_VALUE && !visited[i]) {
                dfsFindComponent(i, visited, component);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of vertices and edges
        System.out.print("Enter the number of vertices: ");
        int vertices = scanner.nextInt();
        WeightedGraph graph = new WeightedGraph(vertices);

        System.out.print("Enter the number of edges: ");
        int edges = scanner.nextInt();
        System.out.println("Enter the edges (u v weight):");
        for (int i = 0; i < edges; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int weight = scanner.nextInt();
            graph.addEdge(u, v, weight);
        }
        
        // Task 2: Display adjacency matrix
        graph.displayMatrix();
        
        // Task 3: Find shortest path
        System.out.print("Enter start and end vertices for shortest path: ");
        int start = scanner.nextInt();
        int end = scanner.nextInt();
        graph.findShortestPath(start, end);

        // Task 4: Find all paths
        System.out.print("Enter start and end vertices for all paths: ");
        start = scanner.nextInt();
        end = scanner.nextInt();
        graph.findAllPaths(start, end);
        
        // Task 5: Find connected components
        graph.findConnectedComponents();
    }    
}
