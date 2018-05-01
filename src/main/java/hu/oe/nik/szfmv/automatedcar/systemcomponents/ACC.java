package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;

public class ACC extends SystemComponent {
    private static final int ACCSPEEDMINVALUE = 30;
    private static final int ACCSPEEDMAXVALUE = 160;
    private static final int ACCSPEEDSTEPVALUE = 5;
    private static final double ACC_DISTANCE_STEP = 0.2;
    private static final double ACC_DISTANCE = 0.8;
    private static final double MAX_ACC_DISTANCE = 1.6;

    private boolean isAccOnPressed = false;
    private boolean accButPressed;
    private boolean plusPressed;
    private boolean minusPressed;
    private boolean accdistPressed;
    private double accDistanceValue;
    private int accSpeedValue;
    private final InputPacket inputPacket;

    private InputHandler inputHandler;

    /**
     * ACC constructor
     *
     * @param bus is the given functionbus
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
        if (inputHandler.isAccDistancePressed() && !accdistPressed) {
            rotateDistanceValue();
            accdistPressed = true;
        }
        if (inputHandler.isAccSpeedIncrementPressedPressed() && !inputHandler.isAccSpeedDecrementPressedPressed()
                && !plusPressed) {
            setAccSpeedValue(+ACCSPEEDSTEPVALUE);
            plusPressed = true;
        }
        if (!inputHandler.isAccSpeedIncrementPressedPressed() && inputHandler.isAccSpeedDecrementPressedPressed()
                && !minusPressed) {
            setAccSpeedValue(-ACCSPEEDSTEPVALUE);
            minusPressed = true;
        }

        setAccOn();
        inputPacket.setAccDistanceValue(accDistanceValue);
        inputPacket.setAccSpeedValue(accSpeedValue);
        notPressed();
    }

    private void notPressed() {
        if (!inputHandler.isAccDistancePressed()) {
            accdistPressed = false;
        }

        if (!inputHandler.isAccSpeedIncrementPressedPressed()) {
            plusPressed = false;
        }

        if (!inputHandler.isAccSpeedDecrementPressedPressed()) {
            minusPressed = false;
        }
    }

    private void setAccOn() {
        if (inputHandler.isAccOnPressed() && !accButPressed) {
            if (!isAccOnPressed) {
                isAccOnPressed = true;
            }

            if (isAccOnPressed) {
                isAccOnPressed = false;
            }

            accButPressed = true;
        }

        if (!inputHandler.isAccOnPressed()) {
            accButPressed = false;
        }

        inputPacket.setAccOn(isAccOnPressed);
    }

    /**
     * Set the distance value
     */
    private void rotateDistanceValue() {
        accDistanceValue += ACC_DISTANCE_STEP;
        if (accDistanceValue == MAX_ACC_DISTANCE) {
            accDistanceValue = ACC_DISTANCE;
        }
    }

    /**
     * Set the acc speed
     *
     * @param diff is the given diff
     */
    private void setAccSpeedValue(int diff) {
        accSpeedValue += diff;
        if (accSpeedValue < ACCSPEEDMINVALUE) {
            accSpeedValue = ACCSPEEDMINVALUE;
        } else if (accSpeedValue > ACCSPEEDMAXVALUE) {
            accSpeedValue = ACCSPEEDMAXVALUE;
        }
    }
}
