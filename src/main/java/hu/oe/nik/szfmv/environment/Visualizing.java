package hu.oe.nik.szfmv.environment;

import org.w3c.dom.Element;

import java.security.InvalidParameterException;
import java.util.List;

import static hu.oe.nik.szfmv.common.Utils.convertMatrixToRadians;

public class Visualizing {

    /**
     * Creates elements that can be Visualized
     *
     * @param xmlToBuild location of the xml containing WorldObjects
     */
    public static List<WorldObject> build(String xmlToBuild) {
        return null;
    }

    public WorldObject readValueFromXml(Element objectElement) {
        String type = objectElement.getAttribute("type");
        WorldObject wo = CreateObjectByType(type);

        //Set setImageFileName
        wo.setImageFileName(type);

        //Set position to object
        Element position = (Element) objectElement.getElementsByTagName("Position");
        wo.setX(Integer.parseInt(position.getAttribute("x")));
        wo.setX(Integer.parseInt(position.getAttribute("y")));

        //Set rotacion
        Element transform = (Element) objectElement.getElementsByTagName("Transform");
        double m11 = Double.parseDouble(transform.getAttribute("m11"));
        double m12 = Double.parseDouble(transform.getAttribute("m12"));
        double m21 = Double.parseDouble(transform.getAttribute("m21"));
        double m22 = Double.parseDouble(transform.getAttribute("m22"));
        wo.setRotation((float) convertMatrixToRadians(m11, m12, m21, m22));
        return wo;
    }

    public WorldObject CreateObjectByType(String type) {
        WorldObject wo;
        // road_something_something -> road
        if (type.indexOf('_') != -1)
            type = type.substring(0, type.indexOf('_') - 1);
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
                throw new InvalidParameterException("Invalid Object type");
        }
        return wo;
    }


}
