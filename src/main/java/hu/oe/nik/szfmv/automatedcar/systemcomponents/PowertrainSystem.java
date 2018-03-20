package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

/**
 * Powertrain system is responsible for the movement of the car.
 */
public class PowertrainSystem extends SystemComponent {
    private double speed;

    /**
     * Creates a powertrain system that connects the Virtual Function Bus
     *
     * @param virtualFunctionBus {@link VirtualFunctionBus} used to connect {@link SystemComponent}s
     */
    public PowertrainSystem(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }

    @Override
    public void loop() {
        //TODO write this
    }

    public double getSpeed() {
        return this.speed;
    }
}

