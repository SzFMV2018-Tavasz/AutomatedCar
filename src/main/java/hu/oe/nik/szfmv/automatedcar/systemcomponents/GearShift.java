package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.Input.InputHandler;
import hu.oe.nik.szfmv.automatedcar.Input.enums.GearEnum;


public class GearShift extends  SystemComponent {

    private InputHandler inputHandler;
    private final InputPacket inputPacket;
    private GearEnum gearShiftsate;

    public GearShift(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);

        inputPacket = InputPacket.getInstance();

        gearShiftsate = GearEnum.P;
        virtualFunctionBus.inputPacket = inputPacket;
        inputHandler = InputHandler.getInstance();

    }

    @Override
    public void loop() {

        if (inputHandler.isGearShiftDownPressed() && inputHandler.isGearShiftUpPressed())
            return;

        if (inputHandler.isGearShiftUpPressed()) {
            gearShiftsate = GearShiftUp();
        } else if (inputHandler.isGearShiftDownPressed()) {

            gearShiftsate = GearShiftDown();
        }
        inputPacket.setGearSate(gearShiftsate);


    }

    private GearEnum GearShiftDown() {
        switch (gearShiftsate) {
            case P:
                return GearEnum.P;

            case R:
                return GearEnum.P;

            case N:
                return GearEnum.R;

            case D:
                return GearEnum.N;

            default:
                return  gearShiftsate;
        }

    }

    private GearEnum GearShiftUp() {
        switch (gearShiftsate) {
            case P:
                return GearEnum.R;

            case R:
                return GearEnum.N;

            case N:
                return GearEnum.D;

            case D:
                return GearEnum.D;
            default:
                return gearShiftsate;
        }



    }
}
