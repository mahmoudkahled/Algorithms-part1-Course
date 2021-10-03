import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private SearchNode goalnode;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    private class SearchNode {
        private Board current;
        private SearchNode prev;
        int moves;
        private int manhattan;

        public SearchNode() {
        }

        public SearchNode(Board current, int moves, SearchNode prev) {
            this.current = current;
            this.moves = moves;
            this.prev = prev;
            manhattan = current.manhattan() + moves;
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        MinPQ<SearchNode> originalminpq = new MinPQ<SearchNode>(manhattanOrder());
        SearchNode root_searchnode = new SearchNode(initial, 0, null);
        originalminpq.insert(root_searchnode);
        originalminpq.delMin();
        MinPQ<SearchNode> unsolvableminpq = new MinPQ<SearchNode>(manhattanOrder());
        SearchNode unsolvable_searchnode = new SearchNode(initial.twin(), 0, null);
        unsolvableminpq.insert(unsolvable_searchnode);
        unsolvableminpq.delMin();
        boolean check = false;
        solvable = true;
        while (!root_searchnode.current.isGoal() && !unsolvable_searchnode.current.isGoal()) {
            for (Board board : root_searchnode.current.neighbors()) {
                if (check) {
                    if (!board.equals(root_searchnode.prev.current)) {
                        SearchNode new_searchnode = new SearchNode(board, (root_searchnode.moves + 1), root_searchnode);
                        originalminpq.insert(new_searchnode);
                    }
                } else {
                    SearchNode new_searchnode = new SearchNode(board, (root_searchnode.moves + 1), root_searchnode);
                    originalminpq.insert(new_searchnode);
                }
            }
            root_searchnode = originalminpq.delMin();
            if (root_searchnode.current.isGoal()) {
                solvable = true;
                break;
            }
            solvable = false;
            for (Board board : unsolvable_searchnode.current.neighbors()) {
                SearchNode new_searchnode = new SearchNode(board, (unsolvable_searchnode.moves + 1), unsolvable_searchnode);
                if (new_searchnode.current.equals(unsolvable_searchnode.current)) {
                    continue;
                } else {
                    unsolvableminpq.insert(new_searchnode);
                }
            }
            unsolvable_searchnode = unsolvableminpq.delMin();
            check = true;
        }
        goalnode = root_searchnode;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return goalnode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            Stack<Board> stack = new Stack<>();
            SearchNode series_searchnode = goalnode;
            while (series_searchnode.prev != null) {
                stack.push(series_searchnode.current);
                series_searchnode = series_searchnode.prev;
            }
            stack.push(series_searchnode.current);
            return stack;
        }
        return null;
    }

    private static Comparator<SearchNode> manhattanOrder() {
        return (s1, s2) -> Integer.compare(s1.manhattan, s2.manhattan);
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        System.out.println(initial.manhattan());

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }
}
