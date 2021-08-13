/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private final static double confidence = 1.96;
    private int experiment_trails;
    private double[] fraction;
    public PercolationStats(int n , int trials){
        if (n <= 0 || trials <= 0){
            throw new IllegalArgumentException("you entered invalid n by n grid and trials inputs");
        }
        experiment_trails = trials;
        fraction = new double[experiment_trails];
        for(int i = 0; i < experiment_trails ; i++){
            Percolation p = new Percolation(n);
            int open_sites = 0;
            while(!p.percolates()){
                int x = StdRandom.uniform(1, n + 1);
                int y = StdRandom.uniform(1, n + 1);
                if(!p.isOpen(x,y)){
                    p.open(x,y);
                    open_sites++;
                }
            }
            fraction[i] = ((double)open_sites / (n * n));

        }
    }
    public double mean(){
        return StdStats.mean(fraction);
    }
    public double stddev(){
        return StdStats.stddev(fraction);
    }
    public double confidenceLo(){
        return (this.mean() - (confidence * this.stddev()) / Math.sqrt(experiment_trails));
    }
    public double confidenceHi(){
        return (this.mean() + (confidence * this.stddev()) / Math.sqrt(experiment_trails));
    }
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n,t);
        System.out.println("mean                    " + "= " + ps.mean());
        System.out.println("stddev                  " + "= " + ps.stddev());
        System.out.println("95% confidence interval " + "= " + "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
