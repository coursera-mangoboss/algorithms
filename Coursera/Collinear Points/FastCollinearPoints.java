import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> lineSegments = new ArrayList<LineSegment>();
    private boolean[][] edge;
    private Point[] points;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] _points)  {
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

        Point[] pointsCopySO = Arrays.copyOf(_points, _points.length);
        Point[] pointsCopyNO = Arrays.copyOf(_points, _points.length);
        lineSegments = new ArrayList<LineSegment>();

        Arrays.sort(pointsCopyNO);

        for (int i = 0; i < pointsCopyNO.length; ++i)
        {
            Point origin = pointsCopyNO[i];
            Arrays.sort(pointsCopySO);
            Arrays.sort(pointsCopySO, origin.slopeOrder());
            int count = 1;
            Point lineBeginning = null;
            for (int j = 0; j < pointsCopySO.length - 1; ++j)
            {
                if (pointsCopySO[j].slopeTo(origin) == pointsCopySO[j + 1].slopeTo(origin))
                {
                    count++;
                    if (count == 2)
                    {
                        lineBeginning = pointsCopySO[j];
                        count++;
                    }
                    else if (count >= 4 && j + 1 == pointsCopySO.length - 1)
                    {
                        if (lineBeginning.compareTo(origin) > 0)
                        {
                            lineSegments.add(new LineSegment(origin, pointsCopySO[j + 1]));
                        }
                        count = 1;
                    }
                }
                else if (count >= 4)
                {
                    if (lineBeginning.compareTo(origin) > 0) {
                        lineSegments.add(new LineSegment(origin, pointsCopySO[j]));
                    }
                    count = 1;
                }
                else {
                    count = 1;
                }
            }
        }
    }
    
    // the number of line segments
    public int numberOfSegments()  {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] _lineSegments = new  LineSegment[numberOfSegments()];
        for(int i=0;i<lineSegments.size();++i) {
            _lineSegments[i] = lineSegments.get(i);
        }
        return _lineSegments;
    }
}