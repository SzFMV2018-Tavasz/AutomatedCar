package hu.oe.nik.szfmv.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    private static final double THRESHOLD = 0.0001d;

    @Test
    public void testPixelConverter() {
        assertEquals(7, Utils.convertPixelToMeter(350), THRESHOLD);
    }

    @Test
    public void testMatrixToRadiansConverter() {
        assertEquals(0.5 * Math.PI, Utils.convertMatrixToRadians(0, -1, 1, 0), THRESHOLD);
        assertEquals(1.5 * Math.PI, Utils.convertMatrixToRadians(0, 1, -1, 0), THRESHOLD);
        assertEquals(0.25 * Math.PI, Utils.convertMatrixToRadians(0.7071068286895752, -0.7071068286895752, 0.7071068286895752, 0.7071068286895752), THRESHOLD);
    }

    @Test
    public void testmatrixArrayToRadianConverter() {
        assertEquals(0.5 * Math.PI, Utils.convertMatrixToRadians(new double[][]{{0, -1}, {1, 0}}), THRESHOLD);
        assertEquals(1.5 * Math.PI, Utils.convertMatrixToRadians(new double[][]{{0, 1}, {-1, 0}}), THRESHOLD);
        assertEquals(0.25 * Math.PI, Utils.convertMatrixToRadians(new double[][]{{0.7071068286895752, -0.7071068286895752}, {0.7071068286895752, 0.7071068286895752}}), THRESHOLD);
    }

    @Test
    public void testRadianToDegree() {
        assertEquals(45, Utils.radianToDegree(0.7853981633974484), THRESHOLD);
    }

}
