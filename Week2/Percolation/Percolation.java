import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int top, bot;
    private int countOpenSites;
    private WeightedQuickUnionUF uf,isFullUF;
    private int matrixSize;
    private boolean[][] matrix;
    private static final int[] DX = {0, 0, 1, -1};
    private static final int[] DY = {-1, 1, 0, 0};


    private int countNodes;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Matrix size must be mort than zero.");
        }
        countNodes = 0;
        matrixSize = n;
        countOpenSites = 0;
        matrix = new boolean[n + 1][n + 1];

        for (int row = 1; row <= n; ++row) {
            for (int col = 1; col <= n; ++col) {
                matrix[row][col] = false;
            }
        }
        top = n*n;
        bot = n*n+1;
        uf = new WeightedQuickUnionUF(n*n+2);
        isFullUF = new WeightedQuickUnionUF(n*n+1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validator(row, col);
        if (!matrix[row][col]) {
            countOpenSites++;
            matrix[row][col] = true;
            connectAround(row, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validator(row, col);
        return matrix[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validator(row, col);
        return (isFullUF.find(top) == isFullUF.find(siteToNode(row,col)));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return isConnected(top, bot);
    }

    // convert [cow][col] => Index of Node
    private int siteToNode(int row, int col) {
        validator(row, col);
        return (matrixSize*(row-1) + col -1);
    }

    private void connectAround(int row, int col) {
        validator(row, col);
        if (isOpen(row, col)) {
            for (int index = 0; index < 4; ++index) {
                int newRow = row + DX[index];
                int newCol = col + DY[index];

                if (isOnMatrix(newRow, newCol) && isOpen(newRow, newCol)) {
                    uf.union(siteToNode(row, col), siteToNode(newRow, newCol));
                    isFullUF.union(siteToNode(row, col), siteToNode(newRow, newCol));
                }


            }

            if (row == 1) {
                uf.union(top, siteToNode(row, col));
                isFullUF.union(top,siteToNode(row,col));
            }

            if (row == matrixSize) {
                uf.union(bot, siteToNode(row, col));
            }
        }
    }

    private boolean isOnMatrix(int row, int col) {
        return (1 <= row && row <= matrixSize && 1 <= col && col <= matrixSize);
    }

    private void validator(int row, int col) {
        if (!isOnMatrix(row, col)) {
            throw new IllegalArgumentException("Site out of Bounds.");
        }
    }

    private boolean isConnected(int p,int q) {
        return (uf.find(p) == uf.find(q));
    }


}