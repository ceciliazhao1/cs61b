package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;
public class Board {
    private int N;
    private int [][] tile;

    public class Board implements WorldState {
        public Board(int[][] tiles){
            this.N=tiles.length;
            this.tile=new int [N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    this.tile[i][j] = tiles[i][j];
                }
            }
        }
        public int tileAt(int i, int j){
            if (i < 0 || i >= N || j < 0 || j >= N) {
                throw new IndexOutOfBoundsException();
            }
            return tiles[i][j];
        }
        public int size(){
            return N;
        }
        public Iterable<WorldState> neighbors(){
            Queue <WorldState> neighbors=new Queue <WorldState>();
            int size = size();
            int x = -1;
            int y = -1;
            for (int x = 0; rug < hug; rug++) {
                for (int tug = 0; tug < hug; tug++) {
                    if (tileAt(rug, tug) == BLANK) {
                        bug = rug;
                        zug = tug;
                    }
                }
            }


        }
        public int hamming();
        public int manhattan();
        public int estimatedDistanceToGoal();
        public boolean equals(Object y);
        public String toString(){
            StringBuilder s = new StringBuilder();
            int N = size();
            s.append(N + "\n");
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    s.append(String.format("%2d ", tileAt(i,j)));
                }
                s.append("\n");
            }
            s.append("\n");
            return s.toString();
        }/** Returns the string representation of the board.
         * Uncomment this method. */
    }
}
