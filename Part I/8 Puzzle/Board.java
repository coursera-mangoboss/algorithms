import java.util.Arrays;
import java.util.Stack;

public final class Board {
    private final int[][] board;

    private int[][] clone(int[][] board) {
        int[][] clone = new int[board.length][board.length];
        for(int x = 0;x<board.length;++x) {
            for(int y = 0;y<board.length;++y) {
                clone[x][y] = board[x][y];
            }
        }
        return clone;
    }
    public Board(int[][] board) {
        if(board == null) {
            throw new IllegalArgumentException();
        }
        this.board = clone(board);
    }

    @Override
    public String toString() {
        String s = ""+dimension();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
               s = s+" " + board[i][j];
            }
            s = s + '\n';
        }
        return s;
    }

    public int dimension() {
        return board.length;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Board)) {
            return false;
        }
        Board that = (Board) obj;
        return Arrays.deepEquals(this.board, that.board);
    }

    public Board twin() {
        int[][] twin = clone(board);

        if (twin[0][0] != 0 && twin[0][1] != 0)
            return new Board(swap(0, 0, 0, 1));
        else
            return new Board(swap(1, 0, 1, 1));
    }

    private int[][] swap(int i1, int j1, int i2, int j2) {
        int[][] clone = clone(board);
        int temp = clone[i1][j1];
        clone[i1][j1] = clone[i2][j2];
        clone[i2][j2] = temp;
        return clone;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> neighbours = new Stack<>();
        int position = findZero();
        int i = position / dimension();
        int j = position % dimension();
        if (i > 0)
            neighbours.push(new Board(swap(i, j, i - 1, j)));
        if (i < board.length - 1)
            neighbours.push(new Board(swap(i, j, i + 1, j)));
        if (j > 0)
            neighbours.push(new Board(swap(i, j, i, j - 1)));
        if (j < board.length - 1)
            neighbours.push(new Board(swap(i, j, i, j + 1)));

        return neighbours;
    }

    private int findZero() {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++)
                if (board[i][j] == 0)
                    return j + i * dimension();
        return 0;
    }

    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++)
                if (board[i][j] != 0 && board[i][j] != hash(i,j)) hamming++;
        return hamming;
    }

    private int hash(int i,int j) {
        return j + i * dimension() + 1;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++)
                if (board[i][j] != 0 && board[i][j] != j + i * dimension() + 1){
                    int square = board[i][j]-1;
                    int horizontal = Math.abs(square % dimension() - j);
                    int vertical = Math.abs(square / dimension() - i);
                    manhattan += (horizontal + vertical);
                }

        return manhattan;
    }


}