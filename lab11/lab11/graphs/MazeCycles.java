package lab11.graphs;
import java.util.Random;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] pathTo;
    private boolean circleFound=false;

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        pathTo=new int[maze.V()];
        //int s=0;
        Random ran=new Random();
        int startX = ran.nextInt(maze.N());
        int startY = ran.nextInt(maze.N());
        int s= maze.xyTo1D(startX,startY);
        pathTo[s]=s;
        dfs(s);
        announce();

    }

    public void dfs(int s){
        marked[s] = true;
        announce();
        for (int w : maze.adj(s)) {
            if (circleFound) {
                return;
            }
            if (!marked[w]) {
                pathTo[w] = s;
                dfs(w);
            }else if (w != pathTo[s]) {
                pathTo[w] = s;

                int cur = s;
                edgeTo[cur] = pathTo[cur];
                while (cur != w) {
                    cur = pathTo[cur];
                    edgeTo[cur] = pathTo[cur];
                }
                circleFound = true;
                return;
            }
        }
    }
}

