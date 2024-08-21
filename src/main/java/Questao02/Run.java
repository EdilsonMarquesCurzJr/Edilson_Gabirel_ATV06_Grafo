package Questao02;

import Questao01.Grafo;


public class Run {
    public static void main(String[] args) {
        // Criação do grafo
        Grafo<String> grafo = new Grafo<>();

        // Adiciona vértices
        grafo.adicionarVertice("A");
        grafo.adicionarVertice("B");
        grafo.adicionarVertice("C");
        grafo.adicionarVertice("D");
        grafo.adicionarVertice("E");

        // Adiciona arestas
        grafo.adicionarAresta(1.0, "A", "B", true);
        grafo.adicionarAresta(1.0, "A", "C", true);
        grafo.adicionarAresta(1.0, "B", "D", true);
        grafo.adicionarAresta(1.0, "C", "D", true);
        grafo.adicionarAresta(1.0, "D", "E", true);

        // Executa a busca em profundidade e imprime os tempos
        System.out.println("Executando busca em profundidade a partir do vértice 'A':");
        grafo.buscaEmProfundidadeComTempos("A");

        // Exemplo de outras operações
        System.out.println("\nLista de vértices:");
        grafo.imprimirVertices();

        System.out.println("\nAdjacentes:");
        grafo.imprimirAdjacentes();
    }
}
