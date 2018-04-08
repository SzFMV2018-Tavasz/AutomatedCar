package hu.oe.nik.szfmv.detector.classes;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.environment.models.Collidable;
import hu.oe.nik.szfmv.environment.models.Road;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class RoadLaneDetector extends Detector {

    private static final double OFFSET_X = 1.2;

    private static final double OFFSET_Y = 2;

    private ArrayList<Collidable> inRoadLaneObjects;

    private List<WorldObject> worldobjects;

    private List<Road> roads;

    private AutomatedCar car;

    private Shape leteralOffset;

    /**
     * Constructor LOL
     *
     * @param worldObjects Object from World
     */
    public RoadLaneDetector(List<WorldObject> worldObjects, AutomatedCar car) {
        super(worldObjects);
        inRoadLaneObjects = new ArrayList<>();
        this.car = car;
        this.worldobjects = worldObjects;
        this.roads = new ArrayList<>();
    }

    /**
     * find road from worldobject
     */
    private void findRoads() {
        for (WorldObject w : worldobjects) {
            if (w instanceof Road) {
                Road r = (Road) w;
                r.generateShape();
                roads.add(r);
            }
        }
    }

    /**
     * @return the car is on the road or no
     */
    private boolean onRoad() {
        for (Road r : roads) {
            if (r.getShape().intersects(car.getShape().getBounds2D())) {
                return true;
            }
        }

        return false;
    }

    /**
     * scale shape for leteral offset
     */
    private void scaleCarShape() {
        AffineTransform at = new AffineTransform();
        at.scale(OFFSET_X, OFFSET_Y);
        leteralOffset = at.createTransformedShape(car.getShape());
    }


}
