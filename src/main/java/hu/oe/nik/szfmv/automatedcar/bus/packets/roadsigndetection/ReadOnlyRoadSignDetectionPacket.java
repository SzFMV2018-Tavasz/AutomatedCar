package hu.oe.nik.szfmv.automatedcar.bus.packets.roadsigndetection;

import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.models.RoadSign;

import java.awt.*;

public interface ReadOnlyRoadSignDetectionPacket {
    /**
     * @return roadsign to show on dasboard
     */
    RoadSign getRoadSignToShowOnDashboard();
    Point[] getTrianglePoints();
}
