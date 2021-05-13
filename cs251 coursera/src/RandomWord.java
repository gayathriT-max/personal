import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champ = "";
        int wordcount = 0;
        double prob = 0;
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            wordcount++;
            prob = 1.0 / wordcount;
            if (StdRandom.bernoulli(prob)) {
                champ = str;
            }
        }
        StdOut.println(champ);
    }
}
