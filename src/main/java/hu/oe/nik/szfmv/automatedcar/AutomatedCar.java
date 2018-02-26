package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv.environment.WorldObject;

public class AutomatedCar extends WorldObject {

    private PowertrainSystem powertrainSystem;
    private double wheelAngle = 0;
    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    /**
     * Constructor of the AutomatedCar class
     *
     * @param x the initial x coordinate of the car
     * @param y the initial y coordinate of the car
     * @param imageFileName name of the image file used displaying the car on the course display
     */
    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        powertrainSystem = new PowertrainSystem(x, y, virtualFunctionBus);

        new Driver(virtualFunctionBus);
    }

    /**
     * Provides a sample method for modifying the position of the car.
     */
    public void drive() {
        // call components
        virtualFunctionBus.loop();
        // Update the position and orientation of the car
        x = powertrainSystem.getX();
        y = powertrainSystem.getY();
        wheelAngle = (float) powertrainSystem.getWheelAngle();
    }
}