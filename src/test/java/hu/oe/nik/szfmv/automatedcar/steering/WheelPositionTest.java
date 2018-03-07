package hu.oe.nik.szfmv.automatedcar.steering;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import org.junit.Assert;
import org.junit.Test;

import java.awt.geom.Point2D;

public class WheelPositionTest extends AutomatedCar {
    public WheelPositionTest(){
        super(0,0,null);
    }

    private final double THRESHOLD = 0.0001d;
    private final Point2D carPosition = new Point2D.Double(0,0);
    private final double wheelBase = 100;

    private double eastRotation = Math.toRadians(0);

    @Test
    public void FacingEastFrontWheelTest() {

        Point2D frontWheelPos = getFrontWheel(eastRotation,wheelBase/2, carPosition);
        double[] frontWheelArray = {frontWheelPos.getX(), frontWheelPos.getY()};
        double[] expected = {wheelBase/2, 0d};
        Assert.assertArrayEquals(expected, frontWheelArray, THRESHOLD);
    }

    @Test
    public void FacingEastBackWheelTest() {
        Point2D backWheelPos = getBackWheel(eastRotation,wheelBase/2, carPosition);
        double[] backWheelArray = {backWheelPos.getX(), backWheelPos.getY()};
        double[] expected = {-wheelBase/2, 0d};
        Assert.assertArrayEquals(expected, backWheelArray, THRESHOLD);
    }

    private double northRotation = Math.toRadians(-90);

    @Test
    public void FacingNorthFrontWheelTest() {
        Point2D frontWheelPos = getFrontWheel(northRotation, wheelBase/2, carPosition);
        double[] frontWheelArray = {frontWheelPos.getX(), frontWheelPos.getY()};
        double[] expected = {0, -wheelBase/2 };
        Assert.assertArrayEquals(expected, frontWheelArray, THRESHOLD);
    }

    @Test
    public void FacingNorthBackWheelTest() {
        Point2D backWheelPos = getBackWheel(northRotation, wheelBase/2, carPosition);
        double[] backWheelArray = {backWheelPos.getX(), backWheelPos.getY()};
        double[] expected = {0, wheelBase/2 };
        Assert.assertArrayEquals(expected, backWheelArray, THRESHOLD);
    }

    private double northEastRotation = Math.toRadians(-45);

    @Test
    public void FacingNorthEastFrontWheelTest() {
        Point2D frontWheelPos = getFrontWheel(northEastRotation, wheelBase/2, carPosition);
        double[] frontWheelArray = {frontWheelPos.getX(), frontWheelPos.getY()};
        double[] expected = {35.3553, -35.3553 }; //calc'd using http://www.cleavebooks.co.uk/scol/calrtri.htm
        Assert.assertArrayEquals(expected, frontWheelArray, THRESHOLD);
    }

    @Test
    public void FacingNorthEastBackWheelTest() {
        Point2D backWheelPos = getBackWheel(northEastRotation, wheelBase/2, carPosition);
        double[] backWheelArray = {backWheelPos.getX(), backWheelPos.getY()};
        double[] expected = {-35.3553, +35.3553 }; //calc'd using http://www.cleavebooks.co.uk/scol/calrtri.htm
        Assert.assertArrayEquals(expected, backWheelArray, THRESHOLD);
    }

}
