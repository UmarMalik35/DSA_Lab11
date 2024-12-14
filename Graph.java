/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Umar Malik
 */

import java.util.*;
public class Graph {
    private int[][] adjacencyMatrix;
    private int vertices;

    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyMatrix = new int[vertices][vertices];
    }

    // Task 1: Add an edge to the graph
    public void addEdge(int u, int v) {
        adjacencyMatrix[u - 1][v - 1] = 1;
    }

    // Task 2: Display the adjacency matrix
    public void displayMatrix() {
        System.out.println("Adjacency Matrix:");
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Task 3: Find the shortest path between two vertices using BFS
    public void findShortestPath(int start, int end) {
        boolean[] visited = new boolean[vertices];
        int[] distance = new int[vertices];
        int[] parent = new int[vertices];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start - 1);
        visited[start - 1] = true;
        distance[start - 1] = 0;
        parent[start - 1] = -1;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int i = 0; i < vertices; i++) {
                if (adjacencyMatrix[current][i] == 1 && !visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                    distance[i] = distance[current] + 1;
                    parent[i] = current;
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

    private void printPath(int[] parent, int vertex) {
        if (vertex == -1) return;
        printPath(parent, parent[vertex]);
        System.out.print((vertex + 1) + " ");
    }

    // Task 4: Find all paths between two vertices using DFS
    public void findAllPaths(int start, int end) {
        boolean[] visited = new boolean[vertices];
        List<Integer> path = new ArrayList<>();
        System.out.println("All Paths:");
        dfsFindPaths(start - 1, end - 1, visited, path);
    }

    private void dfsFindPaths(int current, int end, boolean[] visited, List<Integer> path) {
        visited[current] = true;
        path.add(current);

        if (current == end) {
            printPathList(path);
        } else {
            for (int i = 0; i < vertices; i++) {
                if (adjacencyMatrix[current][i] == 1 && !visited[i]) {
                    dfsFindPaths(i, end, visited, path);
                }
            }
        }

        path.remove(path.size() - 1);
        visited[current] = false;
    }

    private void printPathList(List<Integer> path) {
        for (int i = 0; i < path.size(); i++) {
            System.out.print((path.get(i) + 1) + (i == path.size() - 1 ? "" : " -> "));
        }
        System.out.println();
    }

    // Task 5: Detect connected components in an undirected graph
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
            if (adjacencyMatrix[current][i] == 1 && !visited[i]) {
                dfsFindComponent(i, visited, component);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of vertices and edges
        System.out.print("Enter the number of vertices: ");
        int vertices = scanner.nextInt();
        Graph graph = new Graph(vertices);

        System.out.print("Enter the number of edges: ");
        int edges = scanner.nextInt();
        System.out.println("Enter the edges (u v):");
        for (int i = 0; i < edges; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            graph.addEdge(u, v);
        }

        // Task 2: Display adjacency matrix
        graph.displayMatrix();

        // Task 3: Find shortest path
        System.out.print("Enter start and end vertices for shortest path: ");
        int start = scanner.nextInt();
        int end = scanner.nextInt();
        graph.findShortestPath(start, end);

        // Task 4: Find 
        System.out.print("Enter start and end vertices for ");
        start = scanner.nextInt();
        end = scanner.nextInt();
        graph.findAllPaths(start, end);

        // Task 5: 
        graph.findConnectedComponents();
    }

}
