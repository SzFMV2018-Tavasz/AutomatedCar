package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import lombok.Getter;

public class PowertrainSystem extends SystemComponent {
    @Getter
    private double speed;

    public PowertrainSystem(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }

    @Override
    public void loop() {
        int gasPedal = virtualFunctionBus.samplePacket.getGaspedalPosition();
        speed = gasPedal * 0.8;
        //TODO write this
    }
}

