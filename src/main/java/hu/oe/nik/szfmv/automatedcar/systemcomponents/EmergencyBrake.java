package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.exception.MissingPacketException;
import hu.oe.nik.szfmv.automatedcar.bus.packets.detector.RadarSensorPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;
import hu.oe.nik.szfmv.automatedcar.input.enums.GearEnum;

public class EmergencyBrake extends SystemComponent {

    private static final int BRAKE_STEPVALUE = 20;

    private static final int DEF_BRAKE_STEP_VALUE = 4;

    private RadarSensorPacket rsp;

    private AutomatedCar car;

    private InputPacket ip;

    private InputHandler ih;

    private double speed;

    private boolean press;

    /**
     * @param virtualFunctionBus VirtualFunctuonBus parameter
     * @param car                the given car
     */
    public EmergencyBrake(VirtualFunctionBus virtualFunctionBus, AutomatedCar car) {
        super(virtualFunctionBus);
        this.car = car;
        rsp = RadarSensorPacket.getInstance();
        ip = InputPacket.getInstance();
        ih = InputHandler.getInstance();
    }

    private void brake() {
        final double GSPEED = speed * 3;
        final int DEF = 10;
        if (GSPEED > DEF && ip.getGearState() != GearEnum.R && rsp.getObjectApproachingCenterline().size() != 0) {
            GasBrake.BRAKESTEPVALUE = BRAKE_STEPVALUE;
            ih.setGaspressed(false);
            ih.setBrakepressed(true);
            press = true;
        } else {
            if (press) {
                ih.setBrakepressed(false);
                GasBrake.BRAKESTEPVALUE = DEF_BRAKE_STEP_VALUE;
                press = false;
            }
        }
    }

    @Override
    public void loop() throws MissingPacketException {
        speed = car.getPowertrainValues().getSpeed();
        brake();
    }
}
