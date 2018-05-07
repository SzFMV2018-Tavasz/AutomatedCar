package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;
import hu.oe.nik.szfmv.automatedcar.input.enums.GearEnum;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.environment.models.RoadSign;

public class ACC extends SystemComponent {
    private static final double speedChangingNumber = 3.6;
    private static final int ACCSPEEDMINVALUE = (int) (30 / speedChangingNumber);
    private static final int ACCSPEEDMAXVALUE = (int) (160 / speedChangingNumber);
    private static final int ACCSPEEDSTEPVALUE = (int) (5 / speedChangingNumber);
    private static final double ACC_DISTANCE_STEP = 0.2;
    private static final double ACC_DISTANCE = 0.8;
    private static final double MAX_ACC_DISTANCE = 1.4;


    private boolean isAccOnPressed = false;
    private boolean accButPressed = false;
    private boolean plusPressed = false;
    private boolean minusPressed = false;
    private boolean accdistPressed = false;
    private double accDistanceValue;
    private int accSpeedValue;
    private final InputPacket inputPacket;
    private int limit;

    private InputHandler inputHandler;

    /**
     * ACC constructor
     *
     * @param bus is the given functionbus
     */
    public ACC(VirtualFunctionBus bus) {
        super(bus);

        accDistanceValue = 0.8;
        accSpeedValue = (int) (30 / speedChangingNumber);
        limit = ACCSPEEDMAXVALUE;

        inputPacket = InputPacket.getInstance();
        inputHandler = InputHandler.getInstance();
    }

    @Override
    public void loop() {
        if (inputPacket.getGearState() == GearEnum.D && inputHandler.isAccDistancePressed() && !accdistPressed) {
            rotateDistanceValue();
            accdistPressed = true;
        }
        if ((limit == ACCSPEEDMAXVALUE || limit <= accSpeedValue + ACCSPEEDSTEPVALUE) && inputHandler.isAccSpeedIncrementPressedPressed()
                && !inputHandler.isAccSpeedDecrementPressedPressed() && !plusPressed) {
            setAccSpeedValue(+ACCSPEEDSTEPVALUE);
            plusPressed = true;
        }
        if (!inputHandler.isAccSpeedIncrementPressedPressed() && inputHandler.isAccSpeedDecrementPressedPressed()
                && !minusPressed) {
            setAccSpeedValue(-ACCSPEEDSTEPVALUE);
            minusPressed = true;
        }
        if (isAccOnPressed && inputHandler.isBrakePressed()) {
            turnAccOff();
        }
//        if (AEB --> még nem elérhető az aeb) {
//            turnAccOff();
//        }
        if (isAccOnPressed) {
            checkTheNearsetRoadSignForSpeedChange();
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
        if (inputPacket.getGearState() == GearEnum.D && inputHandler.isAccOnPressed() && !accButPressed) {
            if (!isAccOnPressed) {
                inputPacket.setAccOn(!inputPacket.getACCOn());
                isAccOnPressed = true;
            } else {
                isAccOnPressed = false;
            }

            accButPressed = true;
        }
        if (!inputHandler.isAccOnPressed()) {
            accButPressed = false;
        }
        inputPacket.setAccOn(isAccOnPressed);
    }

    private void turnAccOff() {
        inputPacket.setAccOn(!inputPacket.getACCOn());
        isAccOnPressed = false;
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

    private RoadSign getTheNearestRoadSign() {
        return virtualFunctionBus.roadSignDetectionPacket.getRoadSignToShowOnDashboard();
    }

    private void checkTheNearsetRoadSignForSpeedChange() {
        RoadSign nearestRoadSign = getTheNearestRoadSign();
        if (nearestRoadSign != null) {
            limit = nearestRoadSign.getSpeedLimit();
            if (limit == 0) {
                accSpeedValue = 0;
            } else if (limit > 0 && accSpeedValue * speedChangingNumber > limit) {
                accSpeedValue = (int) (limit / speedChangingNumber);
            }
        }
    }
}
