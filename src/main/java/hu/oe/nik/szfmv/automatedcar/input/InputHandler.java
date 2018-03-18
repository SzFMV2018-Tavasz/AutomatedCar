package hu.oe.nik.szfmv.automatedcar.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    private static InputHandler instance = null;

    private static final int STEERINGLEFTKEYCODE = KeyEvent.VK_LEFT;

    private static final int STEERINGRIGHTKEYCODE = KeyEvent.VK_RIGHT;

    private static final int RIGHTINDEXKEYCODE = KeyEvent.VK_1;

    private static final int LEFTINDEXKEYCODE = KeyEvent.VK_0;

    private static final int GASKEYCODE = KeyEvent.VK_UP;

    private static final int BRAKEKEYCODE = KeyEvent.VK_DOWN;

    private static final int GEARSHIFTUPKEYCODE = KeyEvent.VK_W;

    private static final int GEARSHIFTDOWNKEYCODE = KeyEvent.VK_S;

    private static final int LANEKEEPINGKEYCODE = KeyEvent.VK_L;

    private static final int ACCDISTANCEKEYCODE = KeyEvent.VK_T;

    private static final int ACCSPEEDINCREMENTKEYCODE = KeyEvent.VK_PLUS;

    private static final int ACCSPEEDDECREMENTKEYCODE = KeyEvent.VK_MINUS;

    private static final int PARKINGPILOTEKEYCODE = KeyEvent.VK_P;

    private boolean steeringLeftPressed;

    private boolean steeringRightPressed;

    private boolean rightIndexPressed;

    private boolean leftIndexPressed;

    private boolean gaspressed;

    private boolean brakepressed;

    private boolean gearShiftUpPressed;

    private boolean gearShiftDownPressed;

    private boolean laneKeepingPressed;

    private boolean parkingPiloteOn;

    private boolean accDistancePressed;

    private boolean accSpeedIncrementPressed;

    private boolean accSpeedDecrementPressed;

    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new InputHandler();
        }

        return instance;
    }

    public boolean isRightIndexPressed() {
        return rightIndexPressed;
    }

    public boolean isLeftIndexPressed() {
        return leftIndexPressed;
    }

    public boolean isLaneKeepingPressed() {
        return laneKeepingPressed;
    }

    public boolean isGearShiftUpPressed() {
        return gearShiftUpPressed;
    }

    public boolean isGearShiftDownPressed() {
        return gearShiftDownPressed;
    }

    public boolean isSteeringLeftPressed() {

        return steeringLeftPressed;
    }

    public boolean isSteeringRightPressed() {
        return steeringRightPressed;
    }

    public boolean isGasPressed() {
        return gaspressed;
    }

    public boolean isBrakePressed() {
        return brakepressed;
    }

    public boolean isParkinPilotePressed() {
        return parkingPiloteOn;
    }

    public boolean isAccDistancePressed() {
        return accDistancePressed;
    }

    public boolean isAccSpeedIncrementPressedPressed() {
        return accSpeedIncrementPressed;
    }

    public boolean isAccSpeedDecrementPressedPressed() {
        return accSpeedDecrementPressed;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        setKeyState(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        setKeyState(e.getKeyCode(), false);
    }

    private void setKeyState(int keyCode, boolean state) {

        switch (keyCode) {
            case (STEERINGLEFTKEYCODE):
                steeringLeftPressed = state;
                break;
            case (STEERINGRIGHTKEYCODE):
                steeringRightPressed = state;
                break;
            case (GASKEYCODE):
                gaspressed = state;
                break;
            case (BRAKEKEYCODE):
                brakepressed = state;
                break;
            case (GEARSHIFTDOWNKEYCODE):
                gearShiftDownPressed = state;
                break;
            case (GEARSHIFTUPKEYCODE):
                gearShiftUpPressed = state;
                break;
            case (LANEKEEPINGKEYCODE):
                laneKeepingPressed = state;
                break;
            case (RIGHTINDEXKEYCODE):
                rightIndexPressed = state;
                break;
            case (LEFTINDEXKEYCODE):
                leftIndexPressed = state;
            case (PARKINGPILOTEKEYCODE):
                parkingPiloteOn = state;
                break;
            case (ACCDISTANCEKEYCODE):
                accDistancePressed = state;
                break;
            case (ACCSPEEDINCREMENTKEYCODE):
                accSpeedIncrementPressed = state;
                break;
            case (ACCSPEEDDECREMENTKEYCODE):
                accSpeedDecrementPressed = state;
                break;
                default:
        }
    }
}
