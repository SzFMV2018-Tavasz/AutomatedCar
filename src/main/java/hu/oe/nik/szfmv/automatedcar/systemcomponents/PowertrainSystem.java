package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.powertrain.PowertrainPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Powertrain system is responsible for the movement of the car.
 */
public class PowertrainSystem extends SystemComponent implements IPowertrainSystem {

    private static final Logger LOGGER = LogManager.getLogger(PowertrainSystem.class);

    private static final int IDLE_RMP = 650;
    private static final int MAX_RPM = 7400;
    private static final int MAX_SPEED = 190;    // unit: km/h
    private PowertrainPacket powertrainPacket;

    private int gasPedalStatus;
    private int brakePedalStatus;
    private double speed;                           // unit: m/s
    private TransmissionModes transmissionMode;

    /**
     * Creates a powertrain system that connects the Virtual Function Bus
     *
     * @param virtualFunctionBus {@link VirtualFunctionBus} used to connect {@link SystemComponent}s
     */
    public PowertrainSystem(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        LOGGER.debug("PowerTrain SystemComponent has been registered to VirtualFunctionBus.");
        this.powertrainPacket = new PowertrainPacket();
        virtualFunctionBus.powertrainPacket = this.powertrainPacket;
    }

    /**
     * Calculates the acceleration depending on the position of the accelerator pedal
     */
    public void calculateAcceleration() {
        switch (transmissionMode) {
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
    }

    /**
     * Calculates the decceleration depending on the position of the brake pedal
     */
    public void calculateDecceleration() {
        switch (transmissionMode) {
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
        this.gasPedalStatus = virtualFunctionBus.samplePacket.getGaspedalPosition();
        this.brakePedalStatus = virtualFunctionBus.samplePacket.getGaspedalPosition();
        //this.transmissionMode = virtualFunctionBus.samplePacket.getGaspedalPosition();
    }
}

