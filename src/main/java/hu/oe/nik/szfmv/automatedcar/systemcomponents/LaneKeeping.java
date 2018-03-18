package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;

public class LaneKeeping extends SystemComponent {

    private InputHandler inputHandler;

    private InputPacket inputPacket;

    private boolean wasPressed;

    private boolean laneKeepingOn;

    public LaneKeeping(VirtualFunctionBus VirtualFunctionBus) {
        super(VirtualFunctionBus);

        inputPacket = InputPacket.getInstance();
        virtualFunctionBus.inputPacket = inputPacket;
        inputHandler = InputHandler.getInstance();

        wasPressed = false;
        laneKeepingOn = false;
    }

    @Override
    public void loop() {
        if (!wasPressed && inputHandler.isLaneKeepingPressed()) {
            wasPressed = true;
            laneKeepingOn = !laneKeepingOn;
            inputPacket.setLaneKeepingStatus(laneKeepingOn);
        } else if (wasPressed && !inputHandler.isLaneKeepingPressed()) {
            wasPressed = false;
        }
    }
}
