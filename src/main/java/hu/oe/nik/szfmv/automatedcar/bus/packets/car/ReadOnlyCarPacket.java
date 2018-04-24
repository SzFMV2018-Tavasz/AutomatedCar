package hu.oe.nik.szfmv.automatedcar.bus.packets.car;

public interface ReadOnlyCarPacket {
    /**
     * @return AutomatedCar x position
     */
    double getX();

    /**
     * @return AutomatedCar y position
     */
    double getY();

    /**
     * @return AutomatedCar rotation in radians
     */
    double getRotation();
}
