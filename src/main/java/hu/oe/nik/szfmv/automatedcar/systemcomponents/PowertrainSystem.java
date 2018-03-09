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

    private static final int MAX_RPM = 7400;
    private static final int IDLE_RPM = 740;
    private static final int MAX_SPEED = 190;    // unit: km/h

    private PowertrainPacket powertrainPacket;
    private int maxRpm = MAX_RPM;
    private int maxSpeed = MAX_SPEED;
    private int idleRpm = IDLE_RPM;
    private int rpm;
    private int gasPedalStatus;
    private int brakePedalStatus;
    private double speed;                           // unit: m/s
    //private GearEnum gearState;

    /**
     * Creates a powertrain system that connects the Virtual Function Bus
     *
     * @param virtualFunctionBus {@link VirtualFunctionBus} used to connect {@link SystemComponent}s
     */
    public PowertrainSystem(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        LOGGER.debug("PowerTrain SystemComponent has been registered to VirtualFunctionBus.");
        this.powertrainPacket = new PowertrainPacket();
        this.speed = 0;
        this.rpm = IDLE_RPM;
        virtualFunctionBus.powertrainPacket = this.powertrainPacket;
    }

    /**
     * Calculates actual speed depending on the position of the accelerator pedal
     */
    public void calculateSpeed() {
        /*
        switch (gearState) {
            case P:
                break;
            case R:
                break;
            case N:
                break;
            case D:
                break;
            default:
                break;
        }
         */
    }

    public int getActualRPM(int rpm) {
        if (rpm == 0) {
            return 740;
        } else {
            double multiplier = (double) MAX_RPM / 100;
            return (int) (rpm * multiplier);
        }
    }

    /**
     * Calculates the decceleration depending on the position of the brake pedal
     */
    public void calculateDecceleration() {
        /*
        switch (gearState) {
            case P:
                break;
            case R:
                break;
            case N:
                break;
            case D:
                break;
            default:
                break;
        }
        */
    }

    @Override
    public void shiftUp() {

    }

    @Override
    public void shiftDown() {

    }

    @Override
    public void loop() {
        //int gasPedal = virtualFunctionBus.samplePacket.getGaspedalPosition();
        //speed = gasPedal * 0.8;

        this.getVirtualFunctionBusSignals();
    }

    @Override
    public void getVirtualFunctionBusSignals() {
        /*
        this.gasPedalStatus = virtualFunctionBus.inputPacket.getGasPedalPosition();
        this.brakePedalStatus = virtualFunctionBus.inputPacket.getGaspedalPosition();
        this.gearState = virtualFunctionBus.inputPacket.getGearState();
        */
    }

    public int getMaxRpm() {
        return maxRpm;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getRpm() {
        return rpm;
    }

    public double getSpeed() {
        return speed;
    }

    public int getIdleRpm() {
        return idleRpm;
    }
}

