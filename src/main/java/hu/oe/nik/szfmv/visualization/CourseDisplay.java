package hu.oe.nik.szfmv.visualization;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.environment.XmlToModelConverter;
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
import java.util.List;
import java.util.Map;

/**
 * CourseDisplay is for providing a viewport to the virtual world where the simulation happens.
 */
public class CourseDisplay extends JPanel {

    private static Map<String, Point> referencePoints = new HashMap<>();
    private static final String wordXmlPath = "./src/main/resources/test_world.xml";
    private static final String referencePointsURI = "./src/main/resources/reference_points.xml";
    private static final Logger LOGGER = LogManager.getLogger();

    private final float scale = 0.5F;
    private final int width = 770;
    private final int height = 700;
    private final int backgroundColor = 0xEEEEEE;

    private World world;
    private BufferedImage env = null;
    private  List<WorldObject> objectListFromXml;

    /**
     * Initialize the course display
     */
    public CourseDisplay() {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBounds(0, 0, width, height);
        setBackground(new Color(backgroundColor));
        // Load course from and reference points from xml
        try {
            DrawUtils.loadReferencePoints(referencePoints, referencePointsURI);
            objectListFromXml = XmlToModelConverter.build(wordXmlPath);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Draws a WorldObject to the correct place, with the correct scaling and rotating
     * @param object the object to draw
     * @param g graphics object
     */
    private void drawWorldObject(WorldObject object, Graphics g) {
        BufferedImage image = null;
        // read file from resources
        String fileName = object.getImageFileName().endsWith(".png") ?
                object.getImageFileName() :
                object.getImageFileName() + ".png";
        try {
            image = ImageIO.read(new File(ClassLoader.getSystemResource(fileName).getFile()));
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
        at.translate(object.getX() - center.x, object.getY() - center.y);

        ((Graphics2D)g).drawImage(image, at, this);
    }


    /**
     * Draws the static course from the given xml file to an image
     *
     * @return the course on a BufferedImage
     */
    public BufferedImage drawEnvironment() {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

        for (WorldObject object : objectListFromXml) {
            // draw objects
            drawWorldObject(object, g2);
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
     * Inherited method that can paint on the JPanel.
     *
     * @param g {@link Graphics} object that can draw to the canvas
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw static elements only once
        if (env == null) {
            env = drawEnvironment();
        }
        g.drawImage(env, 0, 0, this);

        for (WorldObject object : this.world.getWorldObjects()) {
            drawWorldObject(object, g);
        }
    }
}
