package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.ultrasonicsensor.UltrasonicSensorPacket;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.common.Utils;
import hu.oe.nik.szfmv.detector.classes.Detector;
import hu.oe.nik.szfmv.detector.classes.Triangle;
import hu.oe.nik.szfmv.environment.models.Collidable;

import java.awt.*;
import java.util.List;

public class UltrasonicSensor extends SystemComponent {

    private static final UltrasonicSensorPacket ULTRASONIC_SENSOR_PACKET = new UltrasonicSensorPacket();
    private static int CURRENT_SENSOR_INDEX;

    private static final int F_ROT = 0;
    private static final int R_ROT = 270;
    private static final int B_ROT = 180;
    private static final int L_ROT = 90;
    private static final int FBL_X = 25;
    private static final int FBR_X = -25;
    private static final int FRONT_Y = 115;
    private static final int BACK_Y = -115;
    private static final int RIGHT_X = -45;
    private static final int LEFT_X = 45;
    private static final int RLF_Y = 70;
    private static final int RLB_Y = -70;

    private final int index;
    private int halfACircle = 180;
    private int relativeX;
    private int relativeY;
    private double relativeRotation;

    private double sensorRange = Utils.convertMeterToPixel(3);
    private double angleOfView = 100;

    private AutomatedCar car;

    /**
     * Constructor for the ultrasonic sensor
     * @param virtualFunctionBus the virtual function bus used by the system components
     * @param relativeX the sensor's X coordinate relative to the car
     * @param relativeY the sensor's Y coordinate relative to the car
     * @param relativeRotation the sensor's rotation relative to the car (in degrees)
     * @param car the car the sensor belongs to
     * @param index the current sensor's index (to be used with the sent packets)
     */
    public UltrasonicSensor(VirtualFunctionBus virtualFunctionBus, int relativeX, int relativeY,
                            double relativeRotation, AutomatedCar car, int index) {
        super(virtualFunctionBus);
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.relativeRotation = relativeRotation;
        this.car = car;
        this.index = index;
        ULTRASONIC_SENSOR_PACKET.getUltrasonicSensorTriangles().add(index, null);
    }

    /**
     * Creates the ultrasonic sensors and adds them to the car's list of ultrasonic sensors.
     * @param car the car the sensors belong to
     * @param virtualFunctionBus the virtual function bus used by the system components
     */
    public static void createUltrasonicSensors(AutomatedCar car, VirtualFunctionBus virtualFunctionBus) {
        car.getUltrasonicSensors().clear();
        virtualFunctionBus.ultrasonicSensorPacket = ULTRASONIC_SENSOR_PACKET;
        CURRENT_SENSOR_INDEX = 0;
        addSensorsToCar(car, virtualFunctionBus);
    }

    /**
     * Adds the created sensors to the car.
     * @param car will receive the sensors.
     * @param virtualFunctionBus will be used by the sensors.
     */
    private static void addSensorsToCar(AutomatedCar car, VirtualFunctionBus virtualFunctionBus) {
        car.getUltrasonicSensors().add(
                new UltrasonicSensor(virtualFunctionBus, FBL_X, FRONT_Y, F_ROT, car,  CURRENT_SENSOR_INDEX));
        car.getUltrasonicSensors().add(
                new UltrasonicSensor(virtualFunctionBus, FBR_X, FRONT_Y, F_ROT, car,  ++CURRENT_SENSOR_INDEX));
        car.getUltrasonicSensors().add(
                new UltrasonicSensor(virtualFunctionBus, FBL_X, BACK_Y, B_ROT, car, ++CURRENT_SENSOR_INDEX));
        car.getUltrasonicSensors().add(
                new UltrasonicSensor(virtualFunctionBus, FBR_X, BACK_Y, B_ROT, car, ++CURRENT_SENSOR_INDEX));
        car.getUltrasonicSensors().add(
                new UltrasonicSensor(virtualFunctionBus, RIGHT_X, RLF_Y, R_ROT, car, ++CURRENT_SENSOR_INDEX));
        car.getUltrasonicSensors().add(
                new UltrasonicSensor(virtualFunctionBus, RIGHT_X, RLB_Y, R_ROT, car, ++CURRENT_SENSOR_INDEX));
        car.getUltrasonicSensors().add(
                new UltrasonicSensor(virtualFunctionBus, LEFT_X, RLF_Y, L_ROT, car, ++CURRENT_SENSOR_INDEX));
        car.getUltrasonicSensors().add(
                new UltrasonicSensor(virtualFunctionBus, LEFT_X, RLB_Y, L_ROT, car, ++CURRENT_SENSOR_INDEX));
    }

    /**
     * Gets the collidable object nearest to the sensor.
     * @return the collidable object that is closest to the sensor, null if no such object exists.
     */
    public Collidable getNearestObject() {
        Point[] triangle = getTriangleForSensor();
        Detector detector = Detector.getDetector();
        List<Collidable> collidables = detector.getCollidableObjects(triangle[0], triangle[1], triangle[2]);
        double minDistance = Double.MAX_VALUE;
        Collidable nearestObject = null;
        Point currentPosition = getPosition();
        for (Collidable collidable : collidables) {
            double distance = Utils.getDistanceBetweenTwoPoints((int)collidable.getX(), (int)collidable.getY(),
                    currentPosition.x, currentPosition.y);
            if (distance < minDistance) {
                minDistance = distance;
                nearestObject = collidable;
            }
        }

        return nearestObject;
    }

    /**
     * Gets the distance of the closest collidable object to the sensor.
     * @return the distance between the sensor and the closest collidable object, null if no such object exists.
     */
    public Double getNearestObjectDistance() {
        Collidable nearestObject = getNearestObject();
        if (nearestObject != null) {
            Point currentPosition = getPosition();
            return Utils.getDistanceBetweenTwoPoints(currentPosition.x, currentPosition.y,
                    (int)nearestObject.getX(), (int)nearestObject.getY());
        }

        return null;
    }

    /**
     * Gets the dimensions of the nearest collidable object.
     * @return an int[] array where the first element is the width and the second element is the height of the object,
     * null if no such object exists.
     */
    public int[] getNearestObjectDimensions() {
        Collidable nearestObject = getNearestObject();
        if (nearestObject != null) {
            int[] dimensions = { nearestObject.getWidth(), nearestObject.getHeight() };
            return dimensions;
        }

        return null;
    }

    /**
     * Gets the triangle for our sensor.
     * @return a Point[] array containing the 3 points of the triangle created for our sensor
     */
    private Point[] getTriangleForSensor() {
        Point sensorPosition = getPosition();
        double sensorRotation = halfACircle - getRotation();
        return Triangle.trianglePoints(sensorPosition, sensorRange, angleOfView, sensorRotation);
    }

    /**
     * Gets the sensor's X and Y coordinates.
     * @return the point containing the coordinates of the sensor
     */
    private Point getPosition() {
        double angle = Math.toRadians(halfACircle) - car.getRotation();
        int positionX = (int)(relativeX * Math.cos(angle) - relativeY * Math.sin(angle) + car.getX());
        int positionY = (int)(relativeY * Math.cos(angle) + relativeX * Math.sin(angle) + car.getY());
        return new Point(positionX, positionY);
    }

    /**
     * Gets the sensor's rotation.
     * @return the rotation of the sensor
     */
    private double getRotation() {
        return Math.toDegrees(car.getRotation()) + relativeRotation;
    }

    @Override
    public void loop() {
        ULTRASONIC_SENSOR_PACKET.getUltrasonicSensorTriangles().set(index, getTriangleForSensor());
    }
}
