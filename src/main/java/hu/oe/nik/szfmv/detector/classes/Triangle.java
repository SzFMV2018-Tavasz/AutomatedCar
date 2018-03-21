package hu.oe.nik.szfmv.detector.classes;

import static java.lang.Math.*;
import java.awt.Point;

/**
 * Create Triangle
 */
public class Triangle {
    /**
     * @param start - Start point of the sensor (Point)
     * @param vector_Length -view range of the sensor (Double)
     * @param degree - Angle of view in degrees (Double)
     * @param sensor_rotation - in degrees (Double)
     * @return a Point array with 3 elemtns. First element: start point, second element: A point, third element: B
     */
    public static Point[] Triangle_points(Point start, Double vector_Length, Double degree, Double sensor_rotation) {
        Double new_length = vector_Length / cos(toRadians(degree / 2));
        System.out.print(cos(toRadians(degree / 2)));

        Point A = Get_polar_point(new_length,  90 + degree / 2 + sensor_rotation);
        Point B = Get_polar_point(new_length, 90 - degree / 2 + sensor_rotation);

        A = Move_point(A, start);
        B = Move_point(B, start);

        Point[] triangle = new Point[3];
        triangle[0] = start;
        triangle[1] = A;
        triangle[2] = B;
        return triangle;
    }

    /**
     * @param length - view range of the sensor
     * @param degree - angle of view in degrees
     * @return A point from polar coordinate system
     */
    private static Point Get_polar_point(Double length, Double degree) {
        Double x = (length * cos(toRadians(degree)));
        Double y = (length * sin(toRadians(degree)));
        return new Point((int)round(x.doubleValue()), (int)round(y.doubleValue()));
    }

    /**
     * @param original_point - point in polar coordinate system
     * @param transformation_point - new origo
     * @return the original point with moved coordinates
     */
    private static Point Move_point(Point original_point, Point transformation_point) {
        return new Point(original_point.x + transformation_point.x, original_point.y + transformation_point.y);
    }
}
