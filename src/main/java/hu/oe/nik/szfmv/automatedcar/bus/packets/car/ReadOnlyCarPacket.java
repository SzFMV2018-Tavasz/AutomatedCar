package hu.oe.nik.szfmv.automatedcar.bus.packets.car;

import java.awt.*;

public interface ReadOnlyCarPacket {
    /**
     * @return AutomatedCar x position
     */
    int getX();

    /**
     * @return AutomatedCar y position
     */
    int getY();

    /**
     * @return AutomatedCar rotation point
     */
    Point getRotationPoint();

    /**
     * @return AutomatedCar rotation in radians
     */
    double getRotation();
}
