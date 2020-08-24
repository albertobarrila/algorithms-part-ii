/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Topological;

public class WordNet {
    private final ST<Integer, String> synsets = new ST<Integer, String>();
    private final ST<String, Bag<Integer>> nouns = new ST<String, Bag<Integer>>();

    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Input parameters are null");

        In in = new In(synsets);
        while (!in.isEmpty()) {
            String[] line = in.readLine().split(",");
            this.synsets.put(Integer.parseInt(line[0]), line[1]);
            String[] nounsList = line[1].split(" ");

            for (String n : nounsList) {
                if (!this.nouns.contains(n)) {
                    Bag<Integer> values = new Bag<Integer>();
                    values.add(Integer.parseInt(line[0]));
                    this.nouns.put(n, values);
                }
                else
                    this.nouns.get(n).add(Integer.parseInt(line[0]));
            }
        }

        // dichiaro i vertici
        Digraph h = new Digraph(this.synsets.size());

        in = new In(hypernyms);
        while (!in.isEmpty()) {
            String[] line = in.readLine().split(",");
            for (int i = 1; i < line.length; i++) {
                h.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[i]));
            }
        }

        Topological t = new Topological(h);
        if (!t.hasOrder())
            throw new IllegalArgumentException("Input parameters are not a DAG");

        verifyOneRoot(h);

        sap = new SAP(h);
    }

    private void verifyOneRoot(Digraph G) {
        int root = -1;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0)
                if (root < 0)
                    root = i;
                else
                    throw new IllegalArgumentException("Hypernyms contains more than 1 root");
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("Input parameters are null");
        return this.nouns.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("Input parameters are null");

        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("Input parameters are not valid noun");


        return sap.length(this.nouns.get(nounA), this.nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("Input parameters are null");

        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("Input parameters are not valid noun");

        int a = sap.ancestor(this.nouns.get(nounA), this.nouns.get(nounB));
        return this.synsets.get(a);
    }
}
