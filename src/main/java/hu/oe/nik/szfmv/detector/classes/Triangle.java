package hu.oe.nik.szfmv.detector.classes;

import java.lang.Math;
import java.awt.Point;

/**
 * Create Triangle
 */
public class Triangle {

    private static final Double utilAngle = 90.0;
    private static final int numberOfPoints = 3;
    /**
     * @param start - Start point of the sensor (Point)
     * @param vectorLength -view range of the sensor (Double)
     * @param degree - Angle of view in degrees (Double)
     * @param sensorRotation - in degrees (Double)
     * @return a Point array with 3 elemtns. First element: start point, second element: A point, third element: B
     */
    public static Point[] trianglePoints(Point start, Double vectorLength, Double degree, Double sensorRotation) {
        final Double utilAngle = 90.0;
        final int numberOfPoints = 3;
        Point[] triangle = new Point[numberOfPoints];

        Double newLength = vectorLength / Math.cos(Math.toRadians(degree / 2));

        Point pointA = getPolarPoint(newLength, utilAngle + degree / 2 + sensorRotation);
        Point pointB = getPolarPoint(newLength, utilAngle - degree / 2 + sensorRotation);

        pointA = movePoint(pointA, start);
        pointB = movePoint(pointB, start);
        
        triangle[0] = start;
        triangle[1] = pointA;
        triangle[2] = pointB;
        return triangle;
    }

    /**
     * @param length - view range of the sensor
     * @param degree - angle of view in degrees
     * @return A point from polar coordinate system
     */
    private static Point getPolarPoint(Double length, Double degree) {
        Double x = (length * Math.cos(Math.toRadians(degree)));
        Double y = (length * Math.sin(Math.toRadians(degree)));
        return new Point((int)Math.round(x.doubleValue()), (int)Math.round(y.doubleValue()));
    }

    /**
     * @param originalPoint - point in polar coordinate system
     * @param transformationPoint - new origo
     * @return the original point with moved coordinates
     */
    private static Point movePoint(Point originalPoint, Point transformationPoint) {
        return new Point(originalPoint.x + transformationPoint.x, originalPoint.y + transformationPoint.y);
    }
}
