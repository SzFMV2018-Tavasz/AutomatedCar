package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PowertrainSystemTest {

    private PowertrainSystem pts = new PowertrainSystem(new VirtualFunctionBus());
    private double multiplier = (double) pts.getCarSpecifications().getMaxPRM() / 100;

    private int maxGaspedalState = 100;
    private int minGaspedalState = 0;
    private int GaspedalState1 = 34;
    private int GaspedalState2 = 71;

    @Test
    public void getRPMWithMaxGaspedalState() {
        assertEquals(7400, pts.calculateExpectedRPM(maxGaspedalState));
    }

    @Test
    public void getRPMWithMinGaspedalState() {
        assertEquals(740, pts.calculateExpectedRPM(minGaspedalState));
    }

    @Test
    public void getRPMWithGaspedalState1() {
        assertEquals(2516, pts.calculateExpectedRPM(GaspedalState1));
    }

    @Test
    public void getRPMWithGaspedalState2() {
        assertEquals(5254, pts.calculateExpectedRPM(GaspedalState2));
    }
}