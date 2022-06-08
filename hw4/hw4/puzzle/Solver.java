package hw4.puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Stack;
import java.util.Comparator;
import java.util.Map;
import edu.princeton.cs.algs4.MinPQ;;
public class Solver {
    private class SearchNode {
        private WorldState state;
        private int moves = 0;
        private SearchNode prev;

        public SearchNode(WorldState state, int moves, SearchNode prev) {
            this.state = state;
            this.moves = moves;
            this.prev = prev;
        }

        private class NodeComparator implements Comparator<SearchNode> {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                int o1Edtg = getEdtg(o1);
                int o2Edtg = getEdtg(o2);
                int o1Priority = o1.moves + o1Edtg;
                int o2Priority = o2.moves + o2Edtg;
                return o1Priority - o2Priority;
            }

            private int getEdtg(SearchNode sn) {
                if (!edtgCaches.containsKey(sn.state)) {
                    edtgCaches.put(sn.state, sn.state.estimatedDistanceToGoal());
                }
                return edtgCaches.get(sn.state);
            }
        }
    }
    private Map<WorldState, Integer> edtgCaches = new HashMap<>();
    private List<WorldState> solutions=new ArrayList<>();
    /**
     * Constructor which solves the puzzle, computing everything necessary for
     * moves() and solution() to not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */

    public Solver(WorldState initial){
        MinPQ<SearchNode> minpq = new MinPQ<>(new NodeComparator());
        SearchNode curnode= new SearchNode(initial,0,null);
        minpq.insert(curnode);
        while(!minpq.isEmpty()){
            SearchNode curnode= minpq.delMin();
            if(curnode.state.isGoal()){
                break;
            }
            for(WorldState nextstate: curnode.state.neighbors()){
                SearchNode newnode= new SearchNode(nextstate, curnode.moves + 1, curnode);
                // A critical optimization checks that no enqueued WorldState is its own
                // grandparent
                if(curnode.prev!=null && nextstate.equals(curnode.prev.state)){
                    continue;
                }
                minpq.insert(newnode);
            }
        }
        Stack <WorldState> path= new Stack<>();
        for( SearchNode n = curnode;n!=null; n = n.prev){
            path.push(n.state);
        }
        while(!path.isEmpty()){
            solutions.add(path.pop());
        }
    }

    public int moves(){
        return solutions.size()-1;
    }
    public Iterable<WorldState> solution(){
        return solutions;
    }
}
