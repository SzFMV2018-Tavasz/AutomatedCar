package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

public class ParkingPilotFunction extends SystemComponent {

    /**
     * ParkingPilotFunction constructor
     *
     * @param virtualFunctionBus is the given functionbus
     */
    public ParkingPilotFunction(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);

    }

    @Override
    public void loop() {

    }
}
