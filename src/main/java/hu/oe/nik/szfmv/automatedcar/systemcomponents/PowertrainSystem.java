package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Powertrain system is responsible for the movement of the car.
 */
public class PowertrainSystem extends SystemComponent implements IPowertrainSystem {

    private static final Logger LOGGER = LogManager.getLogger(PowertrainSystem.class);

    private static final int IDLE_RMP = 650;
    private static final int MAX_RPM = 7400;
    private static final int MAX_VELOCITY = 190;    // unit: km/h

    private int acceleratorStatus;
    private int brakeStatus;
    private double speed;                           // unit: m/s
    private ITransmissionModes transmissionMode;

    /**
     * Creates a powertrain system that connects the Virtual Function Bus
     *
     * @param virtualFunctionBus {@link VirtualFunctionBus} used to connect {@link SystemComponent}s
     */
    public PowertrainSystem(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        LOGGER.debug("PowerTrain SystemComponent has been registered to VirtualFunctionBus.");
    }

    @Override
    public void loop() {
        int gasPedal = virtualFunctionBus.samplePacket.getGaspedalPosition();
        speed = gasPedal * 0.8;
        //TODO write this
    }

    public double getSpeed() {
        return this.speed;
    }

    @Override
    public void getVirtualFunctionBusSignal() {

    }
}

