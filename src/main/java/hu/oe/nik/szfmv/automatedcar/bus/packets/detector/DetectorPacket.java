package hu.oe.nik.szfmv.automatedcar.bus.packets.detector;

import hu.oe.nik.szfmv.environment.models.Collidable;

public class DetectorPacket implements ReadOnlyDetectorPacket {
    private DetectorPacket instance;

    private Collidable closestCollidableinLane;

    /**
     * singleton
     * @return detectorpacket
     */
    public DetectorPacket getInstance() {
        if (instance == null){
            instance = new DetectorPacket();
        }

        return instance;
    }

    /**
     *
     * @param c the collidable object
     */
    public void setClosestCollidableinLane(Collidable c){
        closestCollidableinLane = c;
    }

    @Override
    public Collidable getClosestCollidableObjectinRoadLane() {
        return closestCollidableinLane;
    }
}
