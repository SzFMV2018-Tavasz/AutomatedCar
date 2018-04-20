package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.powertrain.PowertrainTestPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;

public class Driver extends SystemComponent {

//    private final InputPacket inputPacket;

    private InputHandler inputHandler;

    /**
     * @param virtualFunctionBus Can use to send data to other components.
     */
    public Driver(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
//        inputPacket = new InputPacket();
//        virtualFunctionBus.inputPacket = inputPacket;

        inputHandler = InputHandler.getInstance();
    }

    @Override
    public void loop() {


    }
}