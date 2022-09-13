
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.Collections;

public class Solution {
    private static final String PathFile = "D:\\VisualStudioCode\\CTDLGT\\ThreeSum\\src\\io\\input.txt";
    private static final int MAXN = 50000000;
    private static long[] isMark;
    private static long[] getList(String pathFile) {
        In in = new In(pathFile);
        long[] list = in.readAllLongs();
        return list;
    }

    public static void main(String[] args) {
        long[] list = getList(PathFile);

        Arrays.sort(list);

        isMark = new long[MAXN];
        for(int i=0;i<MAXN;++i) {
            isMark[i] = 0;
        }

        long threeSumZeroCount = 0;
        for(int right =list.length-1;right>=0;--right) {
            for(int left = 0;left<right;++left) {
                if(list[left]+list[right] <=0) {
                    threeSumZeroCount = threeSumZeroCount + isMark[(int) -(list[left]+list[right])];
                }
            }
            if(list[right] >=0 ){
                isMark[(int) list[right]]++;
            }

        }

        System.out.println(threeSumZeroCount);
    }
}
