package hu.oe.nik.szfmv.automatedcar.bus.packets.roadsigndetection;

import hu.oe.nik.szfmv.environment.models.RoadSign;

public class RoadSignDetectionPacket implements ReadOnlyRoadSignDetectionPacket {

    private static RoadSignDetectionPacket instance = null;
    private RoadSign roadSign;

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
}
