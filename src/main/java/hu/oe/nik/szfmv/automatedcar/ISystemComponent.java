package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.Signal;

/**
 * This interface provides the necessary methods for system components. Every
 * component has to realize this interface to be able to connect to the car!
 */

public interface ISystemComponent {

    // Provide implementation for component calculation
    void loop();

    // Use this, to receive message from other components
    void receiveSignal(Signal s);
}
