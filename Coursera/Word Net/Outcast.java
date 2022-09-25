import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) {
        int distance = 0;
        String outcast = null;

        for (String i : nouns) {
            int d = 0;

            for (String j : nouns) {
                int dist = wordnet.distance(i, j);
                d += dist;
            }

            if (d > distance) {
                distance = d;
                outcast = i;
            }
        }

        assert outcast != null;
        return outcast;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int queries = 2; queries < args.length; queries++) {
            In in = new In(args[queries]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[queries] + ": " + outcast.outcast(nouns));
        }
    }
}