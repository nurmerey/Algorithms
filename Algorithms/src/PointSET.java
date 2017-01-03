import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
public class PointSET {
   private SET<Point2D> set;
// construct an empty set of points
   public PointSET() {                               
       set = new SET<Point2D>();
   }
   
// is the set empty?
   public boolean isEmpty() {
        return set.isEmpty();
   }
   
// number of points in the set
   public int size() {
       return set.size();
   }
   
// add the point to the set (if it is not already in the set)
   public void insert(Point2D p) {
       if (p == null) throw new NullPointerException();
       set.add(p);
   }
   
// does the set contain point p?
   public boolean contains(Point2D p) {
       if (p == null) throw new NullPointerException();
       return set.contains(p);
   }
   
// draw all points to standard draw
   public void draw() {
       for (Point2D e: set) {
           e.draw();
       }
   }
   
// all set points that are inside the rectangle
   public Iterable<Point2D> range(RectHV rect) {
       if (rect == null) throw new NullPointerException();
       SET<Point2D> s = new SET<Point2D>();
       for (Point2D p: set) {
           if (rect.contains(p)) {
            s.add(p);   
           } 
       }
       return s;
   }
   
// a nearest neighbor in the set to point p; null if the set is empty
   public Point2D nearest(Point2D p) {
       if (p == null) throw new NullPointerException();
       if (!isEmpty()) {
           Point2D nearest = set.max();
      
           for (Point2D e: set) {
              // check if you found new nearest
              if (e.distanceTo(p) < nearest.distanceTo(p)) {
                  nearest = e;
              }
           }
          return nearest;
      } 
      return null;
   }

// unit testing of the methods (optional)
   public static void main(String[] args) {
       
   }
}