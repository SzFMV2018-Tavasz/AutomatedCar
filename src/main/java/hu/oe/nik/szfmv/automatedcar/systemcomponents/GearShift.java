package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;
import hu.oe.nik.szfmv.automatedcar.input.enums.GearEnum;


public class GearShift extends SystemComponent {

    private final InputPacket inputPacket;
    private InputHandler inputHandler;
    private GearEnum gearShiftsate;

    /**
     * GearShift Constructor
     *
     * @param virtualFunctionBus is the given functionbus
     */
    public GearShift(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);

        inputPacket = InputPacket.getInstance();

        //ha nincs itt beállítva akkor null, ha a P-re tesszük, akkor félő, hogy mindig az marad
        //A kiolvasással sem jobb monjuk, bár a kezdőérték már P
//        gearShiftsate = GearEnum.P;
        gearShiftsate = inputPacket.getGearState();

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

//        System.out.println(inputPacket.getGearState());

    }

    /**
     * Set
     *
     * @return the gearshift
     */
    private GearEnum gearShiftDown() {
        GearEnum e;
        switch (gearShiftsate) {
            case P:
                e = GearEnum.D;
                break;
            case R:
                e = GearEnum.P;
                break;
            case N:
                e = GearEnum.R;
                break;
            case D:
                e = GearEnum.N;
                break;
            default:
                e = GearEnum.P;
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
        GearEnum e;
        switch (gearShiftsate) {
            case P:
                e = GearEnum.R;
                break;
            case R:
                e = GearEnum.N;
                break;
            case N:
                e = GearEnum.D;
                break;
            case D:
                e = GearEnum.D;
                break;
            default:
                e = GearEnum.P;
                break;
        }

        return e;
    }
}
