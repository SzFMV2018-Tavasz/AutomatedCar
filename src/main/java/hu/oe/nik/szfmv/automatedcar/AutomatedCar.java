package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.exception.MissingPacketException;
import hu.oe.nik.szfmv.automatedcar.bus.packets.car.CarPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.ReadOnlyInputPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.roadsigndetection.ReadOnlyRoadSignDetectionPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.ultrasonicsensor.ReadOnlyUltrasonicSensorPacket;
import hu.oe.nik.szfmv.automatedcar.bus.powertrain.ReadOnlyPowertrainPacket;
import hu.oe.nik.szfmv.automatedcar.sensors.UltrasonicSensor;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.*;
import hu.oe.nik.szfmv.environment.WorldObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class AutomatedCar extends WorldObject {

    private static final Logger LOGGER = LogManager.getLogger(AutomatedCar.class);
    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();
    private final List<UltrasonicSensor> ultrasonicSensors = new ArrayList<>();
    private double wheelBase;
    private double halfWheelBase;
    private double halfWidth;
    private PowertrainSystem powertrainSystem;
    private SteeringSystem steeringSystem;
    private SteeringWheel steeringWheel;
    private ReverseRadar reverseRadar;


    /**
     * Constructor of the AutomatedCar class
     *
     * @param x             the initial x coordinate of the car
     * @param y             the initial y coordinate of the car
     * @param imageFileName name of the image file used displaying the car on the course display
     */
    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        final int fullCircle = 360;
        final int carTestRotation = 90;
        final int carWidth = 108;
        final int carHeight = 240;

        setRotation(Math.toRadians(fullCircle - carTestRotation));
        wheelBase = carHeight;
        halfWidth = carWidth / 2;
        halfWheelBase = wheelBase / 2;
        this.setWidth(carWidth);
        this.setHeight(carHeight);

        generateShape();

        virtualFunctionBus.carPacket = new CarPacket(this.getX(), this.getY(), this.getRotation());
        new GasBrake(virtualFunctionBus);
        new Index(virtualFunctionBus);
        new GearShift(virtualFunctionBus);
        new SensorsVisualizer(virtualFunctionBus);
        powertrainSystem = new PowertrainSystem(virtualFunctionBus);
        new RoadLaneDetector(virtualFunctionBus, this);

        steeringSystem = new SteeringSystem(virtualFunctionBus);
        steeringWheel = new SteeringWheel(virtualFunctionBus);

        new RoadSignDetection(virtualFunctionBus);
        reverseRadar = new ReverseRadar(virtualFunctionBus);
        UltrasonicSensor.createUltrasonicSensors(this, virtualFunctionBus);

        new Driver(virtualFunctionBus);
    }

    /**
     * Provides a sample method for modifying the position of the car.
     */
    public void drive() {
        try {
            calculatePositionAndOrientation();
            virtualFunctionBus.loop();
            if (virtualFunctionBus.readOnlyPPCoordinatesPacket != null) {
                if (virtualFunctionBus.readOnlyPPCoordinatesPacket.getPlaceIsAvailable()
                        && virtualFunctionBus.inputPacket.getParkingPilotStatus()) {
                    parkingPilot();
                }
            }
            generateShape();
        } catch (MissingPacketException e) {
            LOGGER.error(e);
        }
    }

    /**
     * Calculates the new x and y coordinates of the {@link AutomatedCar} using the powertrain and the steering systems.
     */
    private void calculatePositionAndOrientation() {

        final double carSpeed = virtualFunctionBus.powertrainPacket.getSpeed();
        double angularSpeed = 0;

        try {
            angularSpeed = SteeringMethods.getSteerAngle(-this.getInputValues().getSteeringWheelPosition());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setCarPositionAndOrientation(carSpeed, angularSpeed);
    }

    /**
     * @param carSpeed     Automated car actual speed
     * @param angularSpeed Automated car actual wheel position
     */
    private void setCarPositionAndOrientation(double carSpeed, double angularSpeed) {
        final int threeQuarterCircle = 270;
        double carHeading = Math.toRadians(threeQuarterCircle) - rotation;

        Point2D carPosition = new Point2D.Double(getCarValues().getX(), getCarValues().getY());
        Object[] carPositionAndHeading = SteeringMethods.getCarPositionAndCarHead(carPosition, carHeading, carSpeed,
                angularSpeed, new int[]{width, height});
        if (carPositionAndHeading[0].getClass() == Point2D.Double.class) {
            carPosition = new Point2D.Double(((Point2D) carPositionAndHeading[0]).getX(),
                    ((Point2D) carPositionAndHeading[0]).getY());
        }

        if (carPositionAndHeading[1].getClass() == Double.class) {
            carHeading = (Double) carPositionAndHeading[1];
        }

        this.setX(carPosition.getX() - halfWidth);
        this.setY(carPosition.getY() - halfWheelBase);
        rotation = Math.toRadians(threeQuarterCircle) - carHeading;

        getCarValues().setX(this.getX());
        getCarValues().setY(this.getY());
        getCarValues().setRotation(this.getRotation());
    }

    /**
     * Automated ParkingPilot mode
     */
    public void parkingPilot() {

        double angularSpeed = 0;
        double wheelPosition = 0;
        final double startCarSpeed = 5;
        final double fullWheelPosition = 100;
        double carSpeed = -startCarSpeed;

        // These values come from packet
        double parkingPlaceHeight = virtualFunctionBus.readOnlyPPCoordinatesPacket.getBackY() -
                virtualFunctionBus.readOnlyPPCoordinatesPacket.getFrontY();
        double parkingPlaceEnd = virtualFunctionBus.readOnlyPPCoordinatesPacket.getBackY();
        double parkingPlaceStart = virtualFunctionBus.readOnlyPPCoordinatesPacket.getFrontY();
        double parkingPlaceLeftLine = virtualFunctionBus.readOnlyPPCoordinatesPacket.getFrontX();

        final double firstStateRate = 0.5;
        final double secondStateRate = 0.61;
        double firstState = parkingPlaceEnd + Math.round(parkingPlaceHeight * firstStateRate);
        double secondState = parkingPlaceEnd + Math.round(parkingPlaceHeight * secondStateRate);
        double thirdState = parkingPlaceEnd + parkingPlaceHeight;


        if (this.getCarValues().getY() < parkingPlaceEnd) {
            goBackToTheParkingPlace();
        } else {
            if (this.getCarValues().getY() < firstState) {
                wheelPosition = -fullWheelPosition;
            } else if (this.getCarValues().getY() < secondState) {
                wheelPosition = 0;
            } else if (this.getCarValues().getY() < thirdState && this.getCarValues().getY() < parkingPlaceStart) {
                wheelPosition = fullWheelPosition;
            } else {
                carSpeed = 0;
            }

            try {
                angularSpeed = SteeringMethods.getSteerAngle(wheelPosition);
            } catch (Exception e) {
                e.printStackTrace();
            }
            setCarPositionAndOrientation(carSpeed, angularSpeed);
        }

    }

    private void goBackToTheParkingPlace() {
        final int carSpeed = -5;
        setCarPositionAndOrientation(carSpeed, 0);
    }

    /**
     * Gets the input values as required by the dashboard.
     *
     * @return input packet containing the values that are displayed on the dashboard
     */
    public ReadOnlyInputPacket getInputValues() {
        return virtualFunctionBus.inputPacket;
    }

    /**
     * Gets the car values which needs to change the car position
     *
     * @return car packet
     */
    public CarPacket getCarValues() {
        return virtualFunctionBus.carPacket;
    }

    /**
     * Gets the powertrain values as required by the dashboard.
     *
     * @return powertrain packet containing the values that are displayed on the dashboard
     */
    public ReadOnlyPowertrainPacket getPowertrainValues() {
        return virtualFunctionBus.powertrainPacket;
    }


    /**
     * Gets the roadsign closest to the car
     *
     * @return roadsigndetection packet
     */
    public ReadOnlyRoadSignDetectionPacket getRoadSign() {
        return virtualFunctionBus.roadSignDetectionPacket;
    }

    /**
     * Gets the ultrasonic sensors packet
     *
     * @return ultrasonic sensors packet
     */
    public ReadOnlyUltrasonicSensorPacket getUltrasonicSensorValues() {
        return virtualFunctionBus.ultrasonicSensorPacket;

        /**
         * Gets the list of ultrasonic sensors
         * @return the list of ultrasonic sensors
         */
    }

    public List<UltrasonicSensor> getUltrasonicSensors() {
        return ultrasonicSensors;
    }
}