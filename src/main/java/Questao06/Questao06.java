package Questao06;

import Questao01.Grafo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Questao06 {
    public static void main(String[] args) {
        Grafo<String> grafo = new Grafo<>();

        // Conjunto para armazenar os vértices já adicionados
        Set<String> verticesAdicionados = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/entrada/dados_q6.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] vertices = linha.split(";");
                String vertice1 = vertices[0];
                String vertice2 = vertices[1];
                Double peso = Double.parseDouble(vertices[2]);

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
                grafo.adicionarAresta(peso, vertice1, vertice2, false);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        // Executa o algoritmo de Borůvka
        grafo.boruvkaMST06();
    }
}
