package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.car.CarPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.car.ReadOnlyCarPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.ReadOnlyInputPacket;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PowertrainSystem;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SteeringSystem;
import hu.oe.nik.szfmv.environment.WorldObject;

import java.awt.*;
import java.awt.geom.Point2D;

public class AutomatedCar extends WorldObject {

    private final double fps = 1;
    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();
    private double wheelBase;
    private double halfWidth;
    private PowertrainSystem powertrainSystem;
    private SteeringSystem steeringSystem;

    /**
     * Constructor of the AutomatedCar class
     *
     * @param x             the initial x coordinate of the car
     * @param y             the initial y coordinate of the car
     * @param imageFileName name of the image file used displaying the car on the course display
     */
    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        powertrainSystem = new PowertrainSystem(virtualFunctionBus);
        steeringSystem = new SteeringSystem(virtualFunctionBus);
        setLocation(new Point(500, 500));
        setRotation(Math.toRadians(360 - 90));
        setWidth(102);
        setHeight(208);
        wheelBase = height;
        halfWidth = width / 2;
        virtualFunctionBus.carPacket = new CarPacket(this.getX(), this.getY(), this.getRotation());

        new Driver(virtualFunctionBus);
    }

    /**
     * Provides a sample method for modifying the position of the car.
     */
    public void drive() {
        virtualFunctionBus.loop();

        calculatePositionAndOrientation();
    }

    /**
     * Calculates the new x and y coordinates of the {@link AutomatedCar} using the powertrain and the steering systems.
     */
    private void calculatePositionAndOrientation() {

        double speed = 10;
        double angularSpeed = 100;
        try {
            angularSpeed = SteeringMethods.getSteerAngle(angularSpeed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        double carHeading = Math.toRadians(270) - rotation;
        double halfWheelBase = wheelBase / 2;

        Point2D carPosition = new Point2D.Double(getCarValues().getX(), getCarValues().getY());
        Point2D frontWheel = SteeringMethods.getFrontWheel(carHeading, halfWheelBase, carPosition);
        Point2D backWheel = SteeringMethods.getBackWheel(carHeading, halfWheelBase, carPosition);

        Point2D backWheelDisplacement = SteeringMethods.getBackWheelDisplacement(carHeading, speed, fps);
        Point2D frontWheelDisplacement = SteeringMethods.getFrontWheelDisplacement(carHeading, angularSpeed, speed, fps);

        frontWheel = SteeringMethods.getNewFrontWheelPosition(frontWheel, frontWheelDisplacement);
        backWheel = SteeringMethods.getNewBackWheelPosition(backWheel, backWheelDisplacement);

        carPosition = SteeringMethods.getCarPosition(frontWheel, backWheel);
        carHeading = SteeringMethods.getCarHeading(frontWheel, backWheel);

        this.setX((int) (carPosition.getX()));
        this.setY((int) (carPosition.getY()));
        rotation = Math.toRadians(270) - carHeading;

        this.getCarValues().setX((int) (carPosition.getX()));
        this.getCarValues().setY((int) (carPosition.getY()));
        this.getCarValues().setRotation(this.getRotation());
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
    private ReadOnlyCarPacket getCarValues() {
        return virtualFunctionBus.carPacket;
    }
}