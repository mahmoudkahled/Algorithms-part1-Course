import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {
    private final int[][] tile;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        tile = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tile[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.tile.length).append("\n");
        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile[i].length; j++) {
                sb.append(this.tile[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return this.tile.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming_value = 0;
        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile[i].length; j++) {
                if (tile[i][j] != ((j + 1) + i * dimension()) && tile[i][j] != 0) {
                    hamming_value++;
                }
            }
        }
        return hamming_value;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int row_position, column_position, manhattan_value = 0;
        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile[i].length; j++) {
                if (tile[i][j] == 0) {
                    continue;
                }
                row_position = (tile[i][j] - 1) / dimension();
                column_position = (tile[i][j] - row_position * dimension()) - 1;
                manhattan_value += (Math.abs((column_position - j)) + Math.abs((row_position - i)));
                row_position = 0;
                column_position = 0;
            }
        }
        return manhattan_value;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (hamming() == 0 && manhattan() == 0) {
            return true;
        } else {
            return false;
        }
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if(this == y){
            return true;
        }
        if (this.getClass() != y.getClass()) {
            return false;
        }
        if (this.dimension() != ((Board) y).dimension()) {
            return false;
        }
        for(int i = 0; i < this.tile.length; i++){
            for (int j = 0; j < this.tile[i].length; j++){
                if(this.tile[i][j] != ((Board) y).tile[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int row_value = 0, column_value = 0;
        int[][] temp_tile = new int[dimension()][dimension()];
        Stack<Board> stack = new Stack<>();
        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile[i].length; j++) {
                temp_tile[i][j] = tile[i][j];
                if (tile[i][j] == 0) {
                    row_value = i;
                    column_value = j;
                }
            }
        }
        if ((row_value == 0 || row_value == (dimension() - 1)) && (column_value < (dimension() - 1) && column_value > 0)) {
            exch(temp_tile, (row_value), column_value, (row_value), (column_value + 1));
            stack.push(new Board(temp_tile));
            exch(temp_tile, (row_value), (column_value + 1), (row_value), (column_value));
            exch(temp_tile, (row_value), column_value, (row_value), (column_value - 1));
            stack.push(new Board(temp_tile));
            exch(temp_tile, (row_value), (column_value - 1), (row_value), (column_value));
            if (row_value == 0) {
                exch(temp_tile, (row_value), column_value, (row_value + 1), column_value);
                stack.push(new Board(temp_tile));
                exch(temp_tile, ((row_value + 1)), column_value, (row_value), column_value);
            } else {
                exch(temp_tile, (row_value), column_value, (row_value - 1), column_value);
                stack.push(new Board(temp_tile));
                exch(temp_tile, (row_value - 1), column_value, (row_value), column_value);
            }
        }
        if ((row_value == 0 || row_value == (dimension() - 1)) && (column_value == 0)) {
            exch(temp_tile, (row_value), column_value, (row_value), (column_value + 1));
            stack.push(new Board(temp_tile));
            exch(temp_tile, (row_value), (column_value + 1), (row_value), (column_value));
            if (row_value == 0) {
                exch(temp_tile, (row_value), column_value, (row_value + 1), (column_value));
                stack.push(new Board(temp_tile));
                exch(temp_tile, (row_value + 1), column_value, (row_value), (column_value));
            } else {
                exch(temp_tile, (row_value), column_value, (row_value - 1), (column_value));
                stack.push(new Board(temp_tile));
                exch(temp_tile, (row_value - 1), column_value, (row_value), (column_value));
            }
        }
        if ((row_value == 0 || row_value == (dimension() - 1)) && (column_value == (dimension() - 1))) {
            exch(temp_tile, (row_value), column_value, (row_value), (column_value - 1));
            stack.push(new Board(temp_tile));
            exch(temp_tile, (row_value), (column_value - 1), (row_value), (column_value));
            if (row_value == 0) {
                exch(temp_tile, (row_value), column_value, (row_value + 1), (column_value));
                stack.push(new Board(temp_tile));
                exch(temp_tile, (row_value + 1), column_value, (row_value), (column_value));
            } else {
                exch(temp_tile, (row_value), column_value, (row_value - 1), (column_value));
                stack.push(new Board(temp_tile));
                exch(temp_tile, (row_value - 1), column_value, (row_value), (column_value));
            }
        }
        if ((row_value > 0 && row_value < (dimension() - 1)) && (column_value > 0 && column_value < (dimension() - 1))) {
            exch(temp_tile, (row_value), column_value, (row_value), (column_value + 1));
            stack.push(new Board(temp_tile));
            exch(temp_tile, (row_value), (column_value + 1), (row_value), (column_value));
            exch(temp_tile, (row_value), column_value, (row_value), (column_value - 1));
            stack.push(new Board(temp_tile));
            exch(temp_tile, (row_value), (column_value - 1), (row_value), (column_value));
            exch(temp_tile, (row_value), column_value, (row_value + 1), column_value);
            stack.push(new Board(temp_tile));
            exch(temp_tile, (row_value + 1), column_value, (row_value), column_value);
            exch(temp_tile, (row_value), column_value, (row_value - 1), column_value);
            stack.push(new Board(temp_tile));
            exch(temp_tile, (row_value - 1), column_value, (row_value), column_value);
        }
        if ((row_value > 0 && row_value < (dimension() - 1)) && ((column_value == 0 || column_value == (dimension() - 1)))) {
            exch(temp_tile, (row_value), column_value, (row_value + 1), (column_value));
            stack.push(new Board(temp_tile));
            exch(temp_tile, (row_value + 1), column_value, (row_value), (column_value));
            exch(temp_tile, (row_value), column_value, (row_value - 1), (column_value));
            stack.push(new Board(temp_tile));
            exch(temp_tile, (row_value - 1), column_value, (row_value), (column_value));
            if (column_value == 0) {
                exch(temp_tile, (row_value), column_value, (row_value), (column_value + 1));
                stack.push(new Board(temp_tile));
                exch(temp_tile, (row_value), (column_value + 1), (row_value), (column_value));
            } else {
                exch(temp_tile, (row_value), column_value, (row_value), (column_value - 1));
                stack.push(new Board(temp_tile));
                exch(temp_tile, (row_value), (column_value - 1), (row_value), (column_value));
            }
        }
        return stack;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] temp_tile = new int[dimension()][dimension()];
        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile[i].length; j++) {
                temp_tile[i][j] = tile[i][j];
            }
        }
        for (int i = 0; i < temp_tile.length; i++) {
            if(temp_tile[i][0] != 0 && temp_tile[i][1] != 0){
                exch(temp_tile,i,0,i,1);
                break;
            }
        }
        return new Board(temp_tile);
    }

    private void exch(int[][] temp_tile, int i, int j, int x, int y) {
        int temp_value = 0;
        temp_value = temp_tile[i][j];
        temp_tile[i][j] = temp_tile[x][y];
        temp_tile[x][y] = temp_value;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int n = 10;
        int[][] arr = new int[n][n];

        int x = 1;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                arr[i][j] = x++;

        // arr[0][0] = 2;
        // arr[0][1] = 1;
        // arr[0][2] = 3;
        // arr[1][0] = 4;
        // arr[1][1] = 6;
        // arr[1][2] = 5;
        // arr[2][0] = 7;
        // arr[2][1] = 8;
        // arr[2][2] = 0;

        // arr[0][0] = 0;
        // arr[0][1] = 1;
        // arr[1][0] = 3;
        // arr[1][1] = 2;

        Board b = new Board(arr);
        StdOut.println(b.toString());
        //StdOut.println(b.twin().toString());


        //Iterable<Board> i = b.neighbors();

        // for (Board x : i)
        //     StdOut.println(x.toString());

        //arr[0][0] = 1;
        //arr[0][1] = 8;
        Board b1 = new Board(arr);
        //
        // StdOut.println(b.toString());
        // StdOut.println(b.hamming());
        // StdOut.println(b.manhattan());
        // StdOut.println(b.isGoal());
        //
        StdOut.println(b.equals(b1));
    }
}
