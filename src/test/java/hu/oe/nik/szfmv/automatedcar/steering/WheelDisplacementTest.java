package hu.oe.nik.szfmv.automatedcar.steering;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;

public class WheelDisplacementTest extends AutomatedCar {
    public WheelDisplacementTest() {
        super(0,0, null);
    }

    private double FPS;
    private double THRESHOLD;

    @Before
    public void setUp() throws Exception {
        FPS = 24;
        THRESHOLD = 0.0001d;
    }

    @Test
    public void RestingInPlaceTest() {
        double carHeading = 0;
        double wheelHeading = 0;
        double speed = 0;

        Point2D displacement = getFrontWheelDisplacement(carHeading, wheelHeading, speed, FPS);
        double[] displacementArray = {displacement.getX(), displacement.getY()};
        double[] expectedArray = {0,0};

        Assert.assertArrayEquals(expectedArray, displacementArray, THRESHOLD);
    }

    @Test
    public void GoingForwardTest() {
        double carHeading = 0;
        double wheelHeading = 0;

        for(int n = -100; n <= 100; n++) {
            double speed = n;

            double[] position = {0,0};

            //run for 1 second <-> 24 frames
            for(int i = 0 ; i < FPS; i++) {
                Point2D displacement = getFrontWheelDisplacement(carHeading, wheelHeading, speed, FPS);
                double[] displacementArray = {displacement.getX(), displacement.getY()};
                position[0] += displacementArray[0];
                position[1] += displacementArray[1];
            }

            double[] expectedArray = {speed,0};
            Assert.assertArrayEquals(expectedArray, position, THRESHOLD);
        }
    }

    @Test
    public void GoingBackwardTest() {
        double carHeading = 0;
        double wheelHeading = 0;

        for(int n = -100; n <= 100; n++) {
            double speed = n;

            double[] position = {0,0};

            for(int i = 0 ; i < FPS; i++) {
                Point2D displacement = getFrontWheelDisplacement(carHeading, wheelHeading, -speed, FPS);
                double[] displacementArray = {displacement.getX(), displacement.getY()};
                position[0] -= displacementArray[0];
                position[1] -= displacementArray[1];
            }

            double[] expectedArray = {speed,0};
            Assert.assertArrayEquals(expectedArray, position, THRESHOLD);
        }
    }
}
