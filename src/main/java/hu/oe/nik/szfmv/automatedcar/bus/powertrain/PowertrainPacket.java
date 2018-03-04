package hu.oe.nik.szfmv.automatedcar.bus.powertrain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PowertrainPacket implements IPowertrainPacket {
    private static final Logger LOGGER = LogManager.getLogger(PowertrainPacket.class);

    private int RPM;
    private double speed;

    public PowertrainPacket() {

    }

    @Override
    public int getRPM() {
        return 0;
    }

    @Override
    public void setRPM(int rpm) {
        this.RPM = rpm;
    }

    @Override
    public double getSpeed() {
        return 0;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
