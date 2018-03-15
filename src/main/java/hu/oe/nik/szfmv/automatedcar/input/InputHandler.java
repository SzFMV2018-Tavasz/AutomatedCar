package hu.oe.nik.szfmv.automatedcar.input;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.ParkingPilote;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new InputHandler();
        }

        return instance;
    }

    private static InputHandler instance = null;

    private static final int steeringLeftKeyCode = KeyEvent.VK_LEFT;

    private static final int steeringRightKeyCode = KeyEvent.VK_RIGHT;

    private boolean steeringLeftPressed;

    private boolean steeringRightPressed;

    private boolean rightIndexPressed;

    private boolean leftIndexPressed;

    private boolean gaspressed;

    private boolean brakepressed;

    private boolean gearShiftUpPressed;

    private boolean gearShiftDownPressed;

    private boolean laneKeepingPressed;

    private static final int rightIndexKeyCode = KeyEvent.VK_1;

    private static final int leftIndexKeyCode = KeyEvent.VK_0;

    private static final int gasKeyCode = KeyEvent.VK_UP;

    private static final int brakeKeyCode = KeyEvent.VK_DOWN;

    private static final int gearShiftUpKeyCode = KeyEvent.VK_W;

    private static final int gearShiftDownKeyCode = KeyEvent.VK_S;

    private static final int laneKeepingKeyCode = KeyEvent.VK_L;

    private boolean ParkingPiloteOn;

    private static final int parkingpiloteKeyCode = KeyEvent.VK_P;

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

    public boolean isParkinPilotePressed()
    {
        return ParkingPiloteOn;
    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        setKeyState(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        setKeyState(e.getKeyCode(), false);
    }

    private void setKeyState(int keyCode, boolean state)
    {

        switch (keyCode){
            case (steeringLeftKeyCode):
                steeringLeftPressed = state;
                break;
            case (steeringRightKeyCode):
                steeringRightPressed = state;
                break;
            case (gasKeyCode):
                gaspressed = state;
                break;
            case (brakeKeyCode):
                brakepressed = state;
                break;
            case (gearShiftDownKeyCode):
                gearShiftDownPressed = state;
                break;
            case (gearShiftUpKeyCode):
                gearShiftUpPressed = state;
                break;
            case (laneKeepingKeyCode):
                laneKeepingPressed = state;
                break;
            case(rightIndexKeyCode):
                rightIndexPressed = state;
                break;
            case(leftIndexKeyCode):
                leftIndexPressed = state;
            case(parkingpiloteKeyCode):
                ParkingPiloteOn = state;
                break;
        }
    }
}
