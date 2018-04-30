package hu.oe.nik.szfmv.automatedcar.bus.packets.detector;

import hu.oe.nik.szfmv.environment.models.Collidable;

public class RadarSensorPacket implements ReadOnlyRadarSensorPacket {

    private static RadarSensorPacket instance;

    private Collidable closestCollidableinLane;

    /**
     * singleton
     *
     * @return detectorpacket
     */
    public static RadarSensorPacket getInstance() {
        if (instance == null) {
            instance = new RadarSensorPacket();
        }

        return instance;
    }

    /**
     * @param c the collidable object
     */
    public void setClosestCollidableinLane(Collidable c) {
        closestCollidableinLane = c;
    }

    @Override
    public Collidable getClosestCollidableObjectinRoadLane() {
        return closestCollidableinLane;
    }
}
