package hu.oe.nik.szfmv.detector.classes;

import com.sun.javafx.fxml.builder.TriangleMeshBuilder;
import hu.oe.nik.szfmv.detector.interfaces.ICamera;
import hu.oe.nik.szfmv.detector.interfaces.IRadarUltrasonic;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.environment.models.Collidable;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.TriangleMesh;
import sun.plugin.dom.css.Rect;

import javax.lang.model.type.IntersectionType;
import java.awt.*;
import java.awt.geom.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Responsible for Sensor Functionality
 */
public class Detector implements ICamera, IRadarUltrasonic {

    private List<WorldObject> worldObjects;

    public Detector(List<WorldObject> worldObjects) {
        this.worldObjects = worldObjects;
    }


    private Polygon CreateTriangle(Point a, Point b, Point c)
    {
        int [] XArray = new int[3];
        XArray[0]=a.x; XArray[1]=b.x; XArray[2]=c.x;
        int [] YArray = new int[3];
        YArray[0]=a.y; YArray[1]=b.y; YArray[2]=c.y;

        return  new Polygon(XArray, YArray, 3);
    }

    @Override
    public List<WorldObject> getWorldObjects(Point a, Point b, Point c) {

        Shape sensorVision=CreateTriangle(a,b,c);
        List<WorldObject> noticeableObjects = new ArrayList<WorldObject>();
        for(int x = 0; x < worldObjects.size(); x = x + 1) {
            WorldObject actualObject = worldObjects.get(x);
            if (actualObject.shape.intersects(sensorVision.getBounds())) {
                noticeableObjects.add(actualObject);
            }
        }
        return noticeableObjects;
    }

    @Override
    public List<Collidable> getCollidableObjects(Point a, Point b, Point c) {
        List<Collidable> noticeableObjects = new ArrayList<Collidable>();

        return noticeableObjects;
    }
}
