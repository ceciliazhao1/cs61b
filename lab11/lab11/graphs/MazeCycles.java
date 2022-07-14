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
    private int s = 0;
    // there should be no edges connecting the part of the graph that doesn’t contain a cycle,
    // so create a temp list to save the original edgeTo info, when find the cycle, copy the needed
    // edges from edgeToTemp to edgeTo then update the maze.
    private int[] edgeToTemp;
    private boolean circleFound=false;
    private Maze maze;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        distTo[s] = 0;
        edgeToTemp = new int[maze.V()];
        edgeToTemp[s] = s;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(s);

    }

    public void dfs(int s){
        marked[s] = true;
        announce();
        for (int w : maze.adj(s)) {
            // check if there is an adjacent w such that w is already visited
            // and w is not parent of s, then there is a cycle in graph.
            if (marked[w] && w != edgeToTemp[s]) {//s的parent是之前dfs的母节点 w!=s的parent
                //If it identifies any cycles, it should connect the vertices of
                // the cycle using purple lines (by setting values in the edgeTo
                // array
                circleFound = true;
                edgeTo[w]=s;//purple line
                //寻找到s的母节点，一个个回溯，然后设为purple line
                while (edgeToTemp[s] != w) {//w!=s的parent
                    edgeTo[s] = edgeToTemp[s];//s的母节点，假设是a,a就设为purple line
                    //edgeTo[w]=s;//purple line at 47行
                    //edgeTo[s]=a;
                    //edgeTo[a]=b;
                    //edgeTo[b]=w;s=w;
                    //当母节点回溯到w之后跳出 edgeToTemp[w]==w
                    s = edgeToTemp[s];//s更新为s的母节点a
                }
                //更新w节点。edgeTo[w]=w
                edgeTo[s] = edgeToTemp[s];

                //call announce() and terminating immediately.
                announce();
                return;
            }

            // All visited vertices should marked, but there should be no edges
            // connecting the part of the graph that doesn’t contain a cycle.
            // Instead, the only edges that should be drawn are the ones connecting
            // the cycle.
            if (!marked[w]) {
                edgeToTemp[w] = s;
                distTo[w] = distTo[s] + 1;
                dfs(w);
                if (circleFound) {
                    return;
                }
            }
        }
    }
}

