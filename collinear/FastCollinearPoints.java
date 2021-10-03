import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
public class FastCollinearPoints {
    private Point[] points;
    private int numberOfSegments;
    private LineSegment[] lineSegments;
    public FastCollinearPoints(Point[] point){
        if(point == null){
            throw new IllegalArgumentException();
        }
        for(int i = 0; i < point.length ; i++){
            if(point[i] == null){
                throw new IllegalArgumentException();
            }
        }
        for (int i = 0; i < point.length - 1; i++) {
            for (int j = i+1; j < point.length; j++) {
                if (point[i].compareTo(point[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        points = new Point[point.length];
        ArrayList<LineSegment> temp_linesegments = new ArrayList<>();
        System.arraycopy(point, 0, points, 0, points.length);
        for(int i = 0; i < points.length ; i++) {
            Arrays.sort(points);
            Arrays.sort(points, points[i].slopeOrder());
            for (int origin = 0, first = 1, last = 2; last < points.length; last++) {
                while (last < points.length && points[origin].slopeTo(points[first]) == points[origin].slopeTo(points[last])) {
                    last++;
                }
                if ((last - first) >= 3 && points[origin].compareTo(points[first]) < 0) {
                    temp_linesegments.add(new LineSegment(points[origin], points[last - 1]));
                }
                first = last;
            }
        }
        lineSegments = temp_linesegments.toArray(new LineSegment[temp_linesegments.size()]);
    }
    public int numberOfSegments(){
        return lineSegments.length;
    }
    public LineSegment[] segments(){
        return lineSegments;
    }
    public static void main(String[] args){
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
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
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
