import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;

public class SAP {
    private Digraph g;
    private int lengthAns;
    private int ancestorAns;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        g = new Digraph(G);
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= g.V()) {
            throw new IllegalArgumentException();
        }
    }

    private void validateVertices(Iterable<Integer> sV) {
        if (sV == null) {
            throw new IllegalArgumentException();
        }
        for (Integer v : sV) {
            if (v == null) {
                throw new IllegalArgumentException();
            }
            validateVertex(v);
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        SET<Integer> sV = new SET<>();sV.add(v);
        SET<Integer> sW = new SET<>();sW.add(w);
        return length(sV, sW);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such
    // path
    public int ancestor(int v, int w) {
        SET<Integer> sV = new SET<>();
        sV.add(v);
        SET<Integer> sW = new SET<>();
        sW.add(w);
        return ancestor(sV, sW);
    }


    private void solve(Iterable<Integer> v, Iterable<Integer> w) {
        ancestorAns = -1;
        lengthAns = -1;
        Integer count = 0;


        for(Integer i : v) {
            count++;
        }

        if(count == 0) {
            return;
        }

        count = 0;
        for(Integer i : w) {
            count++;
        }

        if(count == 0) {
            return;
        }


        BreadthFirstDirectedPaths distV = new BreadthFirstDirectedPaths(g,v);
        BreadthFirstDirectedPaths distW = new BreadthFirstDirectedPaths(g,w);
        lengthAns = Integer.MAX_VALUE;
        for (int i = 0; i < g.V(); ++i) {
            if (distV.hasPathTo(i) && distW.hasPathTo(i)) {
                if (lengthAns > distV.distTo(i) + distW.distTo(i)) {
                    lengthAns = distV.distTo(i) + distW.distTo(i);
                    ancestorAns = i;
                }
            }
        }
        if (ancestorAns == -1) {
            lengthAns = -1;
        }
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such
    // path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);
        solve(v, w);
        return lengthAns;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);
        solve(v, w);
        return ancestorAns;
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}