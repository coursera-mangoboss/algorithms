import edu.princeton.cs.algs4.*;
import java.util.Arrays;
public class ThreeSum {

    private static final String pathFile = "D:\\VisualStudioCode\\CTDLGT\\UnionFind\\src\\io\\input.txt";
    private static boolean isDuplicate(long[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i] == a[i-1]) return true;
        return false;
    }

    private static void validator(long list[]) {
        if(isDuplicate(list)) {
            throw new IllegalArgumentException("array contains duplicate integers");
        }
    }

    private static long[] getList(String path) {
        In in = new In(path) ;
        return in.readAllLongs();
    }

    public static void getResults(long[] list) {
        Arrays.sort(list);
        validator(list);
        for (int i = 0; i < list.length; i++) {
            for (int j = i+1; j < list.length; j++) {
                int k = Arrays.binarySearch(list, -(list[i] + list[j]));
                if (k > j) StdOut.println(list[i] + " " + list[j] + " " + list[k]);
            }
        }
    }

    public static void main(String[] args)  {
        long[] list = getList(pathFile);
        getResults(list);
    }
}
