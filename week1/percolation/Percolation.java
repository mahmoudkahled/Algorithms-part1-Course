import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private final static int top_element = 0;
    private boolean[][] elements;
    private int bottom_element;
    private int open_sites;
    private int size;
    private WeightedQuickUnionUF qf;
    public Percolation(int n) {
        if (n < 0 || n == 0){
            throw new IllegalArgumentException("the number of n by n grid is invalid input");
        }
        size = n;
        open_sites = 0;
        bottom_element = n * n + 1;
        elements = new boolean[n + 2][n];
        qf = new WeightedQuickUnionUF(n * n + 2);
    }
    public void open(int row, int col) {
        exception(row,col);
        elements[row - 1][col - 1] = true;
        open_sites++;
        if(row == 1){
            qf.union(top_element,xyto1D(row,col));
        }
        if(row == size){
            qf.union(xyto1D(row , col), bottom_element);
        }
        if(row > 1 && isOpen(row - 1, col)){
            qf.union(xyto1D(row , col), xyto1D(row -1,col));
        }
        if(row < size && isOpen(row + 1, col)){
            qf.union(xyto1D(row , col), xyto1D(row + 1,col));
        }
        if(col > 1 && isOpen(row, col - 1)){
            qf.union(xyto1D(row , col), xyto1D(row,col - 1));
        }
        if(col < size && isOpen(row, col + 1)){
            qf.union(xyto1D(row , col), xyto1D(row,col + 1));
        }
    }
    public boolean isOpen(int row , int col){
        exception(row,col);
        return elements[row - 1][col - 1];
    }
    public boolean isFull(int row , int col){
        if((row > 0 && row <= size) && (col > 0 && col <= size)){
            return qf.find(top_element) == qf.find(xyto1D(row,col));
        }
        else{
            throw new IllegalArgumentException();
        }
    }
    private void exception (int row , int col){
        if (row <= 0  || col <= 0){
            throw new IllegalArgumentException("row or col is out of the boundaries");
        }
        else if(row > size || col > size){
            throw new ArrayIndexOutOfBoundsException("the index is out of boundaries");

        }
    }
    private int xyto1D(int row , int col){
        return size * (row - 1) + col;
    }
    public int numberOfOpenSites(){
        return open_sites;
    }
    public boolean percolates(){
        return qf.find(top_element) == qf.find(bottom_element);
    }
    public static void main(String[] args) {
    }
}
