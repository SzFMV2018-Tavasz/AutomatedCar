package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.powertrain.PowertrainPacket;
//import hu.oe.nik.szfmv.automatedcar.input.enums;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Powertrain system is responsible for the movement of the car.
 */
public class PowertrainSystem extends SystemComponent implements IPowertrainSystem {

    private static final Logger LOGGER = LogManager.getLogger(PowertrainSystem.class);
    private static final double WIND_RESISTANCE = 1.5;
    private static final double REFRESH_RATE = 40;  // 1 sec / 0.025 sec

    private PowertrainPacket powertrainPacket;
    private CarSpecifications carSpecifications;

    private int expectedRPM;
    private int actualRPM;
    private int gasPedalStatus;
    private int brakePedalStatus;
    private double speed;                        // Unit: m/s
    //private GearEnum gearState;
    private int shiftLevel;
    // is a unit vector which reflects the car's orientation,
    private double orientationVector;

    /**
     * Creates a powertrain system that connects the Virtual Function Bus
     *
     * @param virtualFunctionBus {@link VirtualFunctionBus} used to connect {@link SystemComponent}s
     */
    public PowertrainSystem(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        virtualFunctionBus.powertrainPacket = this.powertrainPacket;
        this.powertrainPacket = new PowertrainPacket();

        this.carSpecifications = new CarSpecifications();

        this.speed = 0;
        this.expectedRPM = carSpecifications.getIdleRPM();
        this.actualRPM = carSpecifications.getIdleRPM();

        this.calculateSpeedDifference();
    }

    /**
     * Calculates the speed difference considering the gas pedal position, actual shift level, actual RPM,
     * and gear ratios
     * Based on: http://www.asawicki.info/Mirror/Car%20Physics%20for%20Games/Car%20Physics%20for%20Games.html
     *
     * @return speed difference in m/s
     */
    private double calculateSpeedDifference() {
        boolean isAccelerate = this.actualRPM > this.expectedRPM;
        double speedDelta;

        if (isAccelerate) {
            speedDelta = this.orientationVector * (this.actualRPM
                    * carSpecifications.getGearRatios().get(this.shiftLevel)
                    / (carSpecifications.getWeight() * WIND_RESISTANCE));
        } else {
            speedDelta = -1 * this.orientationVector * (double) carSpecifications.getEngineBreakTorque()
                    * WIND_RESISTANCE / 150;
        }

        LOGGER.debug("IsAccelerate: " + isAccelerate);
        LOGGER.debug("Speed difference (per sec): " + speedDelta);
        LOGGER.debug("Shift level: " + this.shiftLevel);
        LOGGER.debug("Actual RPM: " + this.actualRPM);

        return speedDelta / REFRESH_RATE;
    }

    /**
     * Calculates the RPM considering the gas pedal position
     *
     * @param gaspedalPosition pas pedal position value
     * @return actual RPM
     */
    public int calculateExpectedRPM(int gaspedalPosition) {
        if (gaspedalPosition == 0) {
            this.powertrainPacket.setRpm(carSpecifications.getIdleRPM());
            return carSpecifications.getIdleRPM();
        } else {
            double multiplier = (double) carSpecifications.getMaxPRM() / 100;
            int actualRpm = (int) (gaspedalPosition * multiplier);
            this.powertrainPacket.setRpm(actualRpm);
            return actualRpm;
        }
    }

    @Override
    public void loop() {
        this.getVirtualFunctionBusSignals();
        this.calculateExpectedRPM(this.gasPedalStatus);
    }

    @Override
    public void getVirtualFunctionBusSignals() {
        /*
        this.gasPedalStatus = virtualFunctionBus.inputPacket.getGasPedalPosition();
        this.brakePedalStatus = virtualFunctionBus.inputPacket.getGaspedalPosition();
        this.gearState = virtualFunctionBus.inputPacket.getGearState();
        */
    }

    public CarSpecifications getCarSpecifications() {
        return carSpecifications;
    }
}

