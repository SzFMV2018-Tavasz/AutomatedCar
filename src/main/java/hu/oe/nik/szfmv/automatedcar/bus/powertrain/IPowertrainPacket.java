package hu.oe.nik.szfmv.automatedcar.bus.powertrain;

public interface IPowertrainPacket {
    int getRPM();
    void setRPM(int rpm);
    double getSpeed();
    void setSpeed(double speed);
}
