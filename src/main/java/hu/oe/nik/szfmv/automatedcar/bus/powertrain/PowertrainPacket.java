package hu.oe.nik.szfmv.automatedcar.bus.powertrain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PowertrainPacket implements ReadOnlyPowertrainPacket {

    private static final Logger LOGGER = LogManager.getLogger(PowertrainPacket.class);

    private int rpm;
    private double speed;

    /**
     * Constructor for PowertrainPacket
     */
    public PowertrainPacket() { }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public int getRpm() {
        return 0;
    }

    @Override
    public double getSpeed() {
        return 0;
    }
}
