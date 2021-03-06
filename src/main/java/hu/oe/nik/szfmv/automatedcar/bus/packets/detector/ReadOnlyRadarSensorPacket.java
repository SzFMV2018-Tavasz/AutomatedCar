package hu.oe.nik.szfmv.automatedcar.bus.packets.detector;

import hu.oe.nik.szfmv.environment.models.Collidable;

import java.awt.*;
import java.util.List;

public interface ReadOnlyRadarSensorPacket {
    /**
     * @return closest collidable object in the lane
     */
    Collidable getClosestCollidableObjectinRoadLane();

    /**
     * @return collidable objects approaching center line
     */
    List<Collidable> getObjectApproachingCenterline();

    /**
     * @return points of the radar sensor traingle
     */
    Point[] getTrianglePoints();
}
