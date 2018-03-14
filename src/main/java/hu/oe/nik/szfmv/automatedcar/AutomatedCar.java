package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.car.ReadOnlyCarPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.ReadOnlyInputPacket;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PowertrainSystem;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SteeringSystem;
import hu.oe.nik.szfmv.environment.WorldObject;

import java.awt.*;
import java.awt.geom.Point2D;

public class AutomatedCar extends WorldObject {

    private final double wheelBase = height;
    private double halfWidth = width;
    private final double fps = 1;

    private PowertrainSystem powertrainSystem;
    private SteeringSystem steeringSystem;
    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

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
        setLocation(new Point(300,300));
        setRotation(Math.toRadians(360-90));
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

        double speed = 100;
        double angularSpeed = 0;
        try {
            angularSpeed = getSteerAngle(angularSpeed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        double carHeading = Math.toRadians(270) - rotation;
        double halfWheelBase = wheelBase / 2;

        //Point2D carPosition = new Point2D.Double(this.getX()+halfWheelBase, this.getY()+halfWidth);
        Point2D carPosition = new Point2D.Double(getCarValues().getX(), getCarValues().getY());

        Point2D frontWheel = getFrontWheel(carHeading, halfWheelBase, carPosition);
        Point2D backWheel = getBackWheel(carHeading, halfWheelBase, carPosition);

        Point2D backWheelDisplacement = getBackWheelDisplacement(carHeading, speed, fps);
        Point2D frontWheelDisplacement = getFrontWheelDisplacement(carHeading, angularSpeed, speed, fps);

        frontWheel = getNewFrontWheelPosition(frontWheel, frontWheelDisplacement);
        backWheel = getNewBackWheelPosition(backWheel, backWheelDisplacement);

        carPosition = getCarPosition(frontWheel, backWheel);
        carHeading = getCarHeading(frontWheel, backWheel);

        this.setX((int) (carPosition.getX()- halfWheelBase));
        this.setY((int) (carPosition.getY()- halfWidth));
        this.getCarValues().setX((int)carPosition.getX());
        this.getCarValues().setX((int)carPosition.getY());
        this.getCarValues().setRotation(Math.toRadians(270) - carHeading);
        this.getCarValues().setRotationPoint(new Point((int)(carPosition.getX() - halfWheelBase),
                (int)(carPosition.getY() - halfWidth)));

        rotation = Math.toRadians(270)- carHeading;
    }

    /**
     * Returns the position of the car based on its two wheels by calculating the middle point between two points
     * @param frontWheel Position of the front wheel
     * @param backWheel Position of the back wheel
     * @return Position of the car based on its wheels
     */
    protected double getCarHeading(Point2D frontWheel, Point2D backWheel) {
        return Math.atan2(frontWheel.getY() - backWheel.getY(), frontWheel.getX() - backWheel.getX());
    }

    /** Get [-100,100] percent and it give back a value which between -60 and 60 degree.
     * @param       wheelPosition   in percent form.
     * @return      steeringAngle between -60 and 60 degree.
     * */
    protected double getSteerAngle (double wheelPosition) throws Exception {

        final double maxLeft = 100d;
        final double maxRight = -100d;

        if (wheelPosition > maxLeft || wheelPosition < maxRight) {
            throw new Exception();
        }

        // From -60 to 60 degree
        double steerAngle;
        final double MULTIPLIER = -0.6;


        steerAngle = wheelPosition * MULTIPLIER;
        steerAngle = Math.toRadians(steerAngle);
        return steerAngle;
    }

    /** Get front wheel position before moving
     * @param   carHeading      Car rotation
     * @param   halfWheelBase   Distance between wheels
     * @param   carPosition     Car position, x and y point
     * @return  front wheel position
    **/
    protected Point2D getFrontWheel(double carHeading, double halfWheelBase, Point2D carPosition) {
        return new Point2D.Double((Math.cos(carHeading) * halfWheelBase) + carPosition.getX(),
                (Math.sin(carHeading) * halfWheelBase) + carPosition.getY());
    }

    /** Get back wheel position before moving
     * @param   carHeading      Car rotation
     * @param   halfWheelBase   Distance between wheels
     * @param   carPosition     Car position, x and y point
     * @return  back wheel position
     **/
    protected Point2D getBackWheel(double carHeading, double halfWheelBase, Point2D carPosition) {
        return new Point2D.Double(carPosition.getX() - (Math.cos(carHeading) * halfWheelBase),
                carPosition.getY() - (Math.sin(carHeading) * halfWheelBase));
    }

    /** Get back wheel displacement after moving
     * @param   carHeading  Car rotation
     * @param   speed       Car actual speed
     * @param   fps         Display fps
     * @return  Back wheel displacement after moving
     **/
    private Point2D getBackWheelDisplacement(double carHeading, double speed, double fps) {
        return new Point2D.Double(Math.cos(carHeading) * speed * (1 / fps),
                (Math.sin(carHeading) * speed * (1 / fps)));
    }

    /** Get front wheel displacement after moving
     * @param   carHeading      Car rotation
     * @param   speed           Car actual speed
     * @param   angularSpeed    Car steering angle
     * @param   fps             Display fps
     * @return  Front wheel displacement after moving
     **/
    protected Point2D getFrontWheelDisplacement(double carHeading, double angularSpeed, double speed, double fps) {
        return new Point2D.Double(Math.cos(carHeading + angularSpeed) * speed * (1 / fps),
                (Math.sin(carHeading + angularSpeed) * speed * (1 / fps)));
    }

    /** Get new front wheel position
     * @param   frontWheel              Old front wheel position
     * @param   frontWheelDisplacement  Front wheel displacement
     * @return  New front position
     */
    private Point2D getNewFrontWheelPosition(Point2D frontWheel, Point2D frontWheelDisplacement) {
        return new Point2D.Double(frontWheel.getX() + frontWheelDisplacement.getX(),
                frontWheel.getY() + frontWheelDisplacement.getY());
    }

    /** Get new back wheel position
     * @param   backWheel              Old back wheel position
     * @param   backWheelDisplacement  Back wheel displacement
     * @return  New back position
     */
    private Point2D getNewBackWheelPosition(Point2D backWheel, Point2D backWheelDisplacement) {
        return new Point2D.Double(backWheel.getX() + backWheelDisplacement.getX(),
                backWheel.getY() + backWheelDisplacement.getY());
    }

    /** Get back new car position
     * @param   frontWheel  Front wheel position
     * @param   backWheel   Back wheel position
     * @return  Car position
     **/
    protected Point2D getCarPosition(Point2D frontWheel, Point2D backWheel) {
        return new Point2D.Double((frontWheel.getX() + backWheel.getX()) / 2,
                (frontWheel.getY() + backWheel.getY()) / 2);
    }

    /**
     * Gets the input values as required by the dashboard.
     * @return input packet containing the values that are displayed on the dashboard
     */
    public ReadOnlyInputPacket getInputValues() {
        return virtualFunctionBus.inputPacket;
    }

    /**
     * Gets the car values which needs to change the car position
     * @return car packet
     */
    private ReadOnlyCarPacket getCarValues() {
        return virtualFunctionBus.carPacket;
    }
}