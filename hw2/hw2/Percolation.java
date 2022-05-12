package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
java.lang.IndexOutOfBoundsException

public class Percolation {
   private int N;
   private WeightedQuickUnionUF sites;
    // sites without bottomSite
    private WeightedQuickUnionUF sites2;
    private int topSite;
    private int bottomSite;
    private boolean[][] flagOpen;
    private int numOpen = 0;


   public Percolation(int N){
      if (N <= 0) {
         throw new IllegalArgumentException();
      }
      this.N=N;
      topSite=N*N;
      bottomSite=N * N + 1;

      sites = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 0; i < N; i++) {
            sites.union(topSite, xyTo1D(0, i));
        }
        for (int i = 0; i < N; i++) {
            sites.union(bottomSite, xyTo1D(N - 1, i));
        }

        sites2 = new WeightedQuickUnionUF(N * N + 1);
        for (int i = 0; i < N; i++) {
            sites2.union(topSite, xyTo1D(0, i));
        }

        flagOpen = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                flagOpen[i][j] = false;
            }
        }
   }               // create N-by-N grid, with all sites initially blocked
   public void open(int row, int col)       // open the site (row, col) if it is not open already
   public boolean isOpen(int row, int col)  // is the site (row, col) open?
   public boolean isFull(int row, int col)  // is the site (row, col) full?
   public int numberOfOpenSites()           // number of open sites
   public boolean percolates()              // does the system percolate?
   public static void main(String[] args)   // use for unit testing (not required)
}
}
