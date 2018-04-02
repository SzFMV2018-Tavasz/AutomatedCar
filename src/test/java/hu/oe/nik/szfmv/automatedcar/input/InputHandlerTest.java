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
            try{Thread.sleep(200);}catch(InterruptedException e){}
            assertThat(InputHandler.getInstance().isSteeringLeftPressed(), is(true));
        }
        catch (AWTException e){
            e.printStackTrace();
        }
    }
}