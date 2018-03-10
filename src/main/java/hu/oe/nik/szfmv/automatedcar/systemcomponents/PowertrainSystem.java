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
    private static final double WIND_RESISTANCE = 3.0;
    private static final double REFRESH_RATE = 40;  // 1 sec / 0.025 sec

    private PowertrainPacket powertrainPacket;
    private CarSpecifications carSpecifications;

    private int expectedRPM;
    private int actualRPM;
    private int gasPedalPosition;
    private int brakePedalPosition;
    private double speed;                // Unit: m/s

    /**
     * TODO Only for complie, need to change to "private GearEnum gearState; when merging
     */
    private TestEnum gearState;
    //private GearEnum gearState;
    private int shiftLevel;
    private double orientationVector;    // it is a unit vector which reflects the car's orientation

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

        /**
         * TODO remove this and change to GearEnum
         */
        this.gearState = TestEnum.P;
        // this.gearState = GearEnum.P;
        this.speed = 0;
        this.expectedRPM = carSpecifications.getIdleRPM();
        this.actualRPM = carSpecifications.getIdleRPM();
    }

    /**
     * Calculates the speed difference considering the gas- and brake pedal position, actual shift level, actual RPM,
     * and gear ratios
     * Based on: http://www.asawicki.info/Mirror/Car%20Physics%20for%20Games/Car%20Physics%20for%20Games.html
     *
     * @return speed difference in m/s
     */
    private double calculateSpeedDifference() {
        boolean isAccelerate = this.actualRPM > this.expectedRPM;
        boolean isBraking = ((this.brakePedalPosition > 0) && (this.gasPedalPosition == 0));
        double speedDelta;

        if (isAccelerate) {
            speedDelta = this.orientationVector * (this.actualRPM
                    * carSpecifications.getGearRatios().get(this.shiftLevel)
                    / (carSpecifications.getWeight() * WIND_RESISTANCE));
        } else {
            speedDelta = -1 * this.orientationVector * (double) carSpecifications.getEngineBrakeTorque()
                    * WIND_RESISTANCE / 150;
        }

        if (isBraking) {
            speedDelta = -1 * this.orientationVector * ((carSpecifications.getMaxBrakeSpeed() / 100)
                    * this.brakePedalPosition);
        }

        LOGGER.debug(":: calculateSpeedDifference() method called: { IsAccelerate: " + isAccelerate
                + ", IsBraking: " + isBraking + ", Speed difference (per sec): " + speedDelta
                + ", Shift level: " + this.shiftLevel + ", Actual RPM: " + this.actualRPM + " }");

        return speedDelta / REFRESH_RATE;
    }

    /**
     * Calculates the RPM considering the gas pedal position, and send this value to VirtualFunctionBus
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

    /**
     * Manage the automated gearbox levels
     *
     * @param speedDelta Speed difference input decide to the car is accelerate or slowing down
     */
    private void gearShiftWatcher(double speedDelta) {
        int shiftLevelChange = 0;

        if (speedDelta > 0) {
            while (this.carSpecifications.getGearShiftLevelSpeed().get(this.shiftLevel + shiftLevelChange + 1)
                    <= Math.abs(this.speed)) {
                shiftLevelChange++;
            }
            if ((shiftLevelChange > 0) && (this.shiftLevel < this.carSpecifications.getGearboxMaxLevel())) {
                this.shiftLevel += shiftLevelChange;
                LOGGER.debug(":: gearShiftWatcher() method called: Need to shifting up. New shiftlevel: "
                        + this.shiftLevel);
            }

        }

        if (speedDelta < 0) {
            while (this.carSpecifications.getGearShiftLevelSpeed().get(this.shiftLevel + shiftLevelChange)
                    > Math.abs(this.speed)) {
                shiftLevelChange--;
            }
            if ((shiftLevelChange < 0) && (this.shiftLevel > this.carSpecifications.getGearboxMinLevel())) {
                this.shiftLevel += shiftLevelChange;
                LOGGER.debug(":: gearShiftWatcher() method called: Need to shifting down. New shiftlevel: "
                        + this.shiftLevel);
            }
        }
        LOGGER.debug(":: gearShiftWatcher() method called: Don't need shift.");
    }

    @Override
    public void loop() {
        this.getVirtualFunctionBusSignals();
        this.actualRPM = this.calculateExpectedRPM(this.gasPedalPosition);
        this.doPowertrain();
    }

    /**
     * Modifies the actual speed and send to VirtualFunctionBus
     */
    private void doPowertrain() {
        double speedDelta = this.calculateSpeedDifference();

        switch (this.gearState) {
            case P:
                // Nothing to do
                break;
            case R:
                this.orientationVector = -1;
                this.shiftLevel = 0;

                if (this.brakePedalPosition == 0) {
                    LOGGER.debug(" :: doPowertrain() method called: Slowing down to minimum speed");
                    if ((this.speed > this.carSpecifications.getGearShiftLevelSpeed().get(0) * -1)) {
                        this.speed += speedDelta;
                        this.powertrainPacket.setSpeed(this.speed);
                    }
                } else {
                    LOGGER.debug(" :: doPowertrain() method called: Braking, allow to stop to zero");
                    if (this.speed < 0) {
                        this.speed += speedDelta;
                        this.powertrainPacket.setSpeed(this.speed);
                    }
                }

                break;
            case N:
                // Nothing to do
                break;
            case D:
                this.orientationVector = 1;
                this.shiftLevel = 1;
                this.gearShiftWatcher(speedDelta);

                if (this.brakePedalPosition == 0) {
                    LOGGER.debug(" :: doPowertrain() method called: Slowing down to minimum speed");
                    if ((this.speed < this.carSpecifications.getMaxForwardSpeedInMPS()) &&
                            (this.speed > this.carSpecifications.getMinSpeedInMPS())) {
                        this.speed += speedDelta;
                        this.powertrainPacket.setSpeed(this.speed);
                    }
                } else {
                    LOGGER.debug(" :: doPowertrain() method called: Braking, allow to stop to zero");
                    if (this.speed > 0) {
                        this.speed += speedDelta;
                        this.powertrainPacket.setSpeed(this.speed);
                    }
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void getVirtualFunctionBusSignals() {
        /**
         * TODO remove this comment when merge to master
         this.gasPedalPosition = virtualFunctionBus.inputPacket.getGasPedalPosition();
         this.brakePedalPosition = virtualFunctionBus.inputPacket.getBreakPedalPosition();
         this.gearState = virtualFunctionBus.inputPacket.getGearState();
         */
    }

    public CarSpecifications getCarSpecifications() {
        return carSpecifications;
    }
}

