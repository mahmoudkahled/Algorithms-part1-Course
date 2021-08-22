import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
public class Permutation {
    public static void main(String[] args){
        RandomizedQueue<String> d = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while(!StdIn.isEmpty()){
            d.enqueue(StdIn.readString());
        }
        for(int i = 0; i < k;i++){
            System.out.println(d.dequeue());
        }
    }
}
