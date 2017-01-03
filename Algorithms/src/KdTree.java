import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }

    private Node root;
    private int pointCount;
    // construct an empty set of points
    public KdTree() {                               
        pointCount = 0;
    }

    // is the set empty? 1
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set 2
    public int size() {
        return pointCount;
    }

    // add the point to the set (if it is not already in the set) 3
    public void insert(Point2D p) {
        //        System.out.println("------------insert------------");
        if (p == null) throw new NullPointerException();

        if (isEmpty()) {
            //            System.out.println("is empty "+isEmpty());
            root = new Node();
            root.p = p;
            root.rect = new RectHV(0, 0, 1, 1);
            //            System.out.println("inserted to root point "+p.x() +", "+p.y()+" at level 1");
            pointCount++;
        } else {
            if (!contains(p)) {
                Node current = root;
                int level = 1;
                insertRecursion(current, p, level+1);
                pointCount++;
            } 
        }
    }

    private void insertRecursion(Node current, Point2D p, int level) {
        double pVal;
        double currentVal;
        if (level % 2 == 0) { // if even, then  compare x
            pVal = p.x();
            currentVal = current.p.x();
        } else { // compare y
            pVal = p.y();
            currentVal = current.p.y();
        }
        //        System.out.print("pVal "+pVal);
        //        System.out.println(" currentVal "+currentVal);
        if (pVal < currentVal) { // go left
            if (current.lb == null) {
                current.lb = new Node();
                current.lb.p = p;
                if (level % 2 == 0) { // 1
                    current.lb.rect = new RectHV(0, 0, current.p.x(), 1);
                } else { // 2
                    current.lb.rect = new RectHV(0, 0, 1, current.p.y());
                }
                //                System.out.println("inserted to left point "+p.x() +", "+p.y()+" at level "+level);
                //                System.out.println("inserted to "+current.p.x() +", "+current.p.y());
            } else {
                insertRecursion(current.lb, p, level+1);
            }
        } else { // go right
            if (current.rt == null) {
                current.rt = new Node();
                current.rt.p = p;
                if (level % 2 == 0) { // 3
                    current.rt.rect = new RectHV(current.p.x(), 0, 1, 1);
                } else { // 4
                    current.rt.rect = new RectHV(0, current.p.y(), 1, 1);
                }

                //                System.out.println("inserted to right point "+p.x() +", "+p.y()+" at level "+level);
                //                System.out.println("inserted to "+current.p.x() +", "+current.p.y());
            } else {
                insertRecursion(current.rt, p, level+1);
            }
        }
    }

    // does the set contain point p? 4
    public boolean contains(Point2D p) {
        //        System.out.println("------------contains------------");
        if (p == null) throw new NullPointerException();
        boolean contains = false;
        if (!isEmpty()) {
            //            System.out.println("contains not empty");
            Node current = root;
            contains = containsRecursion(current, p, 2);
        } 
        return contains;
    }

    private boolean containsRecursion(Node current, Point2D p, int level) {
        if (p.equals(current.p)) return true;

        double pVal;
        double currentVal;

        if (level % 2 == 0) { // if even, then  compare x
            pVal = p.x();
            currentVal = current.p.x();
        } else { // compare y
            pVal = p.y();
            currentVal = current.p.y();
        }

        if (pVal < currentVal) {
            if (current.lb != null) {
                if (current.lb.p.equals(p)) {
                    return true;
                } else {
                    return containsRecursion(current.lb, p, level + 1);
                }
            } else {
                return false; // containsRecursion(current.rt, p, level + 1);
            }
        } else {
            if (current.rt != null) {
                if (current.rt.p.equals(p)) {
                    return true;
                } else {
                    return containsRecursion(current.rt, p, level + 1);
                }
            } else {
                return false; // containsRecursion(current.lb, p, level + 1);
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        Node current = root;
        if (!isEmpty()) drawRecursively(current, 1);
    }

    private void drawRecursively(Node current, int level) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        current.p.draw();

        if (level % 2 == 0) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.002);
            StdDraw.line(current.rect.xmin(), current.p.y(), current.rect.xmax(), current.p.y());
        } else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.002);
            StdDraw.line(current.p.x(), current.rect.ymin(), current.p.x(), current.rect.ymax());
        }

        if (current.lb != null) drawRecursively(current.lb, level + 1);
        if (current.rt != null) drawRecursively(current.rt, level + 1);
    }

    // all set points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        Node current = root;
        list = rangeRecursion(current, rect, list);
        return list;
    }

    private ArrayList<Point2D> rangeRecursion(Node current, RectHV rect, ArrayList<Point2D> list) {
        if (rect.contains(current.p)) {
            list.add(current.p);

        } 
        if (current.lb != null) {
            if (current.lb.rect.intersects(rect)) {
                list = rangeRecursion(current.lb, rect, list);
            }
        } 

        if (current.rt != null) {
            if (current.rt.rect.intersects(rect)) {
                list = rangeRecursion(current.rt, rect, list);
            }
        }
        return list;

    }
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException(); // calculate the distance between each node to find the nearest
        if (root == null) {
            return null;
        }
        Point2D nearest = root.p;
        Node current = root;
        nearest = nearestRecursion(current, p, nearest);
        return nearest;
    }

    private Point2D nearestRecursion(Node current, Point2D p, Point2D nearest) {
        if (current.p.distanceTo(p) < nearest.distanceTo(p)) {
            nearest = current.p; 
        }
        if (current.lb != null) {
            if (current.lb.p.distanceTo(p) < nearest.distanceTo(p)) {
                nearest = current.lb.p;
            } 
            nearest = nearestRecursion(current.lb, p, nearest);
        }

        if (current.rt != null) {
            if (current.rt.p.distanceTo(p) < nearest.distanceTo(p)) {
                nearest = current.rt.p;
            } 
            nearest = nearestRecursion(current.rt, p, nearest);
        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        System.out.println("KdTree ----------");

        KdTree kdTree = new KdTree();
        Point2D p = new Point2D(0.7, 0.2);
        Point2D p1 = new Point2D(0.5, 0.4);
        Point2D p2 = new Point2D(0.2, 0.3);
        Point2D p3 = new Point2D(0.4, 0.7);
        Point2D p4 = new Point2D(0.9, 0.6);
        kdTree.insert(p);
        kdTree.insert(p1);
        kdTree.insert(p2);
        kdTree.insert(p3);
        kdTree.insert(p4);
        System.out.println("kdtree p "+kdTree.contains(p));
        System.out.println("kdtree p1 "+kdTree.contains(p1));
        System.out.println("kdtree p2 "+kdTree.contains(p2));
        System.out.println("kdtree p3 "+kdTree.contains(p3));
        System.out.println("kdtree p4 "+kdTree.contains(p4));
        System.out.println("kdtree size "+kdTree.size());
        kdTree.draw();
        //        KdTreeVisualizer viz = new KdTreeVisualizer(); 
        //        System.out.println(viz);
    }
}
