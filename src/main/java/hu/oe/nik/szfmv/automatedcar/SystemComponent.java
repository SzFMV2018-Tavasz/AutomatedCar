package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * This class represents common features for system components By extending this
 * class, the component is registered towards the virtual function bus
 * automatically on instantiation.
 */

public abstract class SystemComponent {
    final private VirtualFunctionBus virtualFunctionBus;
    final private HashMap<SignalEnum, List<Consumer<Signal>>> subscribedSignals = new HashMap<>();

    protected SystemComponent(VirtualFunctionBus virtualFunctionBus) {
        this.virtualFunctionBus = virtualFunctionBus;
        virtualFunctionBus.registerComponent(this);
    }

    protected <T> void sendSignal(SignalEnum id, T data) {
        virtualFunctionBus.sendSignal(new Signal(id, data));
    }


    public void subscribeOnSignal(SignalEnum id, Consumer<Signal> callback) {
        if(subscribedSignals.get(id) == null)
            subscribedSignals.put(id, new ArrayList<>());
        subscribedSignals.get(id).add(callback);
    }

    public void receiveSignal(Signal<Integer> s) {
    }

    public abstract void loop();

    public boolean isSubscribedOn(Signal signal) {
        return subscribedSignals.contains(signal.getId());
    }
}
