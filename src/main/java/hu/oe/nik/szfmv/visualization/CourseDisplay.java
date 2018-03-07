package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.environment.XmlToModelConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.List;
import java.awt.*;

import hu.oe.nik.szfmv.visualization.DrawUtils;

/**
 * CourseDisplay is for providing a viewport to the virtual world where the simulation happens.
 */
public class CourseDisplay extends JPanel {

    private final String xmlPath = "./src/main/resources/test_world.xml";
    private final float scale = 0.5F;

    private static final Logger LOGGER = LogManager.getLogger();
    private final int width = 770;
    private final int height = 700;
    private final int backgroundColor = 0xEEEEEE;

    private List<WorldObject> objectListFromXml;

    /**
     * Initialize the course display
     */
    public CourseDisplay() {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBounds(0, 0, width, height);
        setBackground(new Color(backgroundColor));
        try {
            objectListFromXml = XmlToModelConverter.build(xmlPath);
        } catch (Exception e) {

        }
    }


    public void drawEnvironment(Graphics g) {
        for (WorldObject object : objectListFromXml) {
            // draw objects
            BufferedImage image;
            // read file from resources
            image = DrawUtils.getTransformedImage(object, scale);
            g.drawImage(image, (int)(object.getX() * scale), (int)(object.getY() * scale), this);
            // see javadoc for more info on the parameters
        }
    }

    /**
     * Draws the world to the course display
     *
     * @param world {@link World} object that describes the virtual world
     */
    public void drawWorld(World world) {
        Graphics g = getGraphics();
        super.paintComponent(g);
        paintComponent(g, world);
        drawEnvironment(g);
    }

    /**
     * Inherited method that can paint on the JPanel.
     *
     * @param g     {@link Graphics} object that can draw to the canvas
     * @param world {@link World} object that describes the virtual world
     */
    protected void paintComponent(Graphics g, World world) {
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

    /**
     * Intended to use for refreshing the course display after redrawing the world
     */
    public void refreshFrame() {
        invalidate();
        validate();
        repaint();
    }
}
