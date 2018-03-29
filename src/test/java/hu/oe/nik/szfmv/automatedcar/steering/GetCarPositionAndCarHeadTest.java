package hu.oe.nik.szfmv.automatedcar.steering;

import hu.oe.nik.szfmv.automatedcar.SteeringMethods;
import org.junit.Test;

import java.awt.geom.Point2D;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GetCarPositionAndCarHeadTest {
    @Test
    public void getFrontAndBackWheelPosition100_100_Position() {
        int speed = 0;
        Point2D point2D = new Point2D.Double(100, 100);
        int[] widthAndHeight = new int[]{10, 10};
        Object[] o = SteeringMethods.getCarPositionAndCarHead(point2D, 0, speed, 0, widthAndHeight);

        assertThat(((Point2D) o[0]).getX(), is(105d));
        assertThat(((Point2D) o[0]).getY(), is(105d));
    }

    @Test
    public void getFrontAndBackWheelPosition250_200_Position() {
        int speed = 0;
        Point2D point2D = new Point2D.Double(250, 200);
        int[] widthAndHeight = new int[]{50, 20};
        Object[] o = SteeringMethods.getCarPositionAndCarHead(point2D, 0, speed, 0, widthAndHeight);

        assertThat(((Point2D) o[0]).getX(), is(275d));
        assertThat(((Point2D) o[0]).getY(), is(210d));
    }

    @Test
    public void getFrontAndBackWheelPosition1000_500_Head() {
        int speed = 0;
        Point2D point2D = new Point2D.Double(1000, 500);
        int[] widthAndHeight = new int[]{10, 10};
        Object[] o = SteeringMethods.getCarPositionAndCarHead(point2D, 0, speed, 0, widthAndHeight);

        assertThat(o[1], is(0d));
    }

    @Test
    public void getFrontAndBackWheelPosition220_150_Head() {
        int speed = 0;
        Point2D point2D = new Point2D.Double(220, 150);
        int[] widthAndHeight = new int[]{50, 50};
        Object[] o = SteeringMethods.getCarPositionAndCarHead(point2D, 0, speed, 0, widthAndHeight);

        assertThat(o[1], is(0d));
    }
}
