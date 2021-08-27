import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
public class BruteCollinearPoints {
    private Point[] points;
    private LineSegment[] lineSegments;
    private int numberOfSegments = 0;
    public BruteCollinearPoints(Point[] point){
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
        // first sort object to be able to compare with next value
        Arrays.sort(points);
        // add (-3, -2 ...) indent to avoid checking with same points
        for(int x = 0; x < (points.length - 3); x++){
            for (int y = (x + 1); y < (points.length - 2); y++){
                for (int z = (y + 1); z < (points.length - 1); z++){
                    if(points[x].slopeTo(points[y]) == points[x].slopeTo(points[z])){
                        for (int w = (z + 1); w < points.length; w++){
                            if (points[x].slopeTo(points[y]) == points[x].slopeTo(points[w])){
                                numberOfSegments++;
                                temp_linesegments.add(new LineSegment(points[x], points[w]));
                            }
                            else{
                                continue;
                            }
                        }
                    }
                    else {
                        continue;
                    }
                }
            }

        }
        lineSegments = temp_linesegments.toArray(new LineSegment[temp_linesegments.size()]);

    }
    public int numberOfSegments(){
        return numberOfSegments;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        System.out.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
           segment.draw();
        }
        StdDraw.show();
    }
}
