package Questao01;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class UnionFind<TIPO> {
    private Map<Vertice<TIPO>, Vertice<TIPO>> parent;
    private Map<Vertice<TIPO>, Integer> rank;

    public UnionFind(ArrayList<Vertice<TIPO>> vertices) {
        parent = new HashMap<>();
        rank = new HashMap<>();
        for (Vertice<TIPO> vertice : vertices) {
            parent.put(vertice, vertice);
            rank.put(vertice, 0);
        }
    }

    public Vertice<TIPO> find(Vertice<TIPO> vertice) {
        if (!parent.get(vertice).equals(vertice)) {
            parent.put(vertice, find(parent.get(vertice)));
        }
        return parent.get(vertice);
    }

    public void union(Vertice<TIPO> vertice1, Vertice<TIPO> vertice2) {
        Vertice<TIPO> root1 = find(vertice1);
        Vertice<TIPO> root2 = find(vertice2);

        if (!root1.equals(root2)) {
            if (rank.get(root1) > rank.get(root2)) {
                parent.put(root2, root1);
            } else if (rank.get(root1) < rank.get(root2)) {
                parent.put(root1, root2);
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank.get(root1) + 1);
            }
        }
    }

    public boolean connected(Vertice<TIPO> vertice1, Vertice<TIPO> vertice2) {
        return find(vertice1).equals(find(vertice2));
    }
}
