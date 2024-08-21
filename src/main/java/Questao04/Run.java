
package Questao04;

import Questao01.Grafo;
import Questao01.Lista;
import Questao01.Vertice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import Questao01.Grafo;
import Questao01.Vertice;

import java.util.*;

public class Run {
    public static void main(String[] args) {
        Grafo<String> grafo = new Grafo<>();

        // Exemplo de grafo bipartido
        grafo.adicionarVertices("A");
        grafo.adicionarVertices("B");
        grafo.adicionarVertices("C");
        grafo.adicionarVertices("D");
        grafo.adicionarVertices("E");

        grafo.adicionarAresta("A", "B");
        grafo.adicionarAresta("A", "D");
        grafo.adicionarAresta("B", "C");
        grafo.adicionarAresta("C", "D");
        grafo.adicionarAresta("D", "E");

        grafo.imprimirParticoes();
    }
}
