package hu.oe.nik.szfmv.automatedcar.steering;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;

public class CarPositionTest extends AutomatedCar {
    public CarPositionTest() {
        super(0,0, null);
    }

    private double THRESHOLD;

    @Before
    public void setUp() throws Exception {
        THRESHOLD = 0.0001d;
    }

    @Test
    public void LongCarPositionTest() {
        for(int i = -100; i <= 100; i++) {
            Point2D frontWheel = new Point2D.Double(100*i,0);
            Point2D backWheel = new Point2D.Double(-100*i, 0);
            Point2D carPosition = getCarPosition(frontWheel, backWheel);
            double[] carPositionArray = {carPosition.getX(), carPosition.getY()};
            double[] expectedPosition = {0,0};
            Assert.assertArrayEquals(expectedPosition, carPositionArray, THRESHOLD);
        }
    }

    @Test
    public void VerticalCarPositionTest() {
        for(int i = -100; i <= 100; i++) {
            Point2D frontWheel = new Point2D.Double(0,100*i);
            Point2D backWheel = new Point2D.Double(0, -100*i);
            Point2D carPosition = getCarPosition(frontWheel, backWheel);
            double[] carPositionArray = {carPosition.getX(), carPosition.getY()};
            double[] expectedPosition = {0,0};
            Assert.assertArrayEquals(expectedPosition, carPositionArray, THRESHOLD);
        }
    }

    @Test
    public void carPositonWithNorthEastFacingTest() {
        for(int i = -100; i <= 100; i++) {
            Point2D frontWheel = new Point2D.Double(12.2,100*i);
            Point2D backWheel = new Point2D.Double(-12.2, -100*i);
            Point2D carPosition = getCarPosition(frontWheel, backWheel);
            double[] carPositionArray = {carPosition.getX(), carPosition.getY()};
            double[] expectedPosition = {0,0};
            Assert.assertArrayEquals(expectedPosition, carPositionArray, THRESHOLD);
        }
    }

    @Test
    public void carPositonWithSouthWestFacingTest() {
        for(int i = -100; i <= 100; i++) {
            Point2D frontWheel = new Point2D.Double(12.2,-100*i);
            Point2D backWheel = new Point2D.Double(-12.2, 100*i);
            Point2D carPosition = getCarPosition(frontWheel, backWheel);
            double[] carPositionArray = {carPosition.getX(), carPosition.getY()};
            double[] expectedPosition = {0,0};
            Assert.assertArrayEquals(expectedPosition, carPositionArray, THRESHOLD);
        }
    }
}
