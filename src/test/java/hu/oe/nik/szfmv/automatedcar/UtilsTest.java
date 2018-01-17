package hu.oe.nik.szfmv.automatedcar;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void testPixelConverter() {
        assertEquals(7, Utils.convertPixelToMeter(350),0.0001d);
    }

    @Test
    public void testMatrixToRadiansConverter() {
        assertEquals(4.71238898038469, Utils.convertMatrixToRadians(0,1,-1,0), 0.0001d);
        assertEquals(0.7853981633974484, Utils.convertMatrixToRadians(0.7071068286895752,-0.7071068286895752,0.7071068286895752,0.7071068286895752), 0.0001d);

        double[][] matrix = {{0, 1}, {-1, 0}};
        assertEquals(4.71238898038468985769, Utils.convertMatrixToRadians(matrix), 0.0001d);
    }

    @Test
    public void testRadianToDegree() {
        assertEquals(45, Utils.radianToDegree(0.7853981633974484),0.0001d);
    }

}
