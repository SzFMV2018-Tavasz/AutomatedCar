package hu.oe.nik.szfmv.automatedcar.steering;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;

public class CarHeadingTest extends AutomatedCar {
    public CarHeadingTest() {
        super(0,0, null);
    }

    private double THRESHOLD;

    @Before
    public void setUp() throws Exception {
        THRESHOLD = 0.0001d;
    }

    @Test
    public void FacingEastTest() {
        double expectedDegs = 0;

        Point2D frontWheel = new Point2D.Double(50,0);
        Point2D backWheel = new Point2D.Double(-50,0);
        double heading = Math.toDegrees(getCarHeading(frontWheel, backWheel));
        Assert.assertEquals(0, heading, THRESHOLD);
    }

    @Test
    public void FacingSouthTest() {
        double expectedDegs = 90;

        Point2D frontWheel = new Point2D.Double(0,50);
        Point2D backWheel = new Point2D.Double(-0,-50);
        double heading = Math.toDegrees(getCarHeading(frontWheel, backWheel));
        Assert.assertEquals(expectedDegs, heading, THRESHOLD);
    }

    @Test
    public void FacingSouthWestTest() {
        double expectedDegs = 135;

        Point2D frontWheel = new Point2D.Double(-35.3553, 35.3553); //calc'd using http://www.cleavebooks.co.uk/scol/calrtri.htm
        Point2D backWheel = new Point2D.Double(35.3553,-35.3553);
        double heading = Math.toDegrees(getCarHeading(frontWheel, backWheel));
        Assert.assertEquals(expectedDegs, heading, THRESHOLD);
    }
}
