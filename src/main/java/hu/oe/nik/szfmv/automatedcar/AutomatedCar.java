package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv.environment.WorldObject;

public class AutomatedCar extends WorldObject {

    private PowertrainSystem powertrainSystem;
    private double wheelAngle = 0;

    /**
     * Constructor of the AutomatedCar class
     *
     * @param x the initial x coordinate of the car
     * @param y the initial y coordinate of the car
     * @param imageFileName name of the image file used displaying the car on the course display
     */
    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        // Compose our car from brand new system components
        // The car has to know its PowertrainSystem, to get its coordinates
        powertrainSystem = new PowertrainSystem(x, y);
        // The rest of the components use the VirtualFunctionBus to communicate,
        // they do not communicate with the car itself

        // place a driver into the car for demonstrating the signal sending mechanism
        new Driver();
    }

    /**
     * Provides a sample method for modifying the position of the car.
     */
    public void drive() {
        // call components
        VirtualFunctionBus.loop();
        // Update the position and orientation of the car
        x = powertrainSystem.getX();
        y = powertrainSystem.getY();
        wheelAngle = (float) powertrainSystem.getWheelAngle();
    }
}