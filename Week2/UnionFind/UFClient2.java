import edu.princeton.cs.algs4.*;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Logger;

public class UFClient2 {

    public static void main(String[] args) {
        In input = new In("D:\\VisualStudioCode\\CTDLGT\\UnionFind\\src\\io\\input.txt");
        int[] data = input.readAllInts();
        int query = 1;

        int countEdges = data[0];
        UF uf = new UF(countEdges);

        int countInput = 1;
        while (query < data.length) {
            int p = data[query];
            int q = data[query+1];
            uf.union(p, q);

            boolean isUnionAll = true;
            for (int edge = 0; edge < countEdges - 1; ++edge) {
                if (!uf.connected(edge, edge + 1)) {
                    isUnionAll = false;
                }
            }

            if (isUnionAll) {
                System.out.println(countInput);
                return;
            }
            countInput = countInput + 1;
            query+=2;
        }
        System.out.println("FAILED");

    }
}
