package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.car.CarPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.ReadOnlyInputPacket;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PowertrainSystem;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SteeringSystem;
import hu.oe.nik.szfmv.environment.WorldObject;

import java.awt.*;
import java.awt.geom.Point2D;

public class AutomatedCar extends WorldObject {

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

        final int carTestX = 500;
        final int carTestY = 500;
        final int fullCircle = 360;
        final int carTestRotation = 90;
        final int carWidth = 108;
        final int carHeight = 240;

        setLocation(new Point(carTestX, carTestY));
        setRotation(Math.toRadians(fullCircle - carTestRotation));
        wheelBase = carHeight;
        halfWidth = carWidth / 2;
        this.setWidth(carWidth);
        this.setHeight(carHeight);

        virtualFunctionBus.carPacket = new CarPacket(this.getX(), this.getY(), this.getRotation());
        powertrainSystem = new PowertrainSystem(virtualFunctionBus);
        steeringSystem = new SteeringSystem(virtualFunctionBus);

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

        final double testSpeed = 10;
        double angularSpeed = -100;
        final double fps = 1;
        final int threeQuarterCircle = 270;
        try {
            angularSpeed = SteeringMethods.getSteerAngle(angularSpeed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        double carHeading = Math.toRadians(threeQuarterCircle) - rotation;
        double halfWheelBase = wheelBase / 2;

        Point2D carPosition = new Point2D.Double(getCarValues().getX() + halfWidth,
                getCarValues().getY() + halfWheelBase);
        Point2D frontWheel = SteeringMethods.getFrontWheel(carHeading, halfWheelBase, carPosition);
        Point2D backWheel = SteeringMethods.getBackWheel(carHeading, halfWheelBase, carPosition);

        Point2D backWheelDisplacement = SteeringMethods.getBackWheelDisplacement(carHeading, testSpeed, fps);
        Point2D frontWheelDisplacement =
                SteeringMethods.getFrontWheelDisplacement(carHeading, angularSpeed, testSpeed, fps);

        frontWheel = SteeringMethods.getNewFrontWheelPosition(frontWheel, frontWheelDisplacement);
        backWheel = SteeringMethods.getNewBackWheelPosition(backWheel, backWheelDisplacement);

        carPosition = SteeringMethods.getCarPosition(frontWheel, backWheel);
        carHeading = SteeringMethods.getCarHeading(frontWheel, backWheel);

        this.setX((int) (carPosition.getX() - halfWidth));
        this.setY((int) (carPosition.getY() - halfWheelBase));
        rotation = Math.toRadians(threeQuarterCircle) - carHeading;

        getCarValues().setX((int) (this.getX()));
        getCarValues().setY((int) (this.getY()));
        getCarValues().setRotation(this.getRotation());
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
    private CarPacket getCarValues() {
        return virtualFunctionBus.carPacket;
    }

}