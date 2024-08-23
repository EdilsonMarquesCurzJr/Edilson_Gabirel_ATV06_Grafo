package Questao02;

import Questao01.Grafo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Run {
    public static void main(String[] args) {
        // Criação do grafo
        Grafo<String> grafo = new Grafo<>();

        // Conjunto para armazenar os vértices já adicionados
        Set<String> verticesAdicionados = new HashSet<>();

        // Ler o arquivo e adicionar vértices e arestas
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/entrada/dados_q2.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] vertices = linha.split(";");
                String vertice1 = vertices[0];
                String vertice2 = vertices[1];

                // Adiciona vértice1 ao grafo apenas se ainda não foi adicionado
                if (!verticesAdicionados.contains(vertice1)) {
                    grafo.adicionarVertice(vertice1);
                    verticesAdicionados.add(vertice1);
                }

                // Adiciona vértice2 ao grafo apenas se ainda não foi adicionado
                if (!verticesAdicionados.contains(vertice2)) {
                    grafo.adicionarVertice(vertice2);
                    verticesAdicionados.add(vertice2);
                }

                // Adiciona a aresta entre os vértices
                grafo.adicionarAresta(1.0, vertice1, vertice2, true);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

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
