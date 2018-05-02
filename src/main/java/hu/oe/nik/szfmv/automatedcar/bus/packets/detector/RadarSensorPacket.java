package hu.oe.nik.szfmv.automatedcar.bus.packets.detector;

import hu.oe.nik.szfmv.environment.models.Collidable;

import java.awt.*;
import java.util.List;

public class RadarSensorPacket implements ReadOnlyRadarSensorPacket {

    private static RadarSensorPacket instance;

    private Collidable closestCollidableinLane;

    private Point[] points;

    private List<Collidable> objectApproachingCenterLine;

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

    /**
     * @param points points of the traingle
     */
    public void setPoints(Point[] points) {
        this.points = points;
    }

    /**
     * @param al the collidable objects
     */
    public void setObjectApproachingCenterLine(List<Collidable> al) {
        objectApproachingCenterLine = al;
    }

    @Override
    public Collidable getClosestCollidableObjectinRoadLane() {
        return closestCollidableinLane;
    }

    @Override
    public List<Collidable> getObjectApproachingCenterline() {
        return objectApproachingCenterLine;
    }

    @Override
    public Point[] getTrianglePoints() {
        return points;
    }
}
