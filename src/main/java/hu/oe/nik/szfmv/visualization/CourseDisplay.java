package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.environment.models.Movable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * CourseDisplay is for providing a viewport to the virtual world where the simulation happens.
 */
public class CourseDisplay extends JPanel {

    private static Map<String, Point> referencePoints = new HashMap<>();
    private static final String referencePointsURI = "./src/main/resources/reference_points.xml";
    private static final Logger LOGGER = LogManager.getLogger();

    private final float scale = 0.55F;
    private final int width = 770;
    private final int height = 700;
    private final int backgroundColor = 0xEEEEEE;
    private final int carWidth = 102;
    private final int carHeight = 208;
    private final int courseWidth = 5120;
    private final int courseHeight = 3000;

    private int offsetX = 0;
    private int offsetY = 0;
    private World world;
    private BufferedImage env = null;

    /**
     * Initialize the course display
     */
    public CourseDisplay() {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBounds(0, 0, width, height);
        setBackground(new Color(backgroundColor));
        // Loa reference points from xml
        try {
            DrawUtils.loadReferencePoints(referencePoints, referencePointsURI);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Draws a WorldObject to the correct place, with the correct scaling and rotating
     *
     * @param object  object to draw
     * @param g       graphics object
     * @param offsetX x offset value for course moving
     * @param offsetY y offset value for course moving
     */
    private void drawWorldObject(WorldObject object, Graphics g, int offsetX, int offsetY) {
        BufferedImage image = null;
        // read file from resources
        try {
            image = ImageIO.read(new File(ClassLoader.getSystemResource(object.getImageFileName()).getFile()));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        Point center = referencePoints.getOrDefault(object.getImageFileName(), null);
        if (center == null) {
            center = new Point(0, 0);
        }

        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        at.rotate(-object.getRotation(), object.getX(), object.getY());
        at.translate(object.getX() - center.x + offsetX, object.getY() - center.y + offsetY);

        ((Graphics2D) g).drawImage(image, at, this);
    }


    /**
     * Draws the static course from the given xml file to an image
     *
     * @return the course on a BufferedImage
     */
    public BufferedImage drawEnvironment() {
        BufferedImage img = new BufferedImage((int) (courseWidth * scale),
                (int) (courseHeight * scale), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

        for (WorldObject object : world.getWorldObjects()) {
            // draw not movable objects only once
            if (!Movable.class.isAssignableFrom(object.getClass()) &&
                    !AutomatedCar.class.isAssignableFrom(object.getClass())) {
                drawWorldObject(object, g2, 0, 0);
            }
        }
        return img;
    }

    /**
     * Draws the world to the course display
     *
     * @param world {@link World} object that describes the virtual world
     */
    public void drawWorld(World world) {
        invalidate();
        this.world = world;
        validate();
        repaint();
    }

    /**
     * Gets the offset value to move the camera
     *
     * @param scaledWidth  width of the viewport  multiplied by scaling
     * @param scaledHeight height of the viewport  multiplied by scaling
     * @param car          the car to get location
     * @return offset value to move camera with
     */
    private Point getOffset(int scaledWidth, int scaledHeight, AutomatedCar car) {
        int diffX = (scaledWidth / 2) - car.getX() - carWidth / 2;
        if (diffX < 0) {
            offsetX = diffX;
        }
        int diffY = scaledHeight / 2 - car.getY() - carHeight / 2;
        if (diffY < 0) {
            offsetY = diffY;
        }
        return new Point(offsetX, offsetY);
    }

    /**
     * Returns the car (condition: we have only 1 car) from the WorldObject list,
     *
     * @return
     */
    private AutomatedCar getCarFromWorldObjectList() {
        AutomatedCar car = null;
        for (WorldObject object : world.getWorldObjects()) {
            if (AutomatedCar.class.isAssignableFrom(object.getClass())) {
                car = (AutomatedCar) object;
            }
        }
        return car;
    }

    /**
     * Inherited method that can paint on the JPanel.
     *
     * @param g {@link Graphics} object that can draw to the canvas
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // get car because we need the position of the car, later it may well be available on the bus
        AutomatedCar car;
        car = getCarFromWorldObjectList();
        // when the car reach the half width of the viewport the course move, and the car stay on center
        int scaledWidth = (int) (width / scale);
        int scaledHeight = (int) (height / scale);
        Point offset = getOffset(scaledWidth, scaledHeight, car);
        offsetX = offset.x;
        offsetY = offset.y;
        if (env == null) {
            env = drawEnvironment();
        }
        g.drawImage(env, offsetX, offsetY, this);
        for (WorldObject object : this.world.getWorldObjects()) {
            if (Movable.class.isAssignableFrom(object.getClass()) ||
                    AutomatedCar.class.isAssignableFrom(object.getClass())) {
                drawWorldObject(object, g, offsetX, offsetY);
            }
        }
    }
}
