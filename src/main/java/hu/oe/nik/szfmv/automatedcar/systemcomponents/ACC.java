package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;

public class ACC extends SystemComponent {
    private static final int ACCSPEEDMINVALUE = 30;
    private static final int ACCSPEEDMAXVALUE = 160;
    private static final int ACCSPEEDSTEPVALUE = 5;

    private double accDistanceValue;
    private int accSpeedValue;
    private final InputPacket inputPacket;

    private InputHandler inputHandler;

    /**
     * ACC constructor
     *
     * @param bus
     */
    public ACC(VirtualFunctionBus bus) {
        super(bus);

        accDistanceValue = 0.8;
        accSpeedValue = 30;

        inputPacket = InputPacket.getInstance();
        inputHandler = InputHandler.getInstance();
    }

    @Override
    public void loop() {
        if (inputHandler.isAccDistancePressed()) {
            rotateDistanceValue();
        }
        if (inputHandler.isAccSpeedIncrementPressedPressed() && !inputHandler.isAccSpeedDecrementPressedPressed()) {
            setAccSpeedValue(+ACCSPEEDSTEPVALUE);
        }
        if (!inputHandler.isAccSpeedIncrementPressedPressed() && inputHandler.isAccSpeedDecrementPressedPressed()) {
            setAccSpeedValue(-ACCSPEEDSTEPVALUE);
        }

        inputPacket.setAccDistanceValue(accDistanceValue);
        inputPacket.setAccSpeedValue(accSpeedValue);

    }

    private void rotateDistanceValue() {
        if (accDistanceValue == 0.8) {
            accDistanceValue = 1;
        } else if (accDistanceValue == 1) {
            accDistanceValue = 1.2;
        } else if (accDistanceValue == 1.2) {
            accDistanceValue = 1.4;
        } else if (accDistanceValue == 1.4) {
            accDistanceValue = 0.8;
        } else {    //just to be sure
            accDistanceValue = 0.8;
        }
    }

    private void setAccSpeedValue(int diff) {
        accSpeedValue += diff;
        if (accSpeedValue < ACCSPEEDMINVALUE) {
            accSpeedValue = ACCSPEEDMINVALUE;
        } else if (accSpeedValue > ACCSPEEDMAXVALUE) {
            accSpeedValue = ACCSPEEDMAXVALUE;
        }
    }
}
