package Week2.MinimumSpanningTrees;

public class Edge implements Comparable<Edge> {

    private final int v, w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    int either() {
        return v;
    }

    int other(int v) {
        if (v == this.v)
            return w;
        return v;
    }

    double weight() {
        return weight;
    }

    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }
}
