import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public final class Solver {
    private final Stack<Board> boards;
    private boolean isSolvable;

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        isSolvable = false;
        boards = new Stack<>();

        MinPQ<Node> nodes = new MinPQ<>();
        nodes.insert(new Node(initial, null));
        nodes.insert(new Node(initial.twin(),null));
        while (!nodes.min().board.isGoal()) {
            Node searchNode = nodes.delMin();
            for (Board board : searchNode.board.neighbors())
                if (searchNode.prev == null || (searchNode.prev != null && !searchNode.prev.board.equals(board))) {
                        nodes.insert(new Node(board, searchNode));
                }
        }

        Node current = nodes.min();
        while (current.prev != null) {
            boards.push(current.board);
            current = current.prev;
        }
        boards.push(current.board);

        if (current.board.equals(initial)) isSolvable = true;

    }

    public int moves() {
        if (!isSolvable()) return -1;
        return boards.size() - 1;
    }

    public Iterable<Board> solution() {
        if (isSolvable()) return boards;
        return null;
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    private class Node implements Comparable<Node> {
        private Board board;
        private Node prev;
        private int moves;
        private int manhattan;

        public Node(Board board, Node prevNode) {
            this.board = board;
            this.prev = prevNode;
            this.manhattan = board.manhattan();
            if (prevNode != null) moves = prevNode.moves + 1;
            else moves = 0;
        }

        @Override
        public int compareTo(Node that) {
            int left = (this.manhattan + this.moves);
            int right = (that.manhattan + that.moves);
            return  (left-right == 0 ? this.manhattan - that.manhattan : left-right);
        }
    }

    public static void main(String[] args) {
    }
}