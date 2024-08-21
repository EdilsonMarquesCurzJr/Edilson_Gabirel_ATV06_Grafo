package Questao06;

import java.util.*;

public class Questao06 {
    public static void main(String[] args) {
        // Exemplo de grafo com 5 vértices
        int V = 5;
        List<TSPFromMST.Edge> edges = Arrays.asList(
                new TSPFromMST.Edge(0, 1, 2),
                new TSPFromMST.Edge(0, 2, 3),
                new TSPFromMST.Edge(1, 2, 1),
                new TSPFromMST.Edge(1, 3, 4),
                new TSPFromMST.Edge(2, 3, 5),
                new TSPFromMST.Edge(3, 4, 6)
        );

        // Encontrar a MST usando o Algoritmo de Prim
        List<TSPFromMST.Edge> mst = primMST(V, edges);

        // Construir o ciclo a partir da MST
        List<Integer> cycle = constructCycle(mst, V);

        // Imprimir o ciclo
        System.out.println("Ciclo Mínimo Aproximado:");
        for (int i = 0; i < cycle.size(); i++) {
            System.out.print(cycle.get(i) + " ");
            if (i < cycle.size() - 1) {
                System.out.print("-> ");
            }
        }
        System.out.println();
    }

    // Algoritmo de Prim para encontrar a MST
    public static List<TSPFromMST.Edge> primMST(int V, List<TSPFromMST.Edge> edges) {
        List<TSPFromMST.Edge> mst = new ArrayList<>();
        boolean[] inMST = new boolean[V];
        PriorityQueue<TSPFromMST.Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        pq.add(new TSPFromMST.Edge(-1, 0, 0)); // Começar com o vértice 0

        while (!pq.isEmpty()) {
            TSPFromMST.Edge edge = pq.poll();
            int u = edge.src;
            int v = edge.dest;
            int weight = edge.weight;

            if (inMST[v]) continue; // Verificar se o vértice já está na MST

            inMST[v] = true;
            if (u != -1) {
                mst.add(edge);
            }

            for (TSPFromMST.Edge e : edges) {
                if (e.src == v && !inMST[e.dest]) {
                    pq.add(new TSPFromMST.Edge(v, e.dest, e.weight));
                } else if (e.dest == v && !inMST[e.src]) {
                    pq.add(new TSPFromMST.Edge(v, e.src, e.weight));
                }
            }
        }
        return mst;
    }

    // Construir o ciclo a partir da MST
    public static List<Integer> constructCycle(List<TSPFromMST.Edge> mst, int V) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (TSPFromMST.Edge e : mst) {
            graph.computeIfAbsent(e.src, k -> new ArrayList<>()).add(e.dest);
            graph.computeIfAbsent(e.dest, k -> new ArrayList<>()).add(e.src);
        }

        List<Integer> cycle = new ArrayList<>();
        boolean[] visited = new boolean[V];
        dfs(0, graph, visited, cycle);
        cycle.add(cycle.get(0)); // Fechar o ciclo

        return cycle;
    }

    // DFS para construir o ciclo
    public static void dfs(int v, Map<Integer, List<Integer>> graph, boolean[] visited, List<Integer> cycle) {
        visited[v] = true;
        cycle.add(v);

        for (int neighbor : graph.get(v)) {
            if (!visited[neighbor]) {
                dfs(neighbor, graph, visited, cycle);
            }
        }
    }
}
