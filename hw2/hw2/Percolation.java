package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IndexOutOfBoundsException;
import java.lang.IllegalArgumentException;

public class Percolation {
   private int N;
   private WeightedQuickUnionUF sites;
    private WeightedQuickUnionUF sites2;// sites without bottomSite
    private int topSite;
    private int bottomSite;
    private boolean[][] flagOpen;
    private int numOpen = 0;


   public Percolation(int N){
        if(N<=0)
            throw new IllegalArgumentException();
        this.N=N;
        flagOpen= new boolean [N][N];
        sites=new WeightedQuickUnionUF(N*N+2);//加两个dummyhead
        sites2=new WeightedQuickUnionUF(N*N+1);//解决水倒帐问题,下面不加dummyhead;
        topSite = -1;
        bottomSite=100000;
        for(int i=0;i<N;i++) {
            sites.union(topSite, i);
            sites2.union(topSite, i);
        }

        for (int i=N*N-1; i>=N*N-N;i-- ){
            sites.union(bottomSite, i);
        }

   }    // create N-by-N grid, with all sites initially blocked

    public int xyTo1D(int r, int c){
        return r*N+c;
    }
    public void open(int row, int col) {
        if(N<=row || row<0 || N<=col || col<0)
            throw new IndexOutOfBoundsException();
        flagOpen[row][col]=true;
        int[][] arr={{1,0},{-1,0},{0,1},{0,-1}};
        for(int i=0;i<4;i++){
            helper(row,col,row+arr[i][0],col+arr[i][1]);
        }
        numOpen++;
   }
   public void helper(int row, int col,int newrow, int newcol) {
       if (newrow <= N - 1 && newrow >= 0 && newcol <= N - 1 && newcol >= 0) {
           if (flagOpen[newrow][newcol]) {
               sites.union(xyTo1D(row, col), xyTo1D(newrow, newcol));
               sites2.union(xyTo1D(row, col), xyTo1D(newrow, newcol));
           }
       }
   }



   public boolean isOpen(int row, int col) {
       if(N<=row || row<0 || N<=col || col<0)
           throw new IndexOutOfBoundsException();
        return flagOpen[row][col];
   }
       // is the site (row, col) open?
   public boolean isFull(int row, int col) {
       if(N<=row || row<0 || N<=col || col<0)
           throw new IndexOutOfBoundsException();

       return sites2.connected(topSite,xyTo1D(row,col));
   } // is the site (row, col) full?
   public int numberOfOpenSites() {
        return numOpen;
   }          // number of open sites
   public boolean percolates()  {
        return sites.connected(topSite,bottomSite);
   }            // does the system percolate?
   public static void main(String[] args){
   }   // use for unit testing (not required)

}
