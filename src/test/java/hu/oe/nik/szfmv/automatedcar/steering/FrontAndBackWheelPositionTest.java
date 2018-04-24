package hu.oe.nik.szfmv.automatedcar.steering;

import hu.oe.nik.szfmv.automatedcar.SteeringMethods;
import org.junit.Test;

import java.awt.geom.Point2D;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FrontAndBackWheelPositionTest {
    @Test
    public void getFrontAndBackWheelPosition100_100_Front() {
        int speed = 0;
        Point2D point2D = new Point2D.Double(100, 100);
        int[] widthAndHeight = new int[]{10, 10};
        Point2D[] frontAndBack = SteeringMethods.getFrontAndBackWheel(point2D, 0,
                speed, 0, widthAndHeight);
        assertThat(frontAndBack[0].getX(), is(105d));
        assertThat(frontAndBack[0].getY(), is(100d));
    }

    @Test
    public void getFrontAndBackWheelPosition200_200_Front() {
        int speed = 0;
        Point2D point2D = new Point2D.Double(200, 200);
        int[] widthAndHeight = new int[]{50, 50};
        Point2D[] frontAndBack = SteeringMethods.getFrontAndBackWheel(point2D, 0,
                speed, 0, widthAndHeight);
        assertThat(frontAndBack[0].getX(), is(225d));
        assertThat(frontAndBack[0].getY(), is(200d));
    }

    @Test
    public void getFrontAndBackWheelPosition1000_1000_Front() {
        int speed = 0;
        Point2D point2D = new Point2D.Double(1000, 1000);
        int[] widthAndHeight = new int[]{200, 200};
        Point2D[] frontAndBack = SteeringMethods.getFrontAndBackWheel(point2D, 0,
                speed, 0, widthAndHeight);
        assertThat(frontAndBack[0].getX(), is(1100d));
        assertThat(frontAndBack[0].getY(), is(1000d));
    }

    @Test
    public void getFrontAndBackWheelPosition500_500_Back() {
        int speed = 0;
        Point2D point2D = new Point2D.Double(500, 500);
        int[] widthAndHeight = new int[]{200, 200};
        Point2D[] frontAndBack = SteeringMethods.getFrontAndBackWheel(point2D, 0,
                speed, 0, widthAndHeight);
        assertThat(frontAndBack[1].getX(), is(400d));
        assertThat(frontAndBack[1].getY(), is(500d));
    }

    @Test
    public void getFrontAndBackWheelPosition410_200_Back() {
        int speed = 0;
        Point2D point2D = new Point2D.Double(410, 200);
        int[] widthAndHeight = new int[]{100, 100};
        Point2D[] frontAndBack = SteeringMethods.getFrontAndBackWheel(point2D, 0,
                speed, 0, widthAndHeight);
        assertThat(frontAndBack[1].getX(), is(360d));
        assertThat(frontAndBack[1].getY(), is(200d));
    }
}
