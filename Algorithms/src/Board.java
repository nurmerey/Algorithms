import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private int[][] blocks;
    private int n;
    
    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
     // (where blocks[i][j] = block in row i, column j)
        int[][] newBlocks = blocks.clone();
        this.blocks = newBlocks;
        this.n = blocks.length;
    }
                                           
    
    public int dimension() {
        // board dimension n
        return this.n;
    }       
    
    public int hamming() {
        // number of blocks out of place
        int count = 1;
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
//                System.out.println("test " + blocks[i][j]);
//                System.out.println("should be "+ count);
                if (blocks[i][j] != 0) {
                    if (blocks[i][j] != count) {
                        hamming++;
//                        System.out.println("match! "+ hamming);
                    }
                }
                count++;
            }
        }
        
        return hamming;
    }
    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int block = blocks[i][j];
                if (block != 0) {
                    int fi = block / n; // divide to show row
                    int fj = (block % n)-1; // mod to show column
                    if (block % n == 0) { // edge case: if its divisible by n
                        fj = n-1;
                        fi = fi - 1;
                    }
//                    System.out.println("i: " + i + " & j: "+j);
//                    System.out.println(" should be --> i: " + f_i + " & j: "+f_j);
                    int shouldMove = Math.abs(i - fi) + Math.abs(j - fj);
//                    System.out.println("should move : "+shouldMove);
//                    System.out.println("-------------");
                    count += shouldMove;
                }
            }
        }
        return count;
    }
    public boolean isGoal() {
        // is this board the goal board?
        return hamming() == 0;
    }
    
    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        int firstHalf = (n*n-1)/2;
        int rand = 1;
        if (firstHalf != 1) {
            rand = StdRandom.uniform(1, firstHalf-1);
        } else {
            firstHalf = 2;
        }
        int last = StdRandom.uniform(firstHalf, n*n-1);
        
//        System.out.println("random num "+rand);
//        System.out.println("last num "+last);
        int[][] twin = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int block = blocks[i][j];
                if (block == last) {
                    twin[i][j] = rand;
                } else if (block == rand) {
                    twin[i][j] = last;
                } else {
                    twin[i][j] = block;
                }
            }   
        }
        return new Board(twin);
    }
    public boolean equals(Object y) {
        // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return (this.n == that.n) && Arrays.deepEquals(this.blocks, that.blocks);
    }
    public Iterable<Board> neighbors() {
        // all neighboring boards: Stack<Board> or Queue<Board>
        Stack<Board> stack = new Stack<Board>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int block = blocks[i][j];
//                System.out.println("block "+ block);
                if (block == 0) {
//                    System.out.println("i: " + i + " & j: "+j);
                    if (i > 0) { // top
//                        System.out.println("has top: "+blocks[i-1][j]);
                        stack.push(getNeighbor(blocks[i-1][j]));
                    }
                    if (j > 0) { // left
//                        System.out.println("has left: "+blocks[i][j-1]);
                        stack.push(getNeighbor(blocks[i][j-1]));
                    }
                    if (j < n-1) { // right
//                        System.out.println("has right: "+blocks[i][j+1]);
                        stack.push(getNeighbor(blocks[i][j+1]));
                    }
                    if (i < n-1) { // bottom
//                        System.out.println("has bottom: "+blocks[i+1][j]);
                        stack.push(getNeighbor(blocks[i+1][j]));
                    }
                }
            }
        }
        return stack;
    }
    
    private Board getNeighbor(int num) {
//        System.out.println("getNeighbour num "+num);
        int[][] neighbor = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int block = blocks[i][j];
                if (block == 0) {
                    neighbor[i][j] = num;
                } else if (block == num) {
                    neighbor[i][j] = 0;
                } else {
                    neighbor[i][j] = block;
                }
            }   
        }
        return new Board(neighbor);
    }
    
    public String toString() {
        // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    

    public static void main(String[] args) {
        // unit tests (not graded)
        System.out.println("------ Board --------");
        
     // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        System.out.println(initial.toString());
        System.out.println("hamming --> " + initial.hamming());
        System.out.println("is goal --> " + initial.isGoal());
//        System.out.println("manhattan --> " + initial.manhattan());
//        System.out.println("twin --> "+initial.twin().toString());
//        System.out.println("equal --> "+initial.equals(initial));
//        Board twin = initial.getNeighbor(4);
//        Board twin = initial.twin();
//        System.out.println(twin.toString());
//        System.out.println("neighbors --> "+twin.neighbors());
    }
}
