public class Solution {
    public static int binarySearch(int[] a, int number) {
        int left = 0, right = a.length;
        while (left <= right) {
            int middle = (left + right) / 2;
            if (a[middle] == number) {
                return middle;
            }
            if (a[middle] > number) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
    }
}
