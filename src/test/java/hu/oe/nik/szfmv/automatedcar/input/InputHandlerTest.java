package hu.oe.nik.szfmv.automatedcar.input;

import javax.swing.*;

import hu.oe.nik.szfmv.visualization.GuiAdapter;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InputHandlerTest extends JFrame {

    private final static int waitTime = 100;

    @Before
    public void setUp() {
        setResizable(false);
        pack();
        setLayout(null);
        addWindowListener(new GuiAdapter());
        addKeyListener(InputHandler.getInstance());
        setVisible(true);
    }

    @Test
    public void steeringLeftTest(){
        try{
            Robot robot = new Robot();

            robot.keyPress(KeyEvent.VK_LEFT);
            try{Thread.sleep(waitTime);}catch(InterruptedException e){}
            assertThat(InputHandler.getInstance().isSteeringLeftPressed(), is(true));
        }
        catch (AWTException e){
            e.printStackTrace();
        }
    }

    @Test
    public void steeringRightTest(){
        try{
            Robot robot = new Robot();

            robot.keyPress(KeyEvent.VK_RIGHT);
            try{Thread.sleep(waitTime);}catch(InterruptedException e){}
            assertThat(InputHandler.getInstance().isSteeringRightPressed(), is(true));
        }
        catch (AWTException e){
            e.printStackTrace();
        }
    }

    @Test
    public void gasPressedTest(){
        try{
            Robot robot = new Robot();

            robot.keyPress(KeyEvent.VK_UP);
            try{Thread.sleep(waitTime);}catch(InterruptedException e){}
            assertThat(InputHandler.getInstance().isGasPressed(), is(true));
        }
        catch (AWTException e){
            e.printStackTrace();
        }
    }

    @Test
    public void brakePressedTest(){
        try{
            Robot robot = new Robot();

            robot.keyPress(KeyEvent.VK_DOWN);
            try{Thread.sleep(waitTime);}catch(InterruptedException e){}
            assertThat(InputHandler.getInstance().isBrakePressed(), is(true));
        }
        catch (AWTException e){
            e.printStackTrace();
        }
    }
}