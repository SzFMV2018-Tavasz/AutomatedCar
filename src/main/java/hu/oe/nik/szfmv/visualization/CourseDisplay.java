package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * CourseDisplay is for providing a viewport to the virtual world where the simulation happens.
 */
public class CourseDisplay extends JPanel {

    private static final Logger LOGGER = LogManager.getLogger();
    private final int width = 770;
    private final int height = 700;
    private final int backgroundColor = 0xEEEEEE;
    private final float scale = 0.5f;
    private final String referencePointsURI = "./src/main/resources/reference_points.xml";
    private static Map<String, Point> scaledReferencePoints = new HashMap<>();

    /**
     * Initialize the course display
     */
    public CourseDisplay() {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBounds(0, 0, width, height);
        setBackground(new Color(backgroundColor));
        try {
            loadReferencePoints();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Draws the world to the course display
     *
     * @param world {@link World} object that describes the virtual world
     */
    public void drawWorld(World world) {
        paintComponent(getGraphics(), world);
    }

    /**
     * Inherited method that can paint on the JPanel.
     *
     * @param g     {@link Graphics} object that can draw to the canvas
     * @param world {@link World} object that describes the virtual world
     */
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

    /**
     * Intended to use for refreshing the course display after redrawing the world
     */
    public void refreshFrame() {
        invalidate();
        validate();
        repaint();
    }

    /**
     * Loads the transformation reference points from the resource xml into the scaledReferencePoints HashMap
     *
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    private void loadReferencePoints() throws ParserConfigurationException, IOException, SAXException {
        scaledReferencePoints.clear();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(referencePointsURI);

        NodeList nodes = document.getElementsByTagName("Image");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element image = (Element) nodes.item(i);
            String imageName = image.getAttribute("name");
            Element refPoint = (Element) image.getChildNodes().item(1);

            int x = Integer.parseInt(refPoint.getAttribute("x"));
            int y = Integer.parseInt(refPoint.getAttribute("y"));
            Point p = new Point((int) (scale * x), (int) (scale * y));
            
            scaledReferencePoints.put(imageName, p);
        }
    }
}
