package hu.oe.nik.szfmv.common;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class SortVertices {

    public List<Point2D> sortClockWise(List<Point2D> points) {
        List<Point2D> mTempShape = new ArrayList<>();
        // First Calculate Center of Points
        double centerX = 0;
        double centerY = 0;
        for (int i = 0; i < points.size(); i++) {
            centerX += points.get(i).getX();
            centerY += points.get(i).getY();
        }
        Point2D center = new Point2D.Double(centerX / points.size(), centerY
                / points.size());
        int n = points.size();
        int k;
        for (int m = n; m >= 0; m--) {
            for (int i = 0; i < n - 1; i++) {
                k = i + 1;
                if (!less(points.get(i), points.get(k), center)) {
                    Point2D tmp = new Point2D.Double();
                    tmp = points.get(i);
                    points.set(i, points.get(k));
                    points.set(k, tmp);
                }
            }
        }
        for (int i = 0; i < points.size(); i++) {
            mTempShape.add(points.get(i));
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
