import java.util.Arrays;
import java.util.TreeMap;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Topological;

public class WordNet {
    private TreeMap<String, SET<Integer>> nounToIndex;
    private TreeMap<Integer, String> indexToNoun;

    private int vertices;
    private Digraph g;
    private boolean[] isRoot;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        synsets(synsets);
        hypernyms(hypernyms);
    }

    private void synsets(String file) {
        nounToIndex = new TreeMap<>();
        indexToNoun = new TreeMap<>();
        In in = new In(file);
        while (in.hasNextLine()) {
            String[] parts = in.readLine().split(",");
            int id = Integer.parseInt(parts[0]);
            indexToNoun.put(id, parts[1]);
            String[] nouns = parts[1].split(" ");
            for (String noun : nouns) {
                if (!nounToIndex.containsKey(noun)) {
                    nounToIndex.put(noun, new SET<>());
                }
                nounToIndex.get(noun).add(id);
            }
        }
        vertices = indexToNoun.size();
    }


    private void hypernyms(String file) {
        g = new Digraph(vertices);
        isRoot = new boolean[vertices];
        Arrays.fill(isRoot, Boolean.TRUE);
        In in = new In(file);
        while (in.hasNextLine()) {
            String[] parts = in.readLine().split(",");
            int idU = Integer.parseInt(parts[0]);
            for (int i = 1; i < parts.length; ++i) {
                int idV = Integer.parseInt(parts[i]);
                g.addEdge(idU, idV);
                isRoot[idU] = false;
            }
        }
        if (!isRootedDAG()) {
            throw new IllegalArgumentException();
        }
        sap = new SAP(g);
    }

    private boolean isRootedDAG() {
        int numRoots = 0;
        for (int i = 0; i < vertices; ++i) {
            if (isRoot[i]) {
                numRoots++;
            }
        }
        if (numRoots != 1) {
            return false;
        }
        Topological topo = new Topological(g);
        return topo.hasOrder();
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounToIndex.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return nounToIndex.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        SET<Integer> sA = nounToIndex.get(nounA);
        SET<Integer> sB = nounToIndex.get(nounB);
        return sap.length(sA, sB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        SET<Integer> sA = nounToIndex.get(nounA);
        SET<Integer> sB = nounToIndex.get(nounB);
        return indexToNoun.get(sap.ancestor(sA, sB));

    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}