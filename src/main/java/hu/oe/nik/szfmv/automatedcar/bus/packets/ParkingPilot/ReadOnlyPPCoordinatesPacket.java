package hu.oe.nik.szfmv.automatedcar.bus.packets.ParkingPilot;

public interface ReadOnlyPPCoordinatesPacket {
    /**
     * @return x position founnd by front sensor
     */
    double getFrontX();

    /**
     * @return y position founnd by front sensor
     */
    double getFrontY();

    /**
     * @return x position founnd by back sensor
     */
    double getBackX();

    /**
     * @return y position founnd by back sensor
     */
    double getBackY();
}
