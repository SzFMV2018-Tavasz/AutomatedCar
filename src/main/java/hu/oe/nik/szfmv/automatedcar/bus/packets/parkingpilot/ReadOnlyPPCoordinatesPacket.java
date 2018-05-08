package hu.oe.nik.szfmv.automatedcar.bus.packets.parkingpilot;

public interface ReadOnlyPPCoordinatesPacket {
    /**
     * @return true if find a parking place
     */
    boolean getPlaceIsAvailable();
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
