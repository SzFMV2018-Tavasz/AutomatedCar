package hu.oe.nik.szfmv.automatedcar.bus;

import hu.oe.nik.szfmv.automatedcar.ISystemComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the singleton class for the Virtual Function Bus. Components are only
 * allowed to collect sensory data exclusively using the VFB. The VFB stores the
 * input and output signals, inputs only have setters, while outputs only have
 * getters respectively.
 */
public class VirtualFunctionBus {

    private static final Logger logger = LogManager.getLogger(VirtualFunctionBus.class);

    private static List<ISystemComponent> components = new ArrayList<>();

    // Implement the singleton pattern
    private static VirtualFunctionBus instance = new VirtualFunctionBus();

    private VirtualFunctionBus() {
    }

    public static VirtualFunctionBus getInstance() {
        return instance;
    }

    public static void registerComponent(ISystemComponent comp) {
        VirtualFunctionBus.components.add(comp);
        logger.debug("System component " + comp.toString() + " is registered on the virtual function bus");
    }

    public static void sendSignal(Signal s) {
        logger.debug("Broadcast signal " + s.toString());

        // Broadcast the signal to all system components
        for (ISystemComponent comp : components) {
            comp.receiveSignal(s);
        }
    }

    public static void loop() {

        // Once the virtual function bus has started components are called
        // cyclically
        for (ISystemComponent comp : components) {
            logger.debug("Calling cyclic function of " + comp.toString());
            comp.loop();
        }
    }
}
