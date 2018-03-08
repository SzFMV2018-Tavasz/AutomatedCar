package hu.oe.nik.szfmv.environment;

import hu.oe.nik.szfmv.environment.models.ParkingSpot;
import hu.oe.nik.szfmv.environment.models.Crosswalk;
import hu.oe.nik.szfmv.environment.models.Road;
import hu.oe.nik.szfmv.environment.models.RoadSign;
import hu.oe.nik.szfmv.environment.models.Tree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static hu.oe.nik.szfmv.common.Utils.convertMatrixToRadians;

public abstract class XmlToModelConverter {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Creates List of elements that can be Visualized
     *
     * @param xmlLocation location of the xml containing WorldObjects
     * @return a list of parsed xml objects
     * @throws IOException                  if the xml location is bad
     * @throws ParserConfigurationException when the parse configuration is bad
     * @throws SAXException                 .
     * @throws IOException                  .
     */
    public static List<WorldObject> build(String xmlLocation)
            throws ParserConfigurationException, IOException, SAXException {

        List<WorldObject> objectListToReturn = new ArrayList<WorldObject>();
        File inputFile = new File(xmlLocation);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);

        NodeList xmlNodes = doc.getElementsByTagName("Object");

        for (int iterator = 0; iterator < xmlNodes.getLength(); iterator++) {
            Node nodeToAdd = xmlNodes.item(iterator);
            try {
                objectListToReturn.add(readValueFromXml((Element) nodeToAdd));
            } catch (Exception e) {

                LOGGER.error("XML object parse Error in object #" + iterator + ": " + e.getMessage());
            }
        }
        return objectListToReturn;

    }

    /**
     * @param objectElement A Xml Object that can be converted to WorldObject
     * @return WorldObject created from Xml Object
     * @throws XMLSignatureException thrown if tag missing from XmlObject
     */
    private static WorldObject readValueFromXml
    (Element objectElement) throws XMLSignatureException {

        //Find Position, Transform, type parameter in current object.
        String type = objectElement.getAttribute("type");
        Element position = null;
        Element transform = null;
        NodeList objectChildNodes = objectElement.getChildNodes();
        for (int i = 0; i < objectChildNodes.getLength(); i++) {
            switch (objectChildNodes.item(i).getNodeName()) {
                case "Position":
                    position = (Element) objectChildNodes.item(i);
                    break;
                case "Transform":
                    transform = (Element) objectChildNodes.item(i);
                    break;
                default:

            }
        }
        if (position == null || transform == null) {
            throw new XMLSignatureException("Invalid format: Not found Position or Transform in Object");
        }


        WorldObject wo = createObjectFromType(type);

        //Set setImageFileName
        wo.setImageFileName(type + ".png");

        //Set position
        try {
            wo.setX(Integer.parseInt(position.getAttribute("x")));
            wo.setY(Integer.parseInt(position.getAttribute("y")));
        } catch (NumberFormatException e) {
            throw new XMLSignatureException("Invalid format: Position attributes is not Integer: " + e.getMessage());
        }

        //Set rotation
        double m11;
        double m12;
        double m21;
        double m22;
        try {
            m11 = Double.parseDouble(transform.getAttribute("m11"));
            m12 = Double.parseDouble(transform.getAttribute("m12"));
            m21 = Double.parseDouble(transform.getAttribute("m21"));
            m22 = Double.parseDouble(transform.getAttribute("m22"));
        } catch (NumberFormatException e) {
            throw new XMLSignatureException("Invalid format: Transform attributes is not Double: " + e.getMessage());
        }
        wo.setRotation((float) convertMatrixToRadians(m11, m12, m21, m22));
        return wo;
    }

    /**
     * @param type type read from XmlObject, determines what kind the class to be created
     * @return new class, based on type
     * @throws XMLSignatureException in case type not found
     */
    private static WorldObject createObjectFromType
    (String type) throws XMLSignatureException {
        WorldObject wo;
        // road_something_something -> road
        if (type.indexOf('_') != -1) {
            type = type.substring(0, type.indexOf('_'));
        }
        switch (type) {
            case "road":
                wo = new Road();
                break;
            case "parking":
                wo = new ParkingSpot();
                break;
            case "crosswalk":
                wo = new Crosswalk();
                break;
            case "roadsign":
                wo = new RoadSign();
                break;
            case "tree":
                wo = new Tree();
                break;
            default:
                throw new XMLSignatureException("Invalid Object type: " + type);
        }
        return wo;
    }
}
