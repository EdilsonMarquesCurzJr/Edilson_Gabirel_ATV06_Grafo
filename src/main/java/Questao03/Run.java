
package Questao03;

import Questao01.Grafo;
import Questao01.Vertice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class Run {
    public static void main(String[] args) {
        // Criação do grafo
        Grafo<Integer> grafo = new Grafo<>();

        // Adiciona vértices
        grafo.adicionarVertice(1);
        grafo.adicionarVertice(2);
        grafo.adicionarVertice(3);
        grafo.adicionarVertice(4);
        grafo.adicionarVertice(5);

        // Adiciona arestas
        grafo.adicionarAresta(1.0, 1, 2, true);
        grafo.adicionarAresta(1.0, 1, 3, true);
        grafo.adicionarAresta(1.0, 2, 4, true);
        grafo.adicionarAresta(1.0, 3, 4, true);
        grafo.adicionarAresta(1.0, 4, 5, true);

        // Encontrar e imprimir vértices raízes
        Set<Vertice<Integer>> verticesRaizes = grafo.encontrarVerticesRaizes();
        if (verticesRaizes.isEmpty()) {
            System.out.println("Nenhum vértice raiz encontrado.");
        } else {
            System.out.println("Vértices raízes encontrados:");
            for (Vertice<Integer> vertice : verticesRaizes) {
                System.out.println(vertice.getDado());
            }
        }
    }
}
