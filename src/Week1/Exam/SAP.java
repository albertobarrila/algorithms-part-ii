/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private final Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException("Input parameters are null");
        this.G = new Digraph(G);
    }

    private boolean valid(int v) {
        return v >= 0 && v < G.V();
    }

    private boolean isEmpty(Iterable<Integer> v) {
        int counter = 0;
        for (Object ignored : v) {
            counter++;
        }
        return counter == 0;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (!valid(v) || !valid(w))
            throw new IllegalArgumentException("Input vertex out of bounds");

        BreadthFirstDirectedPaths vBfs = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths wBfs = new BreadthFirstDirectedPaths(this.G, w);

        int distance = Integer.MAX_VALUE;
        for (int i = 0; i < G.V(); i++) {
            if (vBfs.hasPathTo(i) && wBfs.hasPathTo(i)) {
                int d = vBfs.distTo(i) + wBfs.distTo(i);
                if (d < distance)
                    distance = d;
            }
        }

        return distance == Integer.MAX_VALUE ? -1 : distance;

    }


    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (!valid(v) || !valid(w))
            throw new IllegalArgumentException("Input vertex out of bounds");

        BreadthFirstDirectedPaths vBfs = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths wBfs = new BreadthFirstDirectedPaths(this.G, w);

        int distance = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < G.V(); i++) {
            if (vBfs.hasPathTo(i) && wBfs.hasPathTo(i)) {
                int d = vBfs.distTo(i) + wBfs.distTo(i);
                if (d < distance) {
                    distance = d;
                    ancestor = i;
                }

            }
        }

        return ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException("Input parameters are null");

        if (isEmpty(v) || isEmpty(w))
            return -1;

        BreadthFirstDirectedPaths vBfs = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths wBfs = new BreadthFirstDirectedPaths(this.G, w);

        int distance = Integer.MAX_VALUE;
        for (int i = 0; i < G.V(); i++) {
            if (vBfs.hasPathTo(i) && wBfs.hasPathTo(i)) {
                int d = vBfs.distTo(i) + wBfs.distTo(i);
                if (d < distance)
                    distance = d;
            }
        }

        return distance == Integer.MAX_VALUE ? -1 : distance;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException("Input parameters are null");

        if (isEmpty(v) || isEmpty(w))
            return -1;

        BreadthFirstDirectedPaths vBfs = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths wBfs = new BreadthFirstDirectedPaths(this.G, w);

        int distance = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < G.V(); i++) {
            if (vBfs.hasPathTo(i) && wBfs.hasPathTo(i)) {
                int d = vBfs.distTo(i) + wBfs.distTo(i);
                if (d < distance) {
                    distance = d;
                    ancestor = i;
                }

            }
        }

        return ancestor;

    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
