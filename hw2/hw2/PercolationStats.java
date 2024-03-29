package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import java.lang.IllegalArgumentException;
public class PercolationStats {
    private int T;
    private double[] x;
    private Percolation test;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.T = T;
        x = new double[T];

        for (int i = 0; i < T; i++) {
            test = pf.make(N);
            while (!test.percolates()) {
                int randomRow = StdRandom.uniform(N);
                int randomCol = StdRandom.uniform(N);
                test.open(randomRow, randomCol);
            }
            x[i] = (double) test.numberOfOpenSites() / (N * N);
        }
    }


    }// perform T independent experiments on an N-by-N grid
    public double mean() {
        return StdStats.mean(x);
    }// sample mean of percolation threshold
    public double stddev() {
        return StdStats.stddev(x);
    }// sample standard deviation of percolation threshold
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }// low endpoint of 95% confidence interval
    public double confidenceHigh()  {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
