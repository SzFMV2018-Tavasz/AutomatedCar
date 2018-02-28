package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import lombok.Getter;

public class SteeringSystem extends SystemComponent {
    @Getter
    private double angularSpeed = 0;

    public SteeringSystem(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }

    @Override
    public void loop() {
        // TODO
    }
}
