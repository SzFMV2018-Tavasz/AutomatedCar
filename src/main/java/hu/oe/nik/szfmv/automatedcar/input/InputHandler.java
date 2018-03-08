package hu.oe.nik.szfmv.automatedcar.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    private static InputHandler instance = null;

    public static InputHandler getInstance(){
        if (instance == null){
            instance = new InputHandler();
        }

        return instance;
    }

    private static final int steeringLeftKeyCode = KeyEvent.VK_LEFT;

    private static final int steeringRightKeyCode = KeyEvent.VK_RIGHT;

    private  static  final int gearShiftUpKeyCode = KeyEvent.VK_W;

    private  static final int gearShiftDownKeyCode= KeyEvent.VK_S;


    private  boolean gearShiftUpPressed;

    private  boolean gearShiftDownPressed;

    private boolean steeringLeftPressed;

    private boolean steeringRightPressed;

    public  boolean isGearShiftUpPressed(){return  gearShiftUpPressed;}

    public  boolean isGearShiftDownPressed() {return  gearShiftDownPressed;}

    public boolean isSteeringLeftPressed(){
        return steeringLeftPressed;
    }

    public boolean isSteeringRightPressed(){
        return steeringRightPressed;
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

    private void setKeyState(int keyCode, boolean state){

        switch (keyCode){
            case (steeringLeftKeyCode):
                steeringLeftPressed = state;
                break;
            case (steeringRightKeyCode):
                steeringRightPressed = state;
                break;
            case (gearShiftDownKeyCode):
                gearShiftDownPressed = state;
                break;
            case (gearShiftUpKeyCode):
                gearShiftUpPressed = state;
                break;

        }
    }
}