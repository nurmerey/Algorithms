import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;
 // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        ArrayList<LineSegment> tmpSegments = new ArrayList<LineSegment>();
        Point[] tmpPoints = points.clone();
        Arrays.sort(tmpPoints);
        for (int i = 0; i < tmpPoints.length-1; i++) {
            Point p = tmpPoints[i];
            if (p == null) throw new NullPointerException();
            Arrays.sort(tmpPoints, i, tmpPoints.length, p.slopeOrder());
            double slope = p.slopeTo(tmpPoints[i+1]);
            int bingo = 0;
            for (int j = i+2; j < tmpPoints.length; j++) {
                Point q = tmpPoints[j];
                if (p.compareTo(q) == 0) throw new IllegalArgumentException();
                if (Double.compare(slope, p.slopeTo(q)) == 0) {
                    bingo++;
                    if (bingo == 2) {                          
                        Point[] result = new Point[4];
                        result[0] = tmpPoints[j];
                        result[1] = tmpPoints[j-1];
                        result[2] = tmpPoints[j-2];
                        result[3] = tmpPoints[i];
                        Arrays.sort(result);
                        LineSegment ls = new LineSegment(result[0], result[3]);
                        tmpSegments.add(ls);
                    }
                } else {
                    slope = p.slopeTo(q);
                    bingo = 0;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println("segment "+segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
