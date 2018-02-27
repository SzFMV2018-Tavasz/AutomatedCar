package hu.oe.nik.szfmv.automatedcar.powertrainsystem;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static hu.oe.nik.szfmv.automatedcar.bus.SignalEnum.TESTSIGNAL;

public class PowertrainSystem extends SystemComponent {

    private static final Logger LOGGER = LogManager.getLogger();

    // signal id table for PowertrainSystem

    // input signals
    private int gasPedal = 0;

    // Output signals
    // Only these are available trough getters
    private int x = 0;
    private int y = 0;
    private double wheelAngle = 0;

    /**
     * Creates a powertrain system that is a {@link SystemComponent} containing the x and y coordinates of the car.
     * These coordinates are to be controlled by thi class.
     *
     * @param x x coordinate of the car
     * @param y y coordinate of the car
     */
    public PowertrainSystem(int x, int y, VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        this.x = x;
        this.y = y;

        subscribeOnSignal(TESTSIGNAL);
    }

    @Override
    public void loop() {
        //TODO write this
    }

    @Override
    public void receiveSignal(Signal<Integer> s) {
        switch (s.getId()) {

            // Handle demo signal
            case TESTSIGNAL:
                x += (int) s.getData();
                LOGGER.debug(x);
                break;

            default:
                // ignore other signals
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getWheelAngle() {
        return wheelAngle;
    }

    public int getGasPedal() {
        return gasPedal;
    }
}

