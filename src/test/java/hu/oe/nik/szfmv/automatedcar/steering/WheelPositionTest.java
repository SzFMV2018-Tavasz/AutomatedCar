package hu.oe.nik.szfmv.automatedcar.steering;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import org.junit.Assert;
import org.junit.Test;

import java.awt.geom.Point2D;

public class WheelPositionTest extends AutomatedCar {
    public WheelPositionTest(){
        super(0,0,null);
    }

    double wheelBase = 100;

    @Test
    public void FacingEastFrontWheelTest() {
        Point2D frontWheelPos = getFrontWheel(0,wheelBase/2, new Point2D.Double(0,0));
        Double[] frontWheelArray = {frontWheelPos.getX(), frontWheelPos.getY()};
        Double[] expected = {50d, 0d};
        Assert.assertArrayEquals(expected, frontWheelArray);
    }

    @Test
    public void FacingEastBackWheelTest() {
        Point2D backWheelPos = getBackWheel(0,wheelBase/2, new Point2D.Double(0,0));
        Double[] backWheelArray = {backWheelPos.getX(), backWheelPos.getY()};
        Double[] expected = {-50d, 0d};
        Assert.assertArrayEquals(expected, backWheelArray);
    }
}
