package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;

public class GasBrake extends SystemComponent {

    private static final int MAXGASPEDALVALUE = 100;

    private static final int MINGASPEDALVALUE = 0;

    private static final int GASSTEPVALUE = 1;

    private static final int BRAKESTEPVALUE = 2;

    private int gaspedalvalue;

    private int brakepedalvalue;

    private final InputPacket inputPacket;

    private InputHandler inputHandler;

    /**
     * class constructor
     *
     * @param virtual
     */
    public GasBrake(VirtualFunctionBus virtual) {
        super(virtual);
        gaspedalvalue = 0;
        inputPacket = InputPacket.getInstance();
        inputHandler = InputHandler.getInstance();
    }

    private void setGaspedalvalue(int value) {
        if (gaspedalvalue + value <= MAXGASPEDALVALUE && gaspedalvalue + value >= MINGASPEDALVALUE) {
            gaspedalvalue += value;
        }
    }

    private void setBrakepedalvalue(int value) {
        if (gaspedalvalue + value <= MAXGASPEDALVALUE && gaspedalvalue + value >= MINGASPEDALVALUE) {
            brakepedalvalue += value;
        }
    }

    @Override
    public void loop() {
        if (inputHandler.isGasPressed() && inputHandler.isBrakePressed()) {
            return;
        }

        if (!(inputHandler.isGasPressed() && inputHandler.isBrakePressed())) {
            setGaspedalvalue(-GASSTEPVALUE);
            setBrakepedalvalue(-BRAKESTEPVALUE);
        }

        if (inputHandler.isGasPressed()) {
            setGaspedalvalue(GASSTEPVALUE);
            setBrakepedalvalue(-BRAKESTEPVALUE);
        }

        if (inputHandler.isBrakePressed()) {
            setBrakepedalvalue(BRAKESTEPVALUE);
            setGaspedalvalue(-GASSTEPVALUE);
        }

        inputPacket.setGaspeadalposition(gaspedalvalue);
        inputPacket.setBrakepedalvalue(brakepedalvalue);
    }
}
