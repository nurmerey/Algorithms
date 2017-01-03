import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private static Comparator<SearchNode> priority = new Comparator<SearchNode>() {

        @Override
        public int compare(SearchNode sn1, SearchNode sn2) {
            int sn1Priority = sn1.current.manhattan() + sn1.m;
            int sn2Priority = sn2.current.manhattan() + sn2.m;
            
            if (sn1Priority > sn2Priority) return 1;
            else if (sn1Priority < sn2Priority) return -1;
            else return 0;
        }  
    };
    private MinPQ<SearchNode> minPQ;
    private int moves;
    private boolean twinIsSolvable;
    private SearchNode sn;
    
 // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException();
        this.twinIsSolvable = false;
        this.moves = -1;
        this.minPQ = new MinPQ<SearchNode>(1, priority);
        this.sn = new SearchNode(initial, null, 0);
        minPQ.insert(sn);
        
        // ----------twin
        MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>(1, priority);
        SearchNode twinSn = new SearchNode(initial.twin(), null, 0);
        twinPQ.insert(twinSn);
        
        for (int i = 0; i < 1000; i++) {
            sn = minPQ.delMin(); 
            for (Board b : sn.current.neighbors()) {
                minPQ.insert(new SearchNode(b, sn, sn.m+1));
            }
            
            boolean isGoal = sn.current.isGoal();
//            System.out.println(sn.current);

//            System.out.println("isGoal "+isGoal);
            if (isGoal) {
                moves = sn.m;
//                System.out.println("moves took to solve "+moves);
                break;
            }
            
            //------- checking for the twin --------
            twinSn = twinPQ.delMin(); 
            for (Board p : twinSn.current.neighbors()) {
                twinPQ.insert(new SearchNode(p, twinSn, twinSn.m+1));
            }
         
            boolean twinIsGoal = twinSn.current.isGoal();
//            System.out.println(twinSn.current);
//            System.out.println("twinIsGoal "+twinIsGoal);
            if (twinIsGoal) {
//                System.out.println("twin!!! moves took to solve "+twinSn.m);
                this.twinIsSolvable = true;
                break;
            }
        }
        // create sorted node: prev, size, board
        // node with initial board, prev  = null
        // size = 0.
        // loop forever
        // current node get neighbors
        // put them on PQ
        // comparitor with manhatton and num of moves
    }
    
    
    private class SearchNode {
        private SearchNode prev;
        private int m;
        private Board current;
        public SearchNode(Board board, SearchNode prev, int mvs) {
            this.current = board;
            this.m = mvs;
            this.prev = prev;
        }
    }
    public boolean isSolvable() {
        // is the initial board solvable?
//        System.out.println(this.moves());
        return this.moves() != -1; 
    }
    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        if (this.twinIsSolvable) {
            return -1;
        }
        return moves;
    }
    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
//        minPQ.forEach(action);
        if (isSolvable()) {
            SearchNode current = this.sn;
            Stack<Board> queue = new Stack<Board>();
            while (current != null) {
                queue.push(current.current);
                current = current.prev;
            }
            return queue;
        } else {
            return null;
        }
        
    }
    public static void main(String[] args) {
        // solve a slider puzzle (given below)
       System.out.println("------ Solver --------");
    // create initial board from file
       In in = new In(args[0]);
       int n = in.readInt();
       int[][] blocks = new int[n][n];
       for (int i = 0; i < n; i++)
           for (int j = 0; j < n; j++)
               blocks[i][j] = in.readInt();
       Board initial = new Board(blocks);
       System.out.println("zzzz" + initial.toString());
       // solve the puzzle
       Solver solver = new Solver(initial);

       // print solution to standard output
       if (!solver.isSolvable())
           StdOut.println("No solution possible");
       else {
           StdOut.println("Minimum number of moves = " + solver.moves());
           for (Board board : solver.solution())
               StdOut.println(board);
       }
    }
}