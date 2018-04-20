package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.exception.MissingPacketException;
import hu.oe.nik.szfmv.automatedcar.bus.packets.reverseradar.ReverseRadarPacket;
import hu.oe.nik.szfmv.automatedcar.input.enums.GearEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReverseRadar extends SystemComponent {

    private static final Logger LOGGER = LogManager.getLogger(ReverseRadar.class);
    private static ReverseRadarPacket reverseRadarPacket;
    private ReverseRadarState reverseRadarState;

    /**
     * @param virtualFunctionBus VirtualFunctuonBus parameter
     */
    public ReverseRadar(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);

        reverseRadarPacket = new ReverseRadarPacket();
        virtualFunctionBus.reverseRadarPacket = reverseRadarPacket;

    }

    @Override
    public void loop() throws MissingPacketException {
        if (virtualFunctionBus.inputPacket.getGearState() == GearEnum.R) {
            LOGGER.debug("Gear state is: R, now the Reverse Radar activated.");
        }
    }
}
