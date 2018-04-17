package hu.oe.nik.szfmv.automatedcar.bus.packets.roadsigndetection;

import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.models.RoadSign;

import java.awt.*;

public class RoadSignDetectionPacket implements ReadOnlyRoadSignDetectionPacket {

    private static RoadSignDetectionPacket instance = null;
    private RoadSign roadSign;
    private Point[] points;

    /** singleton packet
     *
     * @return instance of packet
     */
    public static RoadSignDetectionPacket getInstance() {
        if (instance == null) {
            instance = new RoadSignDetectionPacket();
        }
        return instance;
    }

    @Override
    public RoadSign getRoadSignToShowOnDashboard() {
        return roadSign;
    }

    public void setRoadSignToShowOnDashboard(RoadSign roadSign) {
        this.roadSign = roadSign;
    }

    public void setTrianglePoints(Point[] points) {
        this.points = points;
    }

    public Point[] getTrianglePoints() {
        return points;
    }
}
