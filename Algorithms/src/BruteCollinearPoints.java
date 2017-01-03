import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        ArrayList<LineSegment> tmpSegments = new ArrayList<LineSegment>();
        Point[] tmpPoints = points.clone();
        Arrays.sort(tmpPoints);
        for (int i = 0; i < tmpPoints.length; i++) {
            Point p = tmpPoints[i];
            if (p == null) throw new NullPointerException();
            for (int j = i+1; j < tmpPoints.length; j++) {
                Point q = tmpPoints[j];
                if (p.compareTo(q) == 0) throw new IllegalArgumentException();
                double pSlope = p.slopeTo(q);
                for (int k = j+1; k < tmpPoints.length; k++) {
                    Point r = tmpPoints[k];
                    double qSlope = q.slopeTo(r);
                    for (int f = k+1; f < tmpPoints.length; f++) {
                        Point s = tmpPoints[f];
                        double rSlope = r.slopeTo(s);
                        if (Double.compare(pSlope, qSlope) == 0 && Double.compare(qSlope, rSlope) == 0) {
                            LineSegment ls = new LineSegment(p, s);
                            if (!tmpSegments.contains(ls)) {
                                tmpSegments.add(ls);
                            }   
                        }
                    }
                }
            }
        }
        this.lineSegments = tmpSegments.toArray(new LineSegment[tmpSegments.size()]);    
    }


    // the number of line segments
    public int numberOfSegments() {
        return this.lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return this.lineSegments.clone();
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            StdOut.println("adding point "+points[i].toString());
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println("segment "+segment);
            segment.draw();
        }
        StdDraw.show();

    }

}
