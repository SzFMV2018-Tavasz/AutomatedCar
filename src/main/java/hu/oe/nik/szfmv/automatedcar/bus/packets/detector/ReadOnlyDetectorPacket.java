package hu.oe.nik.szfmv.automatedcar.bus.packets.detector;

import hu.oe.nik.szfmv.environment.models.Collidable;

import java.awt.*;

public interface ReadOnlyDetectorPacket {
    /**
     * @return closest collidable object in the lane
     */
    Collidable getClosestCollidableObjectinRoadLane();
}
