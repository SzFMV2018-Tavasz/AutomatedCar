package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;

public class ACC extends SystemComponent {
    private final int accSpeedMinValue = 30;
    private final int accSpeedMaxValue = 160;
    private final int accSpeedStepValue = 5;

    private double accDistanceValue;
    private int accSpeedValue;
    private final InputPacket inputPacket;

    private InputHandler inputHandler;

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
            setAccSpeedValue(+accSpeedStepValue);
        }
        if (!inputHandler.isAccSpeedIncrementPressedPressed() && inputHandler.isAccSpeedDecrementPressedPressed()) {
            setAccSpeedValue(-accSpeedStepValue);
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
        if (accSpeedValue < accSpeedMinValue) {
            accSpeedValue = accSpeedMinValue;
        } else if (accSpeedValue > accSpeedMaxValue) {
            accSpeedValue = accSpeedMaxValue;
        }
    }
}
