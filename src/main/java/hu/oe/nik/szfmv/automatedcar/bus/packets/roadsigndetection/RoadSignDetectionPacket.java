package hu.oe.nik.szfmv.automatedcar.bus.packets.roadsigndetection;

import hu.oe.nik.szfmv.environment.models.RoadSign;

public class RoadSignDetectionPacket implements ReadOnlyRoadSignDetectionPacket {

    private RoadSign roadSign;

    public RoadSignDetectionPacket() { }

    @Override
    public RoadSign getRoadSignToShowOnDashboard() {
        return roadSign;
    }

    public void setRoadSignToShowOnDashboard(RoadSign roadSign) {
        this.roadSign = roadSign;
    }
}
