package hu.oe.nik.szfmv.environment;

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
import java.security.InvalidParameterException;
import java.util.*;

import static hu.oe.nik.szfmv.common.Utils.convertMatrixToRadians;

public class Visualizing {

    /**
     * Creates elements that can be Visualized
     *
     * @param xmlLocation location of the xml containing WorldObjects
     */
    public static List<WorldObject> build(String xmlLocation) throws ParserConfigurationException, IOException, SAXException {
        List<WorldObject> ObjectListToReturn = new ArrayList<WorldObject>();
        File inputFile = new File(xmlLocation);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);

        NodeList elements = doc.getElementsByTagName("Object");
        for (int temp = 0; temp < elements.getLength(); temp++) {
            Node element = elements.item(temp);
            try {
                ObjectListToReturn.add(readValueFromXml((Element) element));
            } catch (Exception e) {
                System.out.println("XML object parse Error: "+e.getMessage());
            }
        }
        testList(ObjectListToReturn);
        return ObjectListToReturn;

    }

    private static WorldObject readValueFromXml(Element objectElement) throws  XMLSignatureException {
        String type = objectElement.getAttribute("type");
        WorldObject wo = CreateObjectByType(type);

        //Set setImageFileName
        wo.setImageFileName(type);

        //Set position to object
        NodeList objectChildNondes = objectElement.getChildNodes();
        //for (int i = 0; i < objectChildNondes.getLength(); i++) {
        Element position;
        if (objectChildNondes.item(1).getNodeName()!="Position")
            throw new XMLSignatureException("Invalid format: Position parameter not in valid place");
        else
            position=(Element) objectChildNondes.item(1);
        try{
            wo.setX(Integer.parseInt(position.getAttribute("x")));
        wo.setY(Integer.parseInt(position.getAttribute("y")));}
        catch (NumberFormatException e){
            throw new XMLSignatureException("Invalid format: Position attributes is not Integer: " + e.getMessage());
        }

        //Set rotacion
        Element transform;
        double m11, m12, m21,m22;
        if (objectChildNondes.item(3).getNodeName()!="Transform")
            throw new XMLSignatureException("Invalid format: Transform parameter not in valid place");
        else
            transform=(Element) objectChildNondes.item(3);
        try{
        m11 = Double.parseDouble(transform.getAttribute("m11"));
        m12 = Double.parseDouble(transform.getAttribute("m12"));
        m21 = Double.parseDouble(transform.getAttribute("m21"));
        m22 = Double.parseDouble(transform.getAttribute("m22"));}
        catch (NumberFormatException e){
            throw new XMLSignatureException("Invalid format: Transform attributes is not Double: " + e.getMessage());
        }
        wo.setRotation((float) convertMatrixToRadians(m11, m12, m21, m22));
        return wo;
    }

    private static WorldObject CreateObjectByType(String type) throws XMLSignatureException {
        WorldObject wo;
        // road_something_something -> road
        if (type.indexOf('_') != -1)
            type = type.substring(0, type.indexOf('_'));
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

    private static void testList(List<WorldObject> list){
        for (WorldObject item:
             list) {
            System.out.println(item.toString());
        }
    }

}
