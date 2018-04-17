package hu.oe.nik.szfmv.automatedcar.bus.packets.roadsigndetection;

import hu.oe.nik.szfmv.environment.models.RoadSign;

import java.awt.*;

public interface ReadOnlyRoadSignDetectionPacket {
    /**
     * @return roadsign to show on dasboard
     */
    RoadSign getRoadSignToShowOnDashboard();

    /**
     *
     * @return points of the sensor triangle
     */
    Point[] getTrianglePoints();
}
