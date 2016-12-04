import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    
   private double[] timesItTookToPerculate;
   private int size;
   private int trials;
   
   public PercolationStats(int n, int trials){
	  if(n <= 0 || trials <= 0) throw new IllegalArgumentException("n or trials is <= 0");
	  this.size = n*n;
	  this.trials = trials;
	  timesItTookToPerculate = new double[trials];
	  for(int i=0; i<trials; i++){
	      Percolation p = new Percolation(n);
	      double openSiteCount = 0;
	      while(!p.percolates()){
	          p.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1));
	          openSiteCount++;
	      }
	      System.out.println("has finished perculating "+openSiteCount);
	      timesItTookToPerculate[i] = openSiteCount;
	  }
	  System.out.println("double array "+timesItTookToPerculate[0]);
   }
   public double mean(){
	   return StdStats.mean(timesItTookToPerculate)/this.size;
   }
   public double stddev(){
	   return StdStats.stddev(timesItTookToPerculate)/this.size;
   }
   public double confidenceLo(){
       double lo = this.mean()-((1.96*this.stddev())/this.trials);
	   return lo;
   }
   public double confidenceHi(){
       double hi = this.mean()+((1.96*this.stddev())/this.trials);
	   return hi;
   }

   public static void main(String[] args){
       int n = Integer.parseInt(args[0]);
       int t = Integer.parseInt(args[1]);
	   PercolationStats ps = new PercolationStats(n, t);
	   System.out.println("mean                    = "+ps.mean());
	   System.out.println("stddev                  = "+ps.stddev());
	   System.out.println("95% confidence interval = "+ps.confidenceLo()+", "+ps.confidenceHi());
   }
}
