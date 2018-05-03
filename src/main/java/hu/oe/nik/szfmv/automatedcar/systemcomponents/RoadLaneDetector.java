package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.exception.MissingPacketException;
import hu.oe.nik.szfmv.automatedcar.bus.packets.detector.RadarSensorPacket;
import hu.oe.nik.szfmv.common.Utils;
import hu.oe.nik.szfmv.detector.classes.Detector;
import hu.oe.nik.szfmv.detector.classes.Triangle;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.environment.models.Collidable;
import hu.oe.nik.szfmv.environment.models.Road;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class RoadLaneDetector extends SystemComponent {

    private static final Logger LOGGER = LogManager.getLogger(RoadLaneDetector.class);

    private static final double OFFSET_X = 1.2;

    private static final double OFFSET_Y = 2;

    private static final double SENSOR_RANGE = 200d;

    private static final double ANGLE_OF_VIEW = 60d;

    private List<WorldObject> worldObjects;

    private List<Road> roads;

    private AutomatedCar car;

    private Shape lateralOffset;

    private RadarSensorPacket dp;

    private Detector detector;

    /**
     * Constructor LOL
     *
     * @param car the automated car
     * @param vfb the virtualfunctionbus
     */
    public RoadLaneDetector(VirtualFunctionBus vfb, AutomatedCar car) {
        super(vfb);
        this.car = car;
        this.detector = Detector.getDetector();
        this.worldObjects = detector.getWorldObjects();
        this.roads = new ArrayList<>();

        dp = dp.getInstance();
        findRoads();
    }

    /**
     * find road from worldobject
     */
    private void findRoads() {
        if (worldObjects == null) {
            return;
        }
        for (WorldObject w : worldObjects) {
            if (w instanceof Road) {
                Road r = (Road) w;
                if (r.getShape() == null) {
                    r.generateShape();
                }

                roads.add(r);
            }
        }
    }

    /**
     * @return the points of the traingle
     */
    private Point[] trainglePoints() {
        Point startpoint = new Point();
        int rY = (car.getHeight() / 2) - 10;
        startpoint.x = (int) (car.getX() + Math.cos(-car.getRotation() + Math.PI) -
                rY * Math.sin(-car.getRotation() + Math.PI));
        startpoint.y = (int) (car.getY() + Math.sin(-car.getRotation() + Math.PI) +
                rY * Math.cos(-car.getRotation() + Math.PI));

        return Triangle.trianglePoints(startpoint, SENSOR_RANGE, ANGLE_OF_VIEW, Utils.radianToDegree(-car.getRotation()) + 180);
    }

    /**
     * @return the car is on the road or no
     */
    private boolean onRoad() {
        for (Road r : roads) {
            if (r.getShape().intersects(car.getShape().getBounds2D().getX(), car.getShape().getBounds2D().getY(),
                    car.getShape().getBounds2D().getWidth(), car.getShape().getBounds2D().getHeight())) {
                //LOGGER.debug("on the road fuck yeah");
                return true;
            }
        }

        return false;
    }

    /**
     * rotate the detection area according to the car's rotation
     */
    private void rotateDetectionArea() {
        AffineTransform at = new AffineTransform();
        at.scale(OFFSET_X, OFFSET_Y);
        at.rotate(car.getCarValues().getRotation());
        lateralOffset = at.createTransformedShape(car.getShape());
    }

    /**
     * @return the closest object to the sensor, based on lateral offset
     */
    private Collidable getClosestCollidableObjectBasedOnLateralOffset() {

        rotateDetectionArea();

        ArrayList<Collidable> objectsInDetectionArea = new ArrayList<>();

        for (WorldObject worldObject : worldObjects) {
            if (onRoad() && worldObject instanceof Collidable && worldObject.equals(car) &&
                    worldObject.getShape().intersects(lateralOffset.getBounds())) {
                objectsInDetectionArea.add((Collidable) worldObject);
            }
        }

        if (objectsInDetectionArea.size() == 0) {
            return null;
        }

        if (objectsInDetectionArea.size() == 1) {
            return objectsInDetectionArea.get(0);
        }

        int minIdx = 0;
        double minDistance = Double.MAX_VALUE;

        for (int i = 1; i < objectsInDetectionArea.size(); i++) {
            double distance = objectsInDetectionArea.get(i).getLocation()
                    .distance(objectsInDetectionArea.get(minIdx).getLocation());
            if (distance < minDistance) {
                minIdx = i;
                minDistance = distance;
            }
        }

        return objectsInDetectionArea.get(minIdx);
    }

    @Override
    public void loop() throws MissingPacketException {
        dp.setPoints(trainglePoints());
        dp.setClosestCollidableinLane(getClosestCollidableObjectBasedOnLateralOffset());
    }
}