package hu.oe.nik.szfmv.automatedcar.steering;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SteerAngleTest extends AutomatedCar {

    public SteerAngleTest() {
        super(0,0, null);
    }

    @Test
    public void noSteeringTest() {
        Double angle = null;
        try {
            angle = getSteerAngle(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(new Double(0), angle);
    }

    @Test
    public void fullSteeringTest() {
        Double angle = null;
        try {
            angle = getSteerAngle(-100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(new Double(-60), angle);


        try {
            angle = getSteerAngle(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(new Double(60), angle);

    }

    @Test
    public void loopSteeringTest() {
        for(int n = -100; n <= 100; n += 5) {
            Double angle = null;
            try {
                angle = getSteerAngle(n);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertEquals(new Double(n*0.6),angle);
        }
    }

    @Test
    public void invalidNegativeSteeringTest() {
        Boolean thrown = false;
        try {
            Double angle = getSteerAngle(-100.1);
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void invalidPositiveSteeringTest() {
        Boolean thrown = false;
        try {
            Double angle = getSteerAngle(+100.1);
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

}
