package hu.oe.nik.szfmv.detector.classes;

import java.lang.*;
import java.awt.Point;

/**
 * Create Triangle
 */
public class Triangle {
    /**
     * @param start - Start point of the sensor (Point)
     * @param vectorLength -view range of the sensor (Double)
     * @param degree - Angle of view in degrees (Double)
     * @param sensorRotation - in degrees (Double)
     * @return a Point array with 3 elemtns. First element: start point, second element: A point, third element: B
     */
    public static Point[] TrianglePoints(Point start, Double vectorLength, Double degree, Double sensorRotation) {
        Double newLength = vectorLength / Math.cos(Math.toRadians(degree / 2));

        Double utilAngle = 90.0;
        Point APoint = GetPolarPoint(newLength, utilAngle + degree / 2 + sensorRotation);
        Point BPoint = GetPolarPoint(newLength, utilAngle- degree / 2 + sensorRotation);

        APoint = MovePoint(APoint, start);
        BPoint = MovePoint(BPoint, start);

        int numberOfPoints = 3;
        Point[] triangle = new Point[numberOfPoints];
        triangle[0] = start;
        triangle[1] = APoint;
        triangle[2] = BPoint;
        return triangle;
    }

    /**
     * @param length - view range of the sensor
     * @param degree - angle of view in degrees
     * @return A point from polar coordinate system
     */
    private static Point GetPolarPoint(Double length, Double degree) {
        Double x = (length * Math.cos(Math.toRadians(degree)));
        Double y = (length * Math.sin(Math.toRadians(degree)));
        return new Point((int)Math.round(x.doubleValue()), (int)Math.round(y.doubleValue()));
    }

    /**
     * @param originalPoint - point in polar coordinate system
     * @param transformationPoint - new origo
     * @return the original point with moved coordinates
     */
    private static Point MovePoint(Point originalPoint, Point transformationPoint) {
        return new Point(originalPoint.x + transformationPoint.x, originalPoint.y + transformationPoint.y);
    }
}
