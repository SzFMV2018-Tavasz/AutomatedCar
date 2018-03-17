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
    private VirtualFunctionBus virtualFunctionBus;
    private PowertrainSystem powertrainSystem;
    private SamplePacket samplePacket;

    @Before
    public void registerComponent() {
        samplePacket = new SamplePacket();
        virtualFunctionBus = new VirtualFunctionBus();
        virtualFunctionBus.samplePacket = samplePacket;

        powertrainSystem = new PowertrainSystem(virtualFunctionBus, -10);
    }

    private void setValues(int gaspedalPosition, int brakepedalPosition, GearEnum gearState) {
        samplePacket.setGaspedalPosition(gaspedalPosition);
        samplePacket.setBrakepedalPosition(brakepedalPosition);
        samplePacket.setGearState(gearState);
    }

    @Test
    public void getRPMWithMaxGaspedalState() {
        setValues(100, 0, GearEnum.D);
        assertEquals(CarSpecifications.MAX_RPM, powertrainSystem.calculateExpectedRPM(powertrainSystem.virtualFunctionBus.samplePacket.getGaspedalPosition()));
    }

    @Test
    public void getRPMWithMinGaspedalState() {
        setValues(0, 0, GearEnum.D);
        assertEquals(CarSpecifications.IDLE_RPM, powertrainSystem.calculateExpectedRPM(powertrainSystem.virtualFunctionBus.samplePacket.getGaspedalPosition()));
    }

    @Test
    public void getRPMWithGaspedalState1() {
        setValues(34, 0, GearEnum.D);
        assertEquals(2516, powertrainSystem.calculateExpectedRPM(powertrainSystem.virtualFunctionBus.samplePacket.getGaspedalPosition()));
    }

    @Test
    public void getRPMWithGaspedalState2() {
        setValues(71, 0, GearEnum.D);
        assertEquals(5254, powertrainSystem.calculateExpectedRPM(powertrainSystem.virtualFunctionBus.samplePacket.getGaspedalPosition()));
    }

    @Test
    public void accelerateFullGasForwardTest() throws InterruptedException {
        this.powertrainSystem = new PowertrainSystem(virtualFunctionBus, 0);
        setValues(100, 0, GearEnum.D);

        for (int i = 0; i < 3500; i++) {
            //Thread.sleep(25);
            this.powertrainSystem.loopTest();
        }
        assertEquals(118.06446078430348, powertrainSystem.virtualFunctionBus.powertrainPacket.getSpeed(), 0.01);
    }

    @Test
    public void accelerateFullGasReverseTest() throws InterruptedException {
        this.powertrainSystem = new PowertrainSystem(virtualFunctionBus, 0);
        setValues(100, 0, GearEnum.R);

        for (int i = 0; i < 80; i++) {
            //Thread.sleep(25);
            this.powertrainSystem.loopTest();
        }
        assertEquals(-9.336151960784315, powertrainSystem.virtualFunctionBus.powertrainPacket.getSpeed(), 0.01);
    }

    @Test
    public void EngineBrakeTestForward() throws InterruptedException {
        this.powertrainSystem = new PowertrainSystem(virtualFunctionBus, 50);
        setValues(0, 0, GearEnum.D);

        for (int i = 0; i < 3000; i++) {
            //Thread.sleep(25);
            this.powertrainSystem.loopTest();
        }
        assertEquals(1.3888, powertrainSystem.virtualFunctionBus.powertrainPacket.getSpeed(), 0.01);
    }

    @Test
    public void EngineBrakeTestBackward() throws InterruptedException {
        this.powertrainSystem = new PowertrainSystem(virtualFunctionBus, -9);
        setValues(0, 0, GearEnum.R);

        for (int i = 0; i < 3000; i++) {
            //Thread.sleep(25);
            this.powertrainSystem.loopTest();
        }
        assertEquals(-1.3888, powertrainSystem.virtualFunctionBus.powertrainPacket.getSpeed(), 0.01);
    }

    @Test
    public void deccelerateFullBrakeTestForward() throws InterruptedException {
        this.powertrainSystem = new PowertrainSystem(virtualFunctionBus, 118);
        setValues(0, 80, GearEnum.D);

        powertrainSystem.loopTest();

        for (int i = 0; i < 1000; i++) {
            //Thread.sleep(25);
            this.powertrainSystem.loopTest();
        }
        assertEquals(0, powertrainSystem.virtualFunctionBus.powertrainPacket.getSpeed(), 0.01);
    }

    @Test
    public void deccelerateFullBrakeTestBackward() throws InterruptedException {
        this.powertrainSystem = new PowertrainSystem(virtualFunctionBus, -30);
        setValues(0, 80, GearEnum.R);

        powertrainSystem.loopTest();

        for (int i = 0; i < 500; i++) {
            //Thread.sleep(25);
            this.powertrainSystem.loopTest();
        }
        assertEquals(0, powertrainSystem.virtualFunctionBus.powertrainPacket.getSpeed(), 0.01);
    }

    @Test
    public void GearNTest() throws InterruptedException {
        this.powertrainSystem = new PowertrainSystem(virtualFunctionBus, 0);
        setValues(50, 0, GearEnum.N);

        for (int i = 0; i < 50; i++) {
            powertrainSystem.loopTest();
        }
        assertEquals(0, (int) powertrainSystem.virtualFunctionBus.powertrainPacket.getSpeed());
    }

    @Test
    public void GearPTest() throws InterruptedException {
        this.powertrainSystem = new PowertrainSystem(virtualFunctionBus, 0);
        setValues(50, 0, GearEnum.N);

        for (int i = 0; i < 50; i++) {
            this.powertrainSystem.loopTest();
        }
        assertEquals(0, (int) powertrainSystem.virtualFunctionBus.powertrainPacket.getSpeed());
    }
}