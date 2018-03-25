package hu.oe.nik.szfmv.detector;

import hu.oe.nik.szfmv.detector.classes.Triangle;

import java.awt.Point;

import org.junit.Assert;
import org.junit.Test;


public class TriangleTest {

    @Test
    public void notNull() {
        Point[] actualOutput = Triangle.trianglePoints(new Point(0, 0), 3.0, 90.0, 0.0);
        Assert.assertNotNull(actualOutput);
    }

    @Test
    public void rotationOf0Degrees() {
        Point[] expectedOutput = new Point[]{new Point(0, 0), new Point(-3, 3), new Point(3, 3)};
        Point[] actualOutput = Triangle.trianglePoints(new Point(0, 0), 3.0, 90.0, 0.0);
        Assert.assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void rotationOf90Degrees() {
        Point[] expectedOutput = new Point[]{new Point(0, 0), new Point(-3, -3), new Point(-3, 3)};
        Point[] actualOutput = Triangle.trianglePoints(new Point(0, 0), 3.0, 90.0, 90.0);
        Assert.assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void rotationOf180Degrees() {
        Point[] expectedOutput = new Point[]{new Point(0, 0), new Point(3, -3), new Point(-3, -3)};
        Point[] actualOutput = Triangle.trianglePoints(new Point(0, 0), 3.0, 90.0, 180.0);
        Assert.assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void rotationOf270Degrees() {
        Point[] expectedOutput = new Point[]{new Point(0, 0), new Point(3, 3), new Point(3, -3)};
        Point[] actualOutput = Triangle.trianglePoints(new Point(0, 0), 3.0, 90.0, 270.0);
        Assert.assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void rotationOf360Degrees() {
        Point[] expectedOutput = new Point[]{new Point(0, 0), new Point(-3, 3), new Point(3, 3)};
        Point[] actualOutput = Triangle.trianglePoints(new Point(0, 0), 3.0, 90.0, 360.0);
        Assert.assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void zeroSensorRange() {
        Point[] expectedOutput = new Point[]{new Point(0, 0), new Point(0, 0), new Point(0, 0)};
        Point[] actualOutput = Triangle.trianglePoints(new Point(0, 0), 0.0, 90.0, 0.0);
        Assert.assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void notOriginSensorPosition() {
        Point[] expectedOutput = new Point[]{new Point(3, 3), new Point(0, 6), new Point(6, 6)};
        Point[] actualOutput = Triangle.trianglePoints(new Point(3, 3), 3.0, 90.0, 0.0);
        Assert.assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void viewAngle120Degrees() {
        Point[] expectedOutput = new Point[]{new Point(0, 0), new Point(-7, 4), new Point(7, 4)};
        Point[] actualOutput = Triangle.trianglePoints(new Point(0, 0), 4.0, 120.0, 0.0);
        Assert.assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void zeroAngleOfView() {
        Point[] actualOutput = Triangle.trianglePoints(new Point(0, 0), 3.0, 0.0, 0.0);
        Point PointA = actualOutput[1];
        Point PointB = actualOutput[2];
        Assert.assertEquals(PointA, PointB);
    }
}
