package hu.oe.nik.szfmv.automatedcar.bus.packets.detector;

import hu.oe.nik.szfmv.environment.models.Collidable;

public interface ReadOnlyDetectorPacket {
    Collidable getClosestCollidableObjectinRoadLane();
}
