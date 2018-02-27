package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.Sample.SamplePacket;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;

public class Driver extends SystemComponent {

    private final SamplePacket samplePacket;

    protected Driver(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        samplePacket = new SamplePacket();
        virtualFunctionBus.samplePacket = samplePacket;
    }

    @Override
    public void loop() {
        samplePacket.setGaspedalPosition(5);
    }
}