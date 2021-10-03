import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int size;
    private final int VERTICAL = 1;
    private final int HORIZONTAL = 0;

    // construct an empty set of points
    public KdTree() {
    }

    private class Node {
        private Point2D point;
        private Node right, left;

        public Node(Point2D point) {
            this.point = point;
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        root = insert(root, p, VERTICAL);
    }

    private Node insert(Node root, Point2D point, int orientation) {
        if (root == null) {
            size++;
            return new Node(point);
        }
        double difference_x_coordinate = point.x() - root.point.x();
        double difference_y_coordinate = point.y() - root.point.y();
        if(difference_x_coordinate == 0 && difference_y_coordinate == 0){
            return root;
        }
        if (orientation == VERTICAL) {
            if (difference_x_coordinate < 0) {
                root.left = insert(root.left, point, HORIZONTAL);
            } else {
                root.right = insert(root.right, point, HORIZONTAL);
            }
        } else {
            if (difference_y_coordinate < 0) {
                root.left = insert(root.left, point, VERTICAL);
            } else {
                root.right = insert(root.right, point, VERTICAL);
            }
        }
        return root;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            return false;
        }
        return contains(root, p, VERTICAL);
    }

    private boolean contains(Node root, Point2D point, int orientation) {
        if (root == null) {
            return false;
        }
        double difference_x_coordinate = point.x() - root.point.x();
        double difference_y_coordinate = point.y() - root.point.y();
        if (difference_x_coordinate == 0 && difference_y_coordinate == 0) {
            return true;
        }
        if (orientation == VERTICAL) {
            if (difference_x_coordinate < 0) {
                return contains(root.left, point, HORIZONTAL);
            } else {
                return contains(root.right, point, HORIZONTAL);
            }
        } else {
            if (difference_y_coordinate < 0) {
                return contains(root.left, point, VERTICAL);
            } else {
                return contains(root.right, point, VERTICAL);
            }
        }
    }


    // draw all points to standard draw
    public void draw() {
        draw(root, VERTICAL, 0, 0, 1, 1);
        StdDraw.show();
    }

    private void draw(Node root, double orientation, double minX, double minY, double maxX, double maxY) {
        if (root == null) {
            return;
        }
        double x_value = root.point.x();
        double y_value = root.point.y();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(x_value, y_value);
        if (orientation == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(x_value, minY, x_value, maxY);
            draw(root.left, HORIZONTAL, minX, minY, x_value, maxY);
            draw(root.right, HORIZONTAL, x_value, minY, maxX, maxY);

        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(minX, y_value, maxX, y_value);
            draw(root.left, VERTICAL, minX, minY, maxX, y_value);
            draw(root.right, VERTICAL, minX, y_value, maxX, maxY);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Queue<Point2D> queue = new Queue<>();
        return range(root, rect, queue, VERTICAL);
    }

    private Queue<Point2D> range(Node root, RectHV rect, Queue<Point2D> queue, int orientation) {
        if (root == null) {
            return queue;
        }
        double x_value = root.point.x();
        double y_value = root.point.y();
        if (rect.contains(root.point)) {
            queue.enqueue(root.point);
        }
        if (orientation == VERTICAL) {
            if(x_value <= rect.xmax() && x_value >= rect.xmin()){
                range(root.left, rect, queue, HORIZONTAL);
                range(root.right, rect, queue, HORIZONTAL);
            }
            else if (x_value > rect.xmax()) {
                range(root.left, rect, queue, HORIZONTAL);
            } else if (x_value < rect.xmin()) {
                range(root.right, rect, queue, HORIZONTAL);
            }
        } else {
            if(y_value >= rect.ymin() && y_value <= rect.ymax()){
                range(root.right, rect, queue, VERTICAL);
                range(root.left, rect, queue, VERTICAL);
            }
            else if (y_value < rect.ymin()) {
                range(root.right, rect, queue, VERTICAL);
            } else if (y_value > rect.ymax()) {
                range(root.left, rect, queue, VERTICAL);
            }
        }
        return queue;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return nearest(root, p, null , VERTICAL , new RectHV(0.0 , 0.0 , 1.0 , 1.0));
    }

    private Point2D nearest(Node root, Point2D point, Point2D nearesr_point, int orientation , RectHV rect) {
        if (root == null) {
            return nearesr_point;
        }
        double shortest_distance;
        if (root == this.root) {
            shortest_distance = point.distanceSquaredTo(root.point);
            nearesr_point = point;
        }
        else {
            shortest_distance = point.distanceSquaredTo(nearesr_point);
        }
        if(rect.distanceSquaredTo(point) > shortest_distance){
            return nearesr_point;
        }
        if(rect.distanceSquaredTo(root.point) < shortest_distance){
            nearesr_point = root.point;
        }
        boolean side_orientation;
        if(orientation == VERTICAL){
            side_orientation = point.x() <= root.point.x();
        }
        else{
            side_orientation = point.y() <= root.point.y();
        }
        if(orientation == VERTICAL){
            if (side_orientation){
                RectHV local_rect = new RectHV(rect.xmin(), rect.ymin(), root.point.x() , rect.ymax());
                nearesr_point = nearest(root.left , point , nearesr_point , HORIZONTAL , local_rect);
                local_rect = new RectHV(root.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
                nearesr_point = nearest(root.right , point , nearesr_point , HORIZONTAL , local_rect);

            }
            else{
                RectHV local_rect = new RectHV(root.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
                nearesr_point = nearest(root.right , point , nearesr_point , HORIZONTAL , local_rect);
                local_rect = new RectHV(rect.xmin(), rect.ymin(), root.point.x() , rect.ymax());
                nearesr_point = nearest(root.left , point , nearesr_point , HORIZONTAL , local_rect);
            }
        }
        else {
            if(side_orientation){
                RectHV local_rect = new RectHV(rect.xmin() , rect.ymin(), rect.xmax() , root.point.y());
                nearesr_point = nearest(root.left , point , nearesr_point , VERTICAL ,local_rect);
                local_rect = new RectHV(rect.xmin() , root.point.y(), rect.xmax() , rect.ymax());
                nearesr_point = nearest(root.right , point , nearesr_point , VERTICAL ,local_rect);
            }
            else {
                RectHV local_rect = new RectHV(rect.xmin() , root.point.y(), rect.xmax() , rect.ymax());
                nearesr_point = nearest(root.right , point , nearesr_point , VERTICAL ,local_rect);
                local_rect = new RectHV(rect.xmin() , rect.ymin(), rect.xmax() , root.point.y());
                nearesr_point = nearest(root.left , point , nearesr_point , VERTICAL ,local_rect);
            }
        }
        return nearesr_point;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
