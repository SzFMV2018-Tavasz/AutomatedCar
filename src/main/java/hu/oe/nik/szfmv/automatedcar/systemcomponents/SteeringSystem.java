package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.detector.classes.Detector;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.environment.models.Collidable;
import hu.oe.nik.szfmv.environment.models.Road;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

/**
 * Steering system is responsible for the turning of the car.
 */
public class SteeringSystem extends SystemComponent {
    private double angularSpeed = 0;

    /**
     * Creates a steering system that connects the Virtual Function Bus
     *
     * @param virtualFunctionBus {@link VirtualFunctionBus} used to connect {@link SystemComponent}s
     */
    public SteeringSystem(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }

    @Override
    public void loop() {
    }

    public double getAngularSpeed() {
        return this.angularSpeed;
    }

    public static class RoadLaneDetector extends Detector {

        private static final double OFFSET_X = 2;

        private static final double OFFSET_Y = 1.2;

        private List<WorldObject> worldObjects;

        private List<Road> roads;

        private AutomatedCar car;

        private Shape lateralOffset;

        /**
         * Constructor LOL
         *
         * @param worldObjects Object from World
         * @param car          the automated car
         */
        public RoadLaneDetector(List<WorldObject> worldObjects, AutomatedCar car) {
            super(worldObjects);
            this.car = car;
            this.worldObjects = worldObjects;
            this.roads = new ArrayList<>();

            findRoads();
        }

        /**
         * find road from worldobject
         */
        private void findRoads() {
            for (WorldObject w : worldObjects) {
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
         * rotate && scale the detection area according to the car's rotation
         */
        private void rotate_scale_DetectionArea() {
            AffineTransform at = new AffineTransform();
            at.scale(OFFSET_X, OFFSET_Y);
            at.rotate(car.getCarValues().getRotation());
            lateralOffset = at.createTransformedShape(car.getShape());
        }

        /**
         * @return the closest object to the sensor, based on lateral offset
         */
        public Collidable getClosestCollidableObjectBasedOnLateralOffset() {

            rotate_scale_DetectionArea();

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
    }
}
