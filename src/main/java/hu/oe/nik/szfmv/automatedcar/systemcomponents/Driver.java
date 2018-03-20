package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

public class Driver extends SystemComponent {

    public Driver(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }

    @Override
    public void loop() {
    }
}