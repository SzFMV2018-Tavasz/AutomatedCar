package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PowertrainSystem;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SteeringSystem;
import hu.oe.nik.szfmv.environment.WorldObject;

import java.awt.*;
import java.awt.geom.Point2D;

public class AutomatedCar extends WorldObject {

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
        //TODO it is just a fake implementation
        double speed = powertrainSystem.getSpeed();
        double angularSpeed = steeringSystem.getAngularSpeed();
        angularSpeed = getSteerAngle(angularSpeed);

        // x += speed;
        // y = 0;

        // rotation += angularSpeed;

        double carHeading = 0;
        double wheelBase = 130;
        double halfWheelBase = wheelBase / 2;
        Point2D carPosition = new Point2D.Double(50,100);
        double fps = 24;

        Point2D frontWheel = new Point2D.Double((Math.cos(carHeading) * halfWheelBase) + carPosition.getX(),
                (Math.sin(carHeading) * halfWheelBase) + carPosition.getY());
        Point2D backWheel = new Point2D.Double(carPosition.getX() - (Math.cos(carHeading) * halfWheelBase),
                carPosition.getY() - (Math.sin(carHeading) * halfWheelBase));


        Point2D backWheelAfterMove = new Point2D.Double(Math.cos(carHeading) * speed * (1 / fps),
                (Math.sin(carHeading) * speed * (1 / fps)));
        Point2D frontWheelAfterMove = new Point2D.Double(Math.cos(carHeading + angularSpeed) * speed * (1 / fps),
                (Math.sin(carHeading + angularSpeed) * speed * (1 / fps)));

        frontWheel = new Point2D.Double(frontWheel.getX() + frontWheelAfterMove.getX(), frontWheel.getY() + frontWheelAfterMove.getY());
        backWheel = new Point2D.Double(backWheel.getX() + backWheelAfterMove.getX(), backWheel.getY() + backWheelAfterMove.getY());

        carPosition = new Point2D.Double((frontWheel.getX() + backWheel.getX()) / 2, (frontWheel.getY() + backWheel.getY()) / 2 );
        carHeading = Math.atan2(frontWheel.getY() - backWheel.getY(), frontWheel.getX() - backWheel.getX());

        x = (int) carPosition.getX();
        y = (int) carPosition.getY();

        rotation = (float) carHeading;
    }

    // Get [-100,100] degree and it give back a value which between -60 and 60 degree.
    public double getSteerAngle(double wheelPosition)
    {
        // From -60 to 60 degree
        double steerAngle;

        steerAngle = wheelPosition * 0.6;
        return steerAngle;
    }
}