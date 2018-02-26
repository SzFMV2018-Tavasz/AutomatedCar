package hu.oe.nik.szfmv.automatedcar.bus;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.common.ConfigProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the class for the Virtual Function Bus. Components are only
 * allowed to collect sensory data exclusively using the VFB. The VFB stores the
 * input and output signals, inputs only have setters, while outputs only have
 * getters respectively.
 */
public class VirtualFunctionBus {
    private final Logger logger = LogManager.getLogger(VirtualFunctionBus.class);
    private final String VFB_DEBUD_KEY = "virtualFunctionBus.debug";

    private List<SystemComponent> components = new ArrayList<>();

    /**
     * Registers the provided {@link SystemComponent}
     *
     * @param comp a class that implements @{link ISystemComponent}
     */
    public void registerComponent(SystemComponent comp) {
        components.add(comp);
        if (ConfigProvider.provide().getBoolean(VFB_DEBUD_KEY)) {
            logger.debug("System component " + comp.toString() + " is registered on the virtual function bus");
        }
    }

    /**
     * Broadcasts the signal to all system components
     *
     * @param signal the signal to be broadcasted
     */
    public void sendSignal(Signal signal) {
        if (ConfigProvider.provide().getBoolean(VFB_DEBUD_KEY)) {
            logger.debug("Broadcast signal " + signal.toString());
        }

        for (SystemComponent comp : components)
            if(comp.isSubscribedOn(signal))
                comp.receiveSignal(signal);
    }

    /**
     * Calls cyclically the registered {@link SystemComponent}s once the virtual function bus has started.
     */
    public void loop() {
        for (SystemComponent comp : components) {
            if (ConfigProvider.provide().getBoolean(VFB_DEBUD_KEY)) {
                logger.debug("Calling cyclic function of " + comp.toString());
            }
            comp.loop();
        }
    }
}
