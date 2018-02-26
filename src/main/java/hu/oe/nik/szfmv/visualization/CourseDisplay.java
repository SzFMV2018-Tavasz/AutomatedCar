package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * CourseDisplay is for providing a viewport to the virtual world where the simulation happens.
 */
public class CourseDisplay extends JPanel {

    private static final Logger LOGGER = LogManager.getLogger();

    public CourseDisplay() {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBounds(0, 0, 770, 700);
        setBackground(new Color(0xEEEEEE));
    }

    public void drawWorld(World world) {
        paintComponent(getGraphics(), world);
    }

    protected void paintComponent(Graphics g, World world) {
        super.paintComponent(g);
        for (WorldObject object : world.getWorldObjects()) {
            // draw objects
            BufferedImage image;
            try {
                // read file from resources
                image = ImageIO.read(new File(ClassLoader.getSystemResource(object.getImageFileName()).getFile()));
                g.drawImage(image, object.getX(), object.getY(), this); // see javadoc for more info on the parameters
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    public void refreshFrame() {
        invalidate();
        validate();
        repaint();
    }
}
