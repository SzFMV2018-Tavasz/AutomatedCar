package hu.oe.nik.szfmv.automatedcar.bus.packets.roadsigndetection;

import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.models.RoadSign;

public interface ReadOnlyRoadSignDetectionPacket {
    /**
     * @return roadsign to show on dasboard
     */
    RoadSign getRoadSignToShowOnDashboard();

    /**
     * @param world the world to use its detector
     */
    void setWorld(World world);
}
