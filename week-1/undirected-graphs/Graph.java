import edu.princeton.cs.algs4.Bag;

public class Graph {
    private final int V;
    private Bag<Integer>[] adj;

    public Graph(int V) {
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }
    
    void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    Iterable<Integer> adj(int v) {
        return adj[v];
    }

    int V() {
        return V;
    }

    int E() {
        return 0;
    }

    public String toString() {
        return "Graph";
    }
}
