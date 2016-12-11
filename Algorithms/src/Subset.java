import edu.princeton.cs.algs4.StdIn;

public class Subset {
   public static void main(String[] args){
       int k = Integer.parseInt(args[0]);
       RandomizedQueue<String> randQ = new RandomizedQueue<String>();
       while (StdIn.hasNextLine()) {
           String[] input = StdIn.readLine().split(" ");
           for (String s : input) {
               randQ.enqueue(s);
           }
           
           for (int i = 0; i < k; i++) {
               System.out.println(randQ.dequeue());
           }
       }
   }
}