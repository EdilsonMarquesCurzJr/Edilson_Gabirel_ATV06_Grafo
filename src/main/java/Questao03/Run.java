
package Questao03;

import Questao01.Grafo;
import Questao01.Vertice;

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
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/entrada/dados_q3.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] vertices = linha.split(";");
                String vertice1 = vertices[0];
                String vertice2 = vertices[1];
                double peso = Double.parseDouble(vertices[2]);

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
                grafo.adicionarAresta(peso, vertice1, vertice2, true);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        // Encontrar e imprimir vértices raízes
        Set<Vertice<String>> verticesRaizes = grafo.encontrarVerticesRaizes();
        if (verticesRaizes.isEmpty()) {
            System.out.println("Nenhum vértice raiz encontrado.");
        } else {
            System.out.println("Vértices raízes encontrados:");
            for (Vertice<String> vertice : verticesRaizes) {
                System.out.println(vertice.getDado());
            }
        }
    }
}
