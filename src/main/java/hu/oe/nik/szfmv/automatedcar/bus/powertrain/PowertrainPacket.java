package hu.oe.nik.szfmv.automatedcar.bus.powertrain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PowertrainPacket implements ReadOnlyPowertrainPacket {

    private static final Logger LOGGER = LogManager.getLogger(PowertrainPacket.class);

    private int RPM;
    private double speed;

    public PowertrainPacket() {

    }

    public void setRPM(int RPM) {
        this.RPM = RPM;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public int getRPM() {
        return 0;
    }

    @Override
    public double getSpeed() {
        return 0;
    }
}
