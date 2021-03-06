
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
private int n;
private WeightedQuickUnionUF wquf;
private int[] openSites;

    
// create n-by-n grid, with all sites blocked
   public Percolation(int n) {  
       if (n <= 0) throw new IllegalArgumentException("n is less than 0!");
       this.n = n;
       this.wquf = new WeightedQuickUnionUF(this.n*this.n+2);
       this.openSites = new int[this.n*this.n+2];
       this.openSites[0] = 1; // virtual top
       this.openSites[this.n*this.n+1] = 1; // virtual bottom
   }
   
// open site (row, col) if it is not open already
   public void open(int row, int col) {
       this.validIndex(row);
       this.validIndex(col);
       int num = this.rowColTo1D(row, col);
       this.openSites[num] = 1;  
       
       // check if top, left, bottom, and right are open
       if (!this.isTop(num)) { // check top
           if (this.isOpen(row-1, col)) {
               int topNum = this.rowColTo1D(row-1, col);
               if (!this.wquf.connected(num, topNum)) {
                   this.wquf.union(num, topNum);
               }
           }
       }
       if (!this.isBottom(num)) { // check bottom
           if (this.isOpen(row+1, col)) {
               int bottomNum = this.rowColTo1D(row+1, col);
               if (!this.wquf.connected(num, bottomNum)) {
                   this.wquf.union(num, bottomNum);
               }
           }
       }

       if (!this.isLeftMost(num)) { // check left
           if (this.isOpen(row, col-1)) {
               int leftNum = this.rowColTo1D(row, col-1);
               if (!this.wquf.connected(num, leftNum)) {
                   this.wquf.union(num, leftNum);
               }
           }
       }
       if (!this.isRightMost(num)) { // check right
           if (this.isOpen(row, col+1)) {
               int rightNum = this.rowColTo1D(row, col+1);
               if (!this.wquf.connected(num, rightNum)) {
                   this.wquf.union(num, rightNum);
               }
           }
       }
   }

// is site (row, col) open?
   public boolean isOpen(int row, int col) {
       this.validIndex(row);
       this.validIndex(col);
       int num = this.rowColTo1D(row, col);
       if (this.openSites[num] == 1) {
           return true;
       }
       return false;
   }

// is site (row, col) full?
   public boolean isFull(int row, int col)  {
       this.validIndex(row);
       this.validIndex(col);
       int num = this.rowColTo1D(row, col);
       System.out.println("find virtual bottom parent "+row+" "+col+" "+this.wquf.connected(num, this.n*this.n+1));
       System.out.println(this.wquf.find(num));
       if (this.wquf.connected(0, num) && this.isOpen(row, col)) {
           return true;
       }

       
       return false;
   }
   
// does the system percolate?
   public boolean percolates() {
       if (this.wquf.connected(0, this.n*this.n+1)) {
           return true;
       }
       return false;
   }
   
   private boolean isTop(int x) {
       this.validSquareIndex(x);
       if (x <= this.n) {
           this.wquf.union(0, x); // unite with virtual bottom
           return true;
       }
       return false;
   }
   
   private boolean isBottom(int x) {
       this.validSquareIndex(x);
       if (x >= (this.n*(this.n-1)+1)) {
           System.out.println("CONNECTING TO Virtual Bottom! "+x);
           this.wquf.union(x, this.n*this.n+1); // unite with virtual bottom           
           return true;
       }
       return false;
   }
   
   private boolean isLeftMost(int x) {
       this.validSquareIndex(x);
       if (x == 1 || x % this.n == 1) {
           return true;
       }
       return false;
   }
   
   private boolean isRightMost(int x) {
       this.validSquareIndex(x);
       if (x % this.n  == 0) {
           return true;
       }
       return false;
   }
   
   private void validIndex(int index) {
       if (index <= 0 || index > this.n) throw new IndexOutOfBoundsException("row index i out of bounds");
   }
   
   private void validSquareIndex(int index) {
       if (index <= -1 || index > (this.n*this.n+2)) throw new IndexOutOfBoundsException("square index i out of bounds");
   }
   
   private int rowColTo1D(int row, int col) {
       int id = this.n*(row-1)+col;
       return id;
   }
   
   public static void main(String[] args) {
       Percolation p = new Percolation(10);
       System.out.println("hello");
       while(!p.percolates()){
           int row = StdRandom.uniform(1, 10+1);
           int col = StdRandom.uniform(1, 10+1);
           if (!p.isOpen(row, col)) {
               p.open(row, col);
           }
       }
       StdOut.println("find ----"+p.isFull(9, 1));
       StdOut.println("find ----"+p.isFull(10, 1));
       StdOut.println("find ----"+p.isFull(10, 2));
       
       
//       StdOut.println(p.isFull(3, 2));
       StdOut.println("--- percolates");
       StdOut.println(p.percolates());
   }
   
}