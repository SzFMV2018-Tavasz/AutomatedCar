package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.sample.SamplePacket;
import hu.oe.nik.szfmv.automatedcar.input.enums.GearEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PowertrainSystemTest {
    private static final Logger LOGGER = LogManager.getLogger(PowertrainSystem.class);
    private VirtualFunctionBus virtualFunctionBus;
    private PowertrainSystem powertrainSystem;
    private SamplePacket samplePacket;

    @Before
    public void registerComponent() {
        this.samplePacket = new SamplePacket();
        this.virtualFunctionBus = new VirtualFunctionBus();
        this.virtualFunctionBus.samplePacket = this.samplePacket;

        this.powertrainSystem = new PowertrainSystem(virtualFunctionBus, -10);
    }

    private void setValues(int gaspedalPosition, int brakepedalPosition, GearEnum gearState) {
        this.samplePacket.setGaspedalPosition(gaspedalPosition);
        this.samplePacket.setBrakepedalPosition(brakepedalPosition);
        this.samplePacket.setGearState(gearState);
    }

    @Test
    public void getRPMWithMaxGaspedalState() {
        setValues(100, 0, GearEnum.D);
        assertEquals(powertrainSystem.getCarSpecifications().getMaxPRM(), this.powertrainSystem.calculateExpectedRPM(this.powertrainSystem.virtualFunctionBus.samplePacket.getGaspedalPosition()));
    }

    @Test
    public void getRPMWithMinGaspedalState() {
        setValues(0, 0, GearEnum.D);
        assertEquals(powertrainSystem.getCarSpecifications().getIdleRPM(), this.powertrainSystem.calculateExpectedRPM(this.powertrainSystem.virtualFunctionBus.samplePacket.getGaspedalPosition()));
    }

    @Test
    public void getRPMWithGaspedalState1() {
        setValues(34, 0, GearEnum.D);
        assertEquals(2516, this.powertrainSystem.calculateExpectedRPM(this.powertrainSystem.virtualFunctionBus.samplePacket.getGaspedalPosition()));
    }

    @Test
    public void getRPMWithGaspedalState2() {
        setValues(71, 0, GearEnum.D);
        assertEquals(5254, this.powertrainSystem.calculateExpectedRPM(this.powertrainSystem.virtualFunctionBus.samplePacket.getGaspedalPosition()));
    }

    @Test
    public void accelerateFullGasForwardTest() throws InterruptedException {
        this.powertrainSystem = new PowertrainSystem(virtualFunctionBus, 0);
        setValues(100, 0, GearEnum.D);

        for (int i = 0; i < 1000; i++) {
            this.powertrainSystem.loopTest();
        }
    }

    @Test
    public void accelerateFullGasReverseTest() throws InterruptedException {
        this.powertrainSystem = new PowertrainSystem(virtualFunctionBus, 0);
        setValues(100, 0, GearEnum.R);

        for (int i = 0; i < 100; i++) {
            this.powertrainSystem.loopTest();
        }
        LOGGER.debug(this.powertrainSystem.virtualFunctionBus.powertrainPacket.getSpeed());
        assertEquals(-4.207843137254901, this.powertrainSystem.virtualFunctionBus.powertrainPacket.getSpeed(), 0.3333);
    }

    @Test
    public void deccelerateFullBrakeTest() throws InterruptedException {
        this.powertrainSystem = new PowertrainSystem(virtualFunctionBus, 35);
        setValues(0, 100, GearEnum.D);

        this.powertrainSystem.loopTest();

        while (this.powertrainSystem.virtualFunctionBus.powertrainPacket.getSpeed() != 0) {
            Thread.sleep(1);
        }
    }

    @Test
    public void GearNTest() throws InterruptedException {
        this.powertrainSystem = new PowertrainSystem(virtualFunctionBus, 0);
        setValues(50, 0, GearEnum.N);

        for (int i = 0; i < 50; i++) {
            this.powertrainSystem.loopTest();
        }
        assertEquals(0, (int) this.powertrainSystem.virtualFunctionBus.powertrainPacket.getSpeed());
    }

    @Test
    public void GearPTest() throws InterruptedException {
        this.powertrainSystem = new PowertrainSystem(virtualFunctionBus, 0);
        setValues(50, 0, GearEnum.N);

        for (int i = 0; i < 50; i++) {
            this.powertrainSystem.loopTest();
        }
        assertEquals(0, (int) this.powertrainSystem.virtualFunctionBus.powertrainPacket.getSpeed());
    }
}