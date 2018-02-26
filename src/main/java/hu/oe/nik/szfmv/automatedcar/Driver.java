package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.Signal;
import hu.oe.nik.szfmv.automatedcar.bus.SignalEnum;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

public class Driver extends SystemComponent {

    @Override
    public void loop() {
        // send demo signal
        VirtualFunctionBus.sendSignal(new Signal(SignalEnum.TESTSIGNAL, 5));
    }

    @Override
    public void receiveSignal(Signal s) {
        // TODO Auto-generated method stub
    }

}