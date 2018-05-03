package hu.oe.nik.szfmv.automatedcar.bus.packets.reverseradar;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.ReverseRadarState;

public class ReverseRadarPacket implements ReadOnlyReverseRadarPacket {

    private double distance;
    private ReverseRadarState reverseRadarState;
    private Boolean isActive;

    @Override
    public double getDistance() {
        return distance;
    }

    @Override
    public ReverseRadarState getReverseRadarState() {
        return reverseRadarState;
    }

    @Override
    public Boolean getActivation() {
        return isActive;
    }


    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setReverseRadarState(ReverseRadarState reverseRadarState) {
        this.reverseRadarState = reverseRadarState;
    }

    public void setActivation(Boolean activation) {
        isActive = activation;
    }
}
