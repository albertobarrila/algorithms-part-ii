package Week1.DirectedGraph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Digraph {
    private int V;
    private Bag<Integer>[] adj;

    public Digraph(int V) {
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }

    public Digraph(In in) {
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

    public int E() {
        return 0;
    }

    public Digraph reverse() {
        return null;
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph g = new Digraph(in);

        for (int v = 0; v < g.V(); v++)
            for (int w : g.adj(v))
                StdOut.println(v + " -> " + w);
    }
}
