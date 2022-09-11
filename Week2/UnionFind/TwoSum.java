import edu.princeton.cs.algs4.*;

import java.util.Arrays;

public class TwoSum {

    private static long[] getList(String path) {
        In in = new In(path);
        long[] data = in.readAllLongs();
        return data;
    }

    public static void main(String[] args) {
        long[] list = getList("D:\\VisualStudioCode\\CTDLGT\\UnionFind\\src\\io\\input.txt");

        long countSumZero = 0;
        for(int i=0;i<list.length;++i){
            for(int j=0;j<i;++j) {
                if(list[i]+list[j] == 0) {
                    countSumZero ++ ;
                }
            }
        }

        System.out.println(countSumZero);
    }
}
