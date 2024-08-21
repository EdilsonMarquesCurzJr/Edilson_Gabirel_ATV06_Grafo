
package Questao05;

import Questao01.Grafo;



public class Run{
    public static void main(String[] args) {
        Grafo<String> grafo = new Grafo<>();
        grafo.adicionarVertice("A");
        grafo.adicionarVertice("B");
        grafo.adicionarVertice("C");
        grafo.adicionarVertice("D");

        grafo.adicionarAresta(1.0, "A", "B", false);
        grafo.adicionarAresta(4.0, "B", "C", false);
        grafo.adicionarAresta(3.0, "C", "D", false);
        grafo.adicionarAresta(2.0, "A", "D", false);

        grafo.boruvkaMST();
    }

}