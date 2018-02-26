package hu.oe.nik.szfmv.automatedcar.powertrainsystem;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public PowertrainSystem(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    @Override
    public void loop() {
        //TODO write this
    }

    @Override
    public void receiveSignal(Signal s) {
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

