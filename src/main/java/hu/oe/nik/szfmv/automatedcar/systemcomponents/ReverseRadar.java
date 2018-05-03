package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.exception.MissingPacketException;
import hu.oe.nik.szfmv.automatedcar.bus.packets.reverseradar.ReverseRadarPacket;
import hu.oe.nik.szfmv.automatedcar.input.enums.GearEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReverseRadar extends SystemComponent {

    private static final Logger LOGGER = LogManager.getLogger(ReverseRadar.class);
    private static final double WARNING_VALUE = 0.8;
    private static final double DANGER_VALUE = 0.4;
    private ReverseRadarPacket reverseRadarPacket;
    private ReverseRadarState reverseRadarState;

    /**
     * @param virtualFunctionBus VirtualFunctuonBus parameter
     */
    public ReverseRadar(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);

        reverseRadarPacket = new ReverseRadarPacket();
        virtualFunctionBus.reverseRadarPacket = reverseRadarPacket;
        reverseRadarState = ReverseRadarState.OK;
        reverseRadarPacket.setReverseRadarState(reverseRadarState);
    }

    @Override
    public void loop() throws MissingPacketException {
        reverseRadarPacket.setActivation(false);
        if (virtualFunctionBus.inputPacket.getGearState() == GearEnum.R) {
            LOGGER.debug("Gear state is: R");
            activateReverseRadar();
        }
    }

    private void activateReverseRadar() {
        reverseRadarPacket.setActivation(true);
        LOGGER.debug("Now the Reverse Radar activated: " + virtualFunctionBus.reverseRadarPacket.getActivation());
        double objectDistance = 0.4;

        if (objectDistance >= 0.0 && objectDistance <= DANGER_VALUE) {
            reverseRadarState = ReverseRadarState.DANGER;
        }
        if (objectDistance > DANGER_VALUE && objectDistance <= WARNING_VALUE) {
            reverseRadarState = ReverseRadarState.WARNING;
        }
        if (objectDistance > WARNING_VALUE) {
            reverseRadarState = ReverseRadarState.OK;
        }

        reverseRadarPacket.setDistance(objectDistance);
        reverseRadarPacket.setReverseRadarState(reverseRadarState);

        LOGGER.debug("[Object distance: " + virtualFunctionBus.reverseRadarPacket.getDistance()
                + "] and [Reverse Radar State is: " + virtualFunctionBus.reverseRadarPacket.getReverseRadarState()
                + "]");
    }
}
