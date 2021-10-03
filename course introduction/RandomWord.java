import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
public class RandomWord {
    public static void main(String[] args) {
        double i = 0;
        double p = 0;
        String surviving = "none";
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            i = i + 1;
            p = 1 / i;
            if (StdRandom.bernoulli(p)) {
                surviving = s;
                continue;
            }
            else {
                continue;
            }
        }
        StdOut.println(surviving);
    }

}
