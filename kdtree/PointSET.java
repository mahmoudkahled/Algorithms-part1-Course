import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> points;
    // construct an empty set of points
    public PointSET() {
        points = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size(){
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p){
        if(p == null){
            throw new IllegalArgumentException();
        }
        if(!points.contains(p)){
            points.add(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p){
        if(p == null){
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw(){
        for(Point2D point : points){
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect){
        if(rect == null){
            throw new IllegalArgumentException();
        }
        Queue<Point2D> queue = new Queue<>();
        for(Point2D point : points){
            if(rect.contains(point)){
                queue.enqueue(point);
            }
        }
        return queue;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p){
        if(p == null){
            throw new IllegalArgumentException();
        }
        if(isEmpty()){
            return null;
        }
        double distance = Double.POSITIVE_INFINITY;
        Point2D nearest_point = new Point2D(0,0);
        for (Point2D point : points){
            if(p.distanceSquaredTo(point) < distance){
                distance = p.distanceSquaredTo(point);
                nearest_point = point;
            }
        }
        return new Point2D(nearest_point.x() , nearest_point.y());
    }

    // unit testing of the methods (optional)
    public static void main(String[] args){

    }
}

