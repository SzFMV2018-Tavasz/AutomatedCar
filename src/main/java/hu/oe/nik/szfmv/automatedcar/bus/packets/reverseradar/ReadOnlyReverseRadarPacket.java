package hu.oe.nik.szfmv.automatedcar.bus.packets.reverseradar;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.ReverseRadarState;

public interface ReadOnlyReverseRadarPacket {

    /**
     * Gets distance between back of the car and its near object
     * @return distance
     */
    double getDistance();

    /**
     * Gets the Reverse Radar State (distance > 0.8: OK; distance < 0.8: WARNING; distance < 0.4: DANGER
     * @return state
     */
    ReverseRadarState getReverseRadarState();
}
