package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;

public class GasBrake extends SystemComponent
{
    private static final int MaxGaspedalValue = 100;
    private static final int MinGaspedalValue = 0;
    private int gaspedalvalue;
    private int brakepedalvalue;
    private final InputPacket inputPacket;

    private InputHandler inputHandler;
    public GasBrake(VirtualFunctionBus virtual)
    {
        super(virtual);
        gaspedalvalue = 0;
        inputPacket = InputPacket.getInstance();
        inputHandler = InputHandler.getInstance();
    }

    private void setGaspedalvalue(int value)
    {
        if (gaspedalvalue + value <= MaxGaspedalValue && gaspedalvalue + value >= MinGaspedalValue)
        {
            gaspedalvalue += value;
        }
    }

    private void setBrakepedalvalue(int value)
    {
        if (gaspedalvalue + value <= MaxGaspedalValue && gaspedalvalue + value >= MinGaspedalValue)
        {
            brakepedalvalue += value;
        }
    }

    @Override
    public void loop()
    {
        if (inputHandler.isGasPressed() && inputHandler.isBrakePressed())
        {
            return;
        }

        if  (!(inputHandler.isGasPressed() && inputHandler.isBrakePressed()))
        {
            setGaspedalvalue(-1);
            setBrakepedalvalue(-2);
        }

        if (inputHandler.isGasPressed())
        {
            setGaspedalvalue(1);
            setBrakepedalvalue(-1);
        }

        if (inputHandler.isBrakePressed())
        {
            setBrakepedalvalue(2);
            setGaspedalvalue(-2);
        }

        inputPacket.setGaspeadalposition(gaspedalvalue);
        inputPacket.setBrakepedalvalue(brakepedalvalue);
    }
}
