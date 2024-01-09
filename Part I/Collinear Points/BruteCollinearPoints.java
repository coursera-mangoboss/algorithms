import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private Point[] points;
    private final List<LineSegment> lineSegmentList;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] _points) {
        if (_points==null) {
            throw  new IllegalArgumentException();
        }

        for(int i=0;i<_points.length;++i) {
            if(_points[i]==null) {
                throw new IllegalArgumentException();
            }

            for(int j=0;j<i;++j) {
                if(_points[j].compareTo(_points[i]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        this.points = _points.clone();

        Arrays.sort(points);
        lineSegmentList = new ArrayList<LineSegment>();

        for (int p = 0; p < points.length; p++){
            for (int q = p + 1; q < points.length; q++){
                for (int r = q + 1; r < points.length; r++){
                    for (int s = r + 1; s < points.length; s++){
                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r]) &&
                                points[p].slopeTo(points[r]) == points[p].slopeTo(points[s])){
                            lineSegmentList.add(new LineSegment(points[p], points[s]));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegmentList.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] _lineSegments = new  LineSegment[numberOfSegments()];
        for(int i=0;i<_lineSegments.length;++i) {
            _lineSegments[i] = lineSegmentList.get(i);
        }
        return _lineSegments;
    }
}