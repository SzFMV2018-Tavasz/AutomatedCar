package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.exception.MissingPacketException;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;

public class TrackingBut extends SystemComponent {
    private InputPacket ip;

    private InputHandler ih;

    private boolean pressed;

    private boolean isOn;

    public TrackingBut(VirtualFunctionBus vfb) {
        super(vfb);

        ih = InputHandler.getInstance();

        ip = InputPacket.getInstance();
    }

    @Override
    public void loop() throws MissingPacketException {
        if (ih.isTrackingPressed() && !isOn) {
            if (!pressed) {
                pressed = true;
            } else {
                pressed = false;
            }

            isOn = true;
        }

        if (!ih.isTrackingPressed()){
            isOn = false;
        }

        if (ih.isSteeringRightPressed() || ih.isSteeringLeftPressed()){
            pressed = false;
        }

        ip.setTrackingState(pressed);
    }
}
