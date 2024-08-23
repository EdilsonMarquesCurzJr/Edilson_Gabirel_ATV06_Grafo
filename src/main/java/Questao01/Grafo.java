/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Questao01;

import java.util.*;

/**
 * @param <TIPO>
 */
public class Grafo<TIPO> {
    private final ArrayList<Vertice<TIPO>> vertices;
    private final ArrayList<Aresta<TIPO>> arestas;

    public Grafo() {
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();
    }

    public ArrayList<Vertice<TIPO>> getVertices() {
        return vertices;
    }

    public ArrayList<Aresta<TIPO>> getArestas() {
        return arestas;
    }

    public void adicionarVertice(TIPO dado) {
        Vertice<TIPO> novoVertice = new Vertice<>(dado);
        this.vertices.add(novoVertice);
    }

    public void adicionarAresta(Double peso, TIPO dadoInicio, TIPO dadoFim, boolean direcionado) {
        Vertice<TIPO> inicio = this.getVertice(dadoInicio);
        Vertice<TIPO> fim = this.getVertice(dadoFim);
        Aresta<TIPO> aresta = new Aresta<>(peso, inicio, fim);
        inicio.adicionarArestaSaida(aresta);
        fim.adicionarArestaEntrada(aresta);
        this.arestas.add(aresta);

        if (!direcionado) {
            // Se o grafo não é direcionado, adicionamos uma aresta na direção oposta
            Aresta<TIPO> arestaNaoDirecionada = new Aresta<>(peso, fim, inicio);
            inicio.adicionarArestaEntrada(arestaNaoDirecionada);
            fim.adicionarArestaSaida(arestaNaoDirecionada);
            this.arestas.add(arestaNaoDirecionada);
        }
    }

    public Vertice<TIPO> getVertice(TIPO dado) {
        Vertice<TIPO> vertice = null;
        for (int i = 0; i < this.vertices.size(); i++) {
            if (this.vertices.get(i).getDado().equals(dado)) {
                vertice = this.vertices.get(i);
                break;
            }
        }
        return vertice;
    }

    public void buscaEmProfundidadeComTempos(TIPO inicio) {
        // Inicializa os dados para a busca
        Vertice<TIPO> verticeInicio = getVertice(inicio);
        if (verticeInicio != null) {
            Stack<Vertice<TIPO>> caminho = new Stack<>();
            HashSet<Vertice<TIPO>> visitados = new HashSet<>();
            tempoAtual = 0; // Reseta o tempo

            // Inicia a busca em profundidade
            dfs(verticeInicio, null, caminho, visitados);

            // Imprime os tempos
            imprimirTempos();
        } else {
            System.out.println("Vértice de início não encontrado.");
        }
    }

    public Set<Vertice<TIPO>> encontrarVerticesRaizes() {
        Set<Vertice<TIPO>> verticesRaizes = new HashSet<>();

        for (Vertice<TIPO> vertice : vertices) {
            if (todosVerticesAtingidos(vertice)) {
                verticesRaizes.add(vertice);
            }
        }

        return verticesRaizes;
    }

    private boolean todosVerticesAtingidos(Vertice<TIPO> origem) {
        Set<Vertice<TIPO>> visitados = new HashSet<>();
        buscaEmProfundidade(origem, visitados);
        return visitados.size() == vertices.size();
    }

    private void buscaEmProfundidade(Vertice<TIPO> vertice, Set<Vertice<TIPO>> visitados) {
        visitados.add(vertice);

        for (Aresta<TIPO> aresta : vertice.getArestasSaida()) {
            Vertice<TIPO> proximo = aresta.getFim();
            if (!visitados.contains(proximo)) {
                buscaEmProfundidade(proximo, visitados);
            }
        }
    }

    public void buscaEmLargura() {
        ArrayList<Vertice<TIPO>> marcados = new ArrayList<>();
        ArrayList<Vertice<TIPO>> fila = new ArrayList<>();
        Vertice<TIPO> atual = this.vertices.get(0);
        marcados.add(atual);
        System.out.println(atual.getDado());
        fila.add(atual);
        while (!fila.isEmpty()) {
            Vertice<TIPO> visitado = fila.get(0);
            for (int i = 0; i < visitado.getArestasSaida().size(); i++) {
                Vertice<TIPO> proximo = visitado.getArestasSaida().get(i).getFim();
                if (!marcados.contains(proximo)) { //se o vértice ainda não foi marcado
                    marcados.add(proximo);
                    System.out.println(proximo.getDado());
                    fila.add(proximo);
                }
            }
            fila.remove(0);
        }
    }

    public void removerVertice(TIPO dado) {
        // Encontrar o vértice com o dado especificado
        Vertice<TIPO> verticeRemover = getVertice(dado);

        // Verificar se o vértice foi encontrado
        if (verticeRemover != null) {
            // Remover todas as arestas que envolvem o vértice a ser removido
            removerArestasDoVertice(verticeRemover);

            // Remover o vértice da lista de vértices
            this.vertices.remove(verticeRemover);
            System.out.println("Vértice " + dado + " removido.");
        } else {
            System.out.println("Vértice não encontrado: " + dado);
        }
    }

    private void removerArestasDoVertice(Vertice<TIPO> vertice) {
        // Remover todas as arestas de entrada
        for (Aresta<TIPO> arestaEntrada : vertice.getArestasEntrada()) {
            arestaEntrada.getInicio().getArestasSaida().remove(arestaEntrada);
            this.arestas.remove(arestaEntrada);
        }

        // Remover todas as arestas de saída
        for (Aresta<TIPO> arestaSaida : vertice.getArestasSaida()) {
            arestaSaida.getFim().getArestasEntrada().remove(arestaSaida);
            this.arestas.remove(arestaSaida);
        }
    }

    public void removerAresta(TIPO dadoInicio, TIPO dadoFim) {
        // Encontrar os vértices de início e fim da aresta a ser removida
        Vertice<TIPO> inicio = getVertice(dadoInicio);
        Vertice<TIPO> fim = getVertice(dadoFim);

        // Verificar se os vértices foram encontrados
        if (inicio != null && fim != null) {
            // Encontrar a aresta correspondente
            Aresta<TIPO> arestaRemover = null;
            for (Aresta<TIPO> aresta : arestas) {
                if (aresta.getInicio().equals(inicio) && aresta.getFim().equals(fim)) {
                    arestaRemover = aresta;
                    break;
                }
            }

            // Verificar se a aresta foi encontrada
            if (arestaRemover != null) {
                // Remover a aresta das listas de arestas dos vértices
                inicio.getArestasSaida().remove(arestaRemover);
                fim.getArestasEntrada().remove(arestaRemover);

                // Remover a aresta da lista de arestas do grafo
                this.arestas.remove(arestaRemover);
            } else {
                System.out.println("Aresta não encontrada entre " + dadoInicio + " e " + dadoFim);
            }
        } else {
            System.out.println("Vértices não encontrados para a aresta entre " + dadoInicio + " e " + dadoFim);
        }
    }

    public boolean pesquisarVertice(TIPO dado) {
        for (Vertice<TIPO> vertice : vertices) {
            if (vertice.getDado().equals(dado)) {
                System.out.println("Vértice encontrado: " + dado);
                return true;
            }
        }

        System.out.println("Vértice não encontrado: " + dado);
        return false;
    }

    public boolean pesquisarAresta(TIPO dadoInicio, TIPO dadoFim) {
        Vertice<TIPO> inicio = getVertice(dadoInicio);
        Vertice<TIPO> fim = getVertice(dadoFim);

        if (inicio != null && fim != null) {
            for (Aresta<TIPO> aresta : arestas) {
                if (aresta.getInicio().equals(inicio) && aresta.getFim().equals(fim)) {
                    System.out.println("Aresta encontrada entre " + dadoInicio + " e " + dadoFim);
                    return true;
                }
            }
            System.out.println("Aresta não encontrada entre " + dadoInicio + " e " + dadoFim);
            return false;
        } else {
            System.out.println("Vértices não encontrados para a pesquisa de aresta entre " + dadoInicio + " e " + dadoFim);
            return false;
        }
    }

    public ArrayList<Vertice<TIPO>> obterAdjacentes(TIPO dado) {
        Vertice<TIPO> vertice = getVertice(dado);

        if (vertice != null) {
            ArrayList<Vertice<TIPO>> adjacentes = new ArrayList<>();
            for (Aresta<TIPO> aresta : vertice.getArestasSaida()) {
                adjacentes.add(aresta.getFim());
            }
            return adjacentes;
        } else {
            System.out.println("Vértice não encontrado: " + dado);
            return null;
        }
    }


    public void imprimirAdjacentes() {
        for (Vertice<TIPO> vertice : vertices) {
            System.out.print("Vértice " + vertice.getDado() + " -> Adjacentes: ");
            ArrayList<Vertice<TIPO>> adjacentes = obterAdjacentes(vertice.getDado());

            if (adjacentes != null && !adjacentes.isEmpty()) {
                for (Vertice<TIPO> adjacente : adjacentes) {
                    System.out.print(adjacente.getDado() + " ");
                }
            } else {
                System.out.print("Nenhum adjacente");
            }

            System.out.println();
        }
    }

    public void imprimirVertices() {
        System.out.println("Lista de vértices:");
        for (Vertice<TIPO> vertice : vertices) {
            System.out.println(vertice.getDado());
        }
    }

    // Verificação de ciclo sem usar a lógica de Ordenação Topologica
    public boolean temCiclo() {
        Set<Vertice<TIPO>> visitados = new HashSet<>();
        Set<Vertice<TIPO>> pilha = new HashSet<>();

        for (Vertice<TIPO> vertice : vertices) {
            if (temCicloAux(vertice, visitados, pilha)) {
                System.out.println("Ciclo detectado!");
                return true;
            }
        }

        System.out.println("Nenhum ciclo detectado.");
        return false;
    }

    private boolean temCicloAux(Vertice<TIPO> vertice, Set<Vertice<TIPO>> visitados, Set<Vertice<TIPO>> pilha) {
        if (pilha.contains(vertice)) {
            return true; // Ciclo detectado
        }

        if (visitados.contains(vertice)) {
            return false; // Já visitado, não há ciclo
        }

        visitados.add(vertice);
        pilha.add(vertice);

        for (Aresta<TIPO> aresta : vertice.getArestasSaida()) {
            if (temCicloAux(aresta.getFim(), visitados, pilha)) {
                return true; // Ciclo detectado na recursão
            }
        }

        pilha.remove(vertice);
        return false;
    }


    // Verificação de ciclo usando a lógica de Ordenação Topologica
    public boolean temCicloOrdenacaoTopologica() {
        Set<Vertice<TIPO>> visitados = new HashSet<>();
        Stack<Vertice<TIPO>> pilha = new Stack<>();

        for (Vertice<TIPO> vertice : vertices) {
            if (!visitados.contains(vertice)) {
                if (temCicloOrdenacaoTopologicaAux(vertice, visitados, pilha)) {
                    System.out.println("Ciclo detectado!");
                    return true;
                }
            }
        }

        System.out.println("Nenhum ciclo detectado.");
        return false;
    }

    private Map<TIPO, List<TIPO>> adjacencias = new HashMap<>();

    public void adicionarVertices(TIPO vertice) {
        adjacencias.putIfAbsent(vertice, new ArrayList<>());
    }

    public void adicionarAresta(TIPO origem, TIPO destino) {
        adjacencias.get(origem).add(destino);
        adjacencias.get(destino).add(origem);
    }

    public boolean verificarBipartido() {
        Map<TIPO, Integer> cores = new HashMap<>();
        for (TIPO vertice : adjacencias.keySet()) {
            if (!cores.containsKey(vertice)) {
                if (!dfs(vertice, 0, cores)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean dfs(TIPO vertice, int cor, Map<TIPO, Integer> cores) {
        cores.put(vertice, cor);
        for (TIPO vizinho : adjacencias.get(vertice)) {
            if (!cores.containsKey(vizinho)) {
                if (!dfs(vizinho, 1 - cor, cores)) {
                    return false;
                }
            } else if (cores.get(vizinho) == cores.get(vertice)) {
                return false;
            }
        }
        return true;
    }

    public void imprimirParticoes() {
        Map<TIPO, Integer> cores = new HashMap<>();
        boolean bipartido = true;
        for (TIPO vertice : adjacencias.keySet()) {
            if (!cores.containsKey(vertice)) {
                if (!dfs(vertice, 0, cores)) {
                    bipartido = false;
                    break;
                }
            }
        }
        if (bipartido) {
            System.out.println("O grafo É bipartido:");
            List<TIPO> particao1 = new ArrayList<>();
            List<TIPO> particao2 = new ArrayList<>();
            for (Map.Entry<TIPO, Integer> entry : cores.entrySet()) {
                if (entry.getValue() == 0) {
                    particao1.add(entry.getKey());
                } else {
                    particao2.add(entry.getKey());
                }
            }
            System.out.println("Partição 1: " + particao1);
            System.out.println("Partição 2: " + particao2);
        } else {
            System.out.println("O grafo NÃO é bipartido.");
        }
    }

    private boolean temCicloOrdenacaoTopologicaAux(Vertice<TIPO> vertice, Set<Vertice<TIPO>> visitados, Stack<Vertice<TIPO>> pilha) {
        visitados.add(vertice);
        pilha.push(vertice);

        for (Aresta<TIPO> aresta : vertice.getArestasSaida()) {
            Vertice<TIPO> vizinho = aresta.getFim();

            if (!visitados.contains(vizinho)) {
                if (temCicloOrdenacaoTopologicaAux(vizinho, visitados, pilha)) {
                    return true;
                }
            } else if (pilha.contains(vizinho)) {
                System.out.println("Ciclo detectado!");
                return true;
            }
        }

        pilha.pop();
        return false;
    }

    public ArrayList<Vertice<TIPO>> ordenacaoTopologica() {
        Stack<Vertice<TIPO>> pilha = new Stack<>();
        Set<Vertice<TIPO>> visitados = new HashSet<>();

        for (Vertice<TIPO> vertice : vertices) {
            if (!visitados.contains(vertice)) {
                ordenacaoTopologicaAux(vertice, visitados, pilha);
            }
        }

        ArrayList<Vertice<TIPO>> resultado = new ArrayList<>();
        while (!pilha.isEmpty()) {
            resultado.add(pilha.pop());
        }

        return resultado;
    }

    private void ordenacaoTopologicaAux(Vertice<TIPO> vertice, Set<Vertice<TIPO>> visitados, Stack<Vertice<TIPO>> pilha) {
        visitados.add(vertice);

        for (Aresta<TIPO> aresta : vertice.getArestasSaida()) {
            Vertice<TIPO> vizinho = aresta.getFim();

            if (!visitados.contains(vizinho)) {
                ordenacaoTopologicaAux(vizinho, visitados, pilha);
            }
        }

        pilha.push(vertice);
    }

    public void encontrarCaminho(TIPO inicio, TIPO destino) {
        Vertice<TIPO> verticeInicio = this.getVertice(inicio);
        Vertice<TIPO> verticeDestino = this.getVertice(destino);

        if (verticeInicio != null && verticeDestino != null) {
            Stack<Vertice<TIPO>> caminho = new Stack<>();
            HashSet<Vertice<TIPO>> visitados = new HashSet<>();

            if (dfs(verticeInicio, verticeDestino, caminho, visitados)) {
                System.out.println("Caminho encontrado:");


                int con = 0;
                while (!caminho.isEmpty() && con < caminho.size() - 1) {
                    System.out.print(caminho.pop().getDado() + " ");
                }
                System.out.println(destino);
            } else {
                System.out.println("Caminho não encontrado.");
            }
        } else {
            System.out.println("Vértices de início ou destino não encontrados.");
        }
    }

    private int tempoAtual = 0; // Adicione um contador global para os tempos

    private boolean dfs(Vertice<TIPO> atual, Vertice<TIPO> destino, Stack<Vertice<TIPO>> caminho, HashSet<Vertice<TIPO>> visitados) {
        visitados.add(atual);
        atual.setTempoChegada(++tempoAtual); // Registra o tempo de chegada

        if (atual.equals(destino)) {
            caminho.push(atual); // Adiciona o vértice de destino ao caminho
            return true;  // Chegou ao destino
        }

        for (Aresta<TIPO> aresta : atual.getArestasSaida()) {
            Vertice<TIPO> proximo = aresta.getFim();
            if (!visitados.contains(proximo)) {
                if (dfs(proximo, destino, caminho, visitados)) {
                    caminho.push(atual); // Adiciona o vértice atual ao caminho
                    return true;
                }
            }
        }

        atual.setTempoPartida(++tempoAtual); // Registra o tempo de partida
        return false;
    }

    public void imprimirTempos() {
        // Imprimir os tempos de chegada e partida de cada vértice
        for (Vertice<TIPO> vertice : vertices) {
            System.out.printf("Vértice %s (Chegada: %d, Partida: %d)%n",
                    vertice.getDado(),
                    vertice.getTempoChegada(),
                    vertice.getTempoPartida());
        }
    }


    public void floydWarshall(TIPO origem, TIPO destino) {
        int numVertices = vertices.size();
        double[][] distancias = new double[numVertices][numVertices];
        int[][] proximo = new int[numVertices][numVertices];

        // Inicialização da matriz de distâncias e da matriz de próximos vértices
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (i == j) {
                    distancias[i][j] = 0;
                    proximo[i][j] = i;
                } else {
                    distancias[i][j] = Double.POSITIVE_INFINITY;
                    proximo[i][j] = -1;
                }
            }
        }

        // Preenchimento da matriz com as distâncias conhecidas
        for (Aresta<TIPO> aresta : arestas) {
            int indiceInicio = vertices.indexOf(aresta.getInicio());
            int indiceFim = vertices.indexOf(aresta.getFim());
            distancias[indiceInicio][indiceFim] = aresta.getPeso();
            proximo[indiceInicio][indiceFim] = indiceFim;
        }

        // Algoritmo de Floyd-Warshall
        for (int k = 0; k < numVertices; k++) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        proximo[i][j] = proximo[i][k];
                    }
                }
            }
        }

        // Imprimir caminho entre origem e destino
        imprimirCaminho(origem, destino, distancias, proximo);
    }

    private void imprimirCaminho(TIPO origem, TIPO destino, double[][] distancias, int[][] proximo) {
        int indiceOrigem = vertices.indexOf(getVertice(origem));
        int indiceDestino = vertices.indexOf(getVertice(destino));

        if (distancias[indiceOrigem][indiceDestino] == Double.POSITIVE_INFINITY) {
            System.out.println("Não há caminho entre " + origem + " e " + destino);
        } else {
            System.out.println("Menor caminho entre " + origem + " e " + destino + ":");
            List<TIPO> caminho = new ArrayList<>();
            int atual = indiceOrigem;
            while (atual != indiceDestino) {
                caminho.add(vertices.get(atual).getDado());
                atual = proximo[atual][indiceDestino];
            }
            caminho.add(destino);

            // Imprimir a lista do caminho
            for (int i = 0; i < caminho.size() - 1; i++) {
                TIPO verticeAtual = caminho.get(i);
                TIPO proximoVertice = caminho.get(i + 1);
                double peso = 0;

                // Iterar sobre a lista de arestas para encontrar a aresta entre verticeAtual e proximoVertice
                for (Aresta<TIPO> aresta : arestas) {
                    if (aresta.getInicio().getDado().equals(verticeAtual) && aresta.getFim().getDado().equals(proximoVertice)) {
                        peso = aresta.getPeso();
                        break;
                    }
                }

                System.out.printf("%s - %.1f km -> ", verticeAtual, peso);
            }
            System.out.println(caminho.get(caminho.size() - 1));


            System.out.println("Distancia total: " + distancias[vertices.indexOf(getVertice(origem))][vertices.indexOf(getVertice(destino))] + " km.");
        }
    }

    public void boruvkaMST() {
        // Inicialização
        int numVertices = vertices.size();
        UnionFind<TIPO> uf = new UnionFind<>(vertices);
        ArrayList<Aresta<TIPO>> mst = new ArrayList<>();

        // Enquanto o MST não estiver completo
        while (mst.size() < numVertices - 1) {
            // Array para armazenar a menor aresta de cada componente
            Map<Vertice<TIPO>, Aresta<TIPO>> menorAresta = new HashMap<>();

            // Percorre todas as arestas para encontrar a menor aresta de cada componente
            for (Aresta<TIPO> aresta : arestas) {
                Vertice<TIPO> u = uf.find(aresta.getInicio());
                Vertice<TIPO> v = uf.find(aresta.getFim());

                if (!u.equals(v)) {
                    // Verifica e atualiza a menor aresta para o componente de u
                    if (!menorAresta.containsKey(u) || menorAresta.get(u).getPeso() > aresta.getPeso()) {
                        menorAresta.put(u, aresta);
                    }
                    // Verifica e atualiza a menor aresta para o componente de v
                    if (!menorAresta.containsKey(v) || menorAresta.get(v).getPeso() > aresta.getPeso()) {
                        menorAresta.put(v, aresta);
                    }
                }
            }

            // Adiciona as menores arestas encontradas ao MST e une os componentes
            for (Map.Entry<Vertice<TIPO>, Aresta<TIPO>> entry : menorAresta.entrySet()) {
                Aresta<TIPO> aresta = entry.getValue();
                Vertice<TIPO> u = uf.find(aresta.getInicio());
                Vertice<TIPO> v = uf.find(aresta.getFim());

                if (!uf.connected(u, v)) {
                    uf.union(u, v);
                    mst.add(aresta);
                    System.out.println("Aresta adicionada ao MST: " + aresta.getInicio().getDado() + " - " + aresta.getFim().getDado() + " com peso " + aresta.getPeso());
                }
            }
        }

        System.out.println("Árvore Geradora Mínima construída pelo algoritmo de Borůvka:");
        for (Aresta<TIPO> aresta : mst) {
            System.out.println(aresta.getInicio().getDado() + " - " + aresta.getFim().getDado() + " com peso " + aresta.getPeso());
        }
    }


    public void boruvkaMST06() {
        // Inicialização
        int numVertices = vertices.size();
        UnionFind<TIPO> uf = new UnionFind<>(vertices);
        List<Aresta<TIPO>> mst = new ArrayList<>();

        // Enquanto o MST não estiver completo
        while (mst.size() < numVertices - 1) {
            // Array para armazenar a menor aresta de cada componente
            Map<Vertice<TIPO>, Aresta<TIPO>> menorAresta = new HashMap<>();

            // Percorre todas as arestas para encontrar a menor aresta de cada componente
            for (Aresta<TIPO> aresta : arestas) {
                Vertice<TIPO> u = uf.find(aresta.getInicio());
                Vertice<TIPO> v = uf.find(aresta.getFim());

                if (!u.equals(v)) {
                    // Verifica e atualiza a menor aresta para o componente de u
                    if (!menorAresta.containsKey(u) || menorAresta.get(u).getPeso() > aresta.getPeso()) {
                        menorAresta.put(u, aresta);
                    }
                    // Verifica e atualiza a menor aresta para o componente de v
                    if (!menorAresta.containsKey(v) || menorAresta.get(v).getPeso() > aresta.getPeso()) {
                        menorAresta.put(v, aresta);
                    }
                }
            }

            // Adiciona as menores arestas encontradas ao MST e une os componentes
            for (Map.Entry<Vertice<TIPO>, Aresta<TIPO>> entry : menorAresta.entrySet()) {
                Aresta<TIPO> aresta = entry.getValue();
                Vertice<TIPO> u = uf.find(aresta.getInicio());
                Vertice<TIPO> v = uf.find(aresta.getFim());

                if (!uf.connected(u, v)) {
                    uf.union(u, v);
                    mst.add(aresta);
                    System.out.println("Aresta adicionada ao MST: " + aresta.getInicio().getDado() + " - " + aresta.getFim().getDado() + " com peso " + aresta.getPeso());
                }
            }
        }

        System.out.println("Árvore Geradora Mínima construída pelo algoritmo de Borůvka:");
        for (Aresta<TIPO> aresta : mst) {
            System.out.println(aresta.getInicio().getDado() + " - " + aresta.getFim().getDado() + " com peso " + aresta.getPeso());
        }

        // Gera o ciclo mínimo aproximado usando DFS na MST
        List<Vertice<TIPO>> cicloMinimo = gerarCicloMinimo(mst);

        // Imprime o ciclo mínimo encontrado
        System.out.println("Ciclo Mínimo:");
        for (int i = 0; i < cicloMinimo.size(); i++) {
            Vertice<TIPO> atual = cicloMinimo.get(i);
            Vertice<TIPO> proximo = cicloMinimo.get((i + 1) % cicloMinimo.size());
            System.out.println(atual.getDado() + " -> " + proximo.getDado());
        }
    }


    private List<Vertice<TIPO>> gerarCicloMinimo(List<Aresta<TIPO>> mst) {
        Set<Vertice<TIPO>> visitados = new HashSet<>();
        List<Vertice<TIPO>> caminho = new ArrayList<>();
        Vertice<TIPO> inicial = mst.get(0).getInicio();
        dfs(inicial, visitados, caminho);
        caminho.add(inicial); // Fecha o ciclo

        return caminho;
    }

    private void dfs(Vertice<TIPO> vertice, Set<Vertice<TIPO>> visitados, List<Vertice<TIPO>> caminho) {
        visitados.add(vertice);
        caminho.add(vertice);

        for (Aresta<TIPO> aresta : arestas) {
            Vertice<TIPO> vizinho = null;
            if (aresta.getInicio().equals(vertice) && !visitados.contains(aresta.getFim())) {
                vizinho = aresta.getFim();
            } else if (aresta.getFim().equals(vertice) && !visitados.contains(aresta.getInicio())) {
                vizinho = aresta.getInicio();
            }

            if (vizinho != null) {
                dfs(vizinho, visitados, caminho);
            }
        }
    }

    private List<Vertice<TIPO>> gerarCicloMinimo05(List<Aresta<TIPO>> mst) {
        // Construa o grafo da MST
        Map<Vertice<TIPO>, List<Vertice<TIPO>>> adjList = new LinkedHashMap<>();
        for (Aresta<TIPO> aresta : mst) {
            adjList.computeIfAbsent(aresta.getInicio(), k -> new ArrayList<>()).add(aresta.getFim());
            adjList.computeIfAbsent(aresta.getFim(), k -> new ArrayList<>()).add(aresta.getInicio());
        }

        // Execute DFS para encontrar um ciclo (o ciclo mínimo aproximado)
        Set<Vertice<TIPO>> visitados = new HashSet<>();
        List<Vertice<TIPO>> ciclo = new ArrayList<>();
        Vertice<TIPO> inicio = adjList.keySet().iterator().next();
        dfs5(ciclo, adjList, inicio, visitados);

        // Adiciona o vértice inicial ao final para fechar o ciclo
        if (!ciclo.isEmpty() && !ciclo.get(0).equals(ciclo.get(ciclo.size() - 1))) {
            ciclo.add(ciclo.get(0));
        }

        return ciclo;
    }

    private void dfs5(List<Vertice<TIPO>> ciclo, Map<Vertice<TIPO>, List<Vertice<TIPO>>> adjList, Vertice<TIPO> atual, Set<Vertice<TIPO>> visitados) {
        visitados.add(atual);
        ciclo.add(atual);

        for (Vertice<TIPO> vizinho : adjList.get(atual)) {
            if (!visitados.contains(vizinho)) {
                dfs5(ciclo, adjList, vizinho, visitados);
            }
        }
    }
}