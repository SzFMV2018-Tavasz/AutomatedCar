package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents common features for system components By extending this
 * class, the component is registered towards the virtual function bus
 * automatically on instantiation.
 */

public abstract class SystemComponent {
    final private VirtualFunctionBus virtualFunctionBus;
    final private List<SignalEnum> subscribedSignals = new ArrayList<>();

    protected SystemComponent(VirtualFunctionBus virtualFunctionBus) {
        this.virtualFunctionBus = virtualFunctionBus;
        virtualFunctionBus.registerComponent(this);
    }

    protected <T> void sendSignal(SignalEnum id, T data) {
        virtualFunctionBus.sendSignal(new Signal(id, data));
    }


    public void subscribeOnSignal(SignalEnum id) {
        subscribedSignals.add(id);
    }

    public void receiveSignal(Signal<Integer> s) {
    }

    public abstract void loop();

    public boolean isSubscribedOn(Signal signal) {
        return subscribedSignals.contains(signal.getId());
    }
}
