package hu.oe.nik.szfmv.automatedcar.input;

import javax.swing.*;

import hu.oe.nik.szfmv.visualization.GuiAdapter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InputHandlerTest extends JFrame {

    private InputHandler inputHandler;

    @Before
    public void setUp() {
        setTitle("AutomatedCar");
        setLocation(0, 0); // default is 0,0 (top left corner)
        addWindowListener(new GuiAdapter());
        setPreferredSize(new Dimension(0, 0)); // inner size
        setResizable(false);
        pack();

        // Icon downloaded from: http://www.iconarchive.com/show/toolbar-2-icons-by-shlyapnikova/car-icon.html
        // and available under the licence of: https://creativecommons.org/licenses/by/4.0/
        ImageIcon carIcon = new ImageIcon(ClassLoader.getSystemResource("car-icon.png"));
        setIconImage(carIcon.getImage());

        // Not using any layout manager, but fixed coordinates
        setLayout(null);



        inputHandler = InputHandler.getInstance();
        addKeyListener(inputHandler);
        setVisible(true);
    }

    @Test
    public void firstTest(){
        try{
            Robot robot = new Robot();

            robot.keyPress(KeyEvent.VK_LEFT);

            assertThat(inputHandler.isSteeringLeftPressed(), is(true));
        }
        catch (AWTException e){
            e.printStackTrace();
        }
    }
}