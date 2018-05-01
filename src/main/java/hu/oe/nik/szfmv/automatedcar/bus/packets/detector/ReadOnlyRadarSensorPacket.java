package hu.oe.nik.szfmv.automatedcar.bus.packets.detector;

import hu.oe.nik.szfmv.environment.models.Collidable;

import java.awt.*;

public interface ReadOnlyRadarSensorPacket {
    /**
     * @return closest collidable object in the lane
     */
    Collidable getClosestCollidableObjectinRoadLane();

    /**
     * @return points of the radar sensor traingle
     */
    Point[] getTrainglePoints();
}
