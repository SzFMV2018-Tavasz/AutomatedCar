package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;
import hu.oe.nik.szfmv.automatedcar.input.enums.GearEnum;


public class GearShift extends SystemComponent {

    private InputHandler inputHandler;

    private final InputPacket inputPacket;

    private GearEnum gearShiftsate;

    /**
     * GearShift Constructor
     *
     * @param virtualFunctionBus is the given functionbus
     */
    public GearShift(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);

        inputPacket = InputPacket.getInstance();

        gearShiftsate = GearEnum.P;
        virtualFunctionBus.inputPacket = inputPacket;
        inputHandler = InputHandler.getInstance();

    }

    @Override
    public void loop() {

        if (inputHandler.isGearShiftDownPressed() && inputHandler.isGearShiftUpPressed()) {
            return;
        }

        if (inputHandler.isGearShiftUpPressed()) {
            gearShiftsate = gearShiftUp();
        } else if (inputHandler.isGearShiftDownPressed()) {

            gearShiftsate = gearShiftDown();
        }
        inputPacket.setGearSate(gearShiftsate);


    }

    /**
     * Set
     *
     * @return the gearshift
     */
    private GearEnum gearShiftDown() {
        GearEnum e = null;
        switch (gearShiftsate) {
            case P:
                e = GearEnum.D;

            case R:
                e = GearEnum.P;

            case N:
                e = GearEnum.R;

            case D:
                e = GearEnum.N;

            default:
                break;
        }

        return e;
    }

    /**
     * Set
     *
     * @return the gearshift
     */
    private GearEnum gearShiftUp() {
        GearEnum e = null;
        switch (gearShiftsate) {
            case P:
                e = GearEnum.R;

            case R:
                e = GearEnum.N;

            case N:
                e = GearEnum.D;

            case D:
                e = GearEnum.D;
            default:
                break;
        }

        return e;
    }
}
