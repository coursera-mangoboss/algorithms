import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final double x;
    private final double y;
    // constructs the point (x, y)
    public Point(int x,int y) {
        this.x =(double) x;
        this.y =(double) y;
    }

    // draws this point
    public void draw() {
        StdDraw.point(x,y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point point) {
        StdDraw.line(x,y,point.x,point.y);
    }

    // string representation
    @Override
    public String toString()    {
        return "(" + x + "," + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point point) {
        if(y>point.y || (y==point.y&&x>point.x)) {
            return 1;
        }else {
            if(y==point.y&&x==point.x) {
                return 0;
            }else {
                return -1;
            }
        }
    }
    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (x == that.x) {
            if (y == that.y) {
                return Double.NEGATIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        }
        else if (y == that.y) {
            return 0;
        }
        return ((that.y - y) / (that.x - x));
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new comparator();
    }
    private class comparator implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            double slopeWithP1 = slopeTo(o1);
            double slopeWithP2 = slopeTo(o2);
            if (slopeWithP1 > slopeWithP2) return 1;
            if (slopeWithP2 > slopeWithP1) return -1;
            return 0;
        }
    }
}