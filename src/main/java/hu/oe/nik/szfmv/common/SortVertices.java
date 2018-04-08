package hu.oe.nik.szfmv.common;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class SortVertices {

    public List<Point2D> SortClockWise(List<Point2D> Points) {
        List<Point2D> mTempShape = new ArrayList<>();
        // First Calculate Center of Points
        double CenterX = 0;
        double CenterY = 0;
        for (int i = 0; i < Points.size(); i++) {
            CenterX += Points.get(i).getX();
            CenterY += Points.get(i).getY();
        }
        Point2D Center = new Point2D.Double(CenterX / Points.size(), CenterY
                / Points.size());
        int n = Points.size();
        int k;
        for (int m = n; m >= 0; m--) {
            for (int i = 0; i < n - 1; i++) {
                k = i + 1;
                if (!less(Points.get(i), Points.get(k), Center)) {
                    Point2D tmp = new Point2D.Double();
                    tmp = Points.get(i);
                    Points.set(i, Points.get(k));
                    Points.set(k, tmp);
                }
            }
        }
        for (int i = 0; i < Points.size(); i++) {
            mTempShape.add(Points.get(i));
        }
        return mTempShape;
    }

    private Boolean less(Point2D a, Point2D b, Point2D center) {
        if (a.getX() - center.getX() >= 0 && b.getX() - center.getX() < 0) {
            return true;
        }
        if (a.getX() - center.getX() < 0 && b.getX() - center.getX() >= 0) {
            return false;
        }
        if (a.getX() - center.getX() == 0 && b.getX() - center.getX() == 0) {
            if (a.getY() - center.getY() >= 0 || b.getY() - center.getY() >= 0) {
                return (a.getY() > b.getY());
            }
            return b.getY() > a.getY();
        }

        // compute the cross product of vectors (center -> a) x (center -> b)
        double det = (a.getX() - center.getX()) * (b.getY() - center.getY())
                - (b.getX() - center.getX()) * (a.getY() - center.getY());
        if (det < 0) {
            return true;
        }
        if (det > 0) {
            return false;
        }

        // points a and b are on the same line from the center
        // check which point is closer to the center
        double d1 = (a.getX() - center.getX()) * (a.getX() - center.getX())
                + (a.getY() - center.getY()) * (a.getY() - center.getY());
        double d2 = (b.getX() - center.getX()) * (b.getX() - center.getX())
                + (b.getY() - center.getY()) * (b.getY() - center.getY());
        return d1 > d2;
    }
}
