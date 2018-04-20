package hu.oe.nik.szfmv.automatedcar.bus.powertrain;

import hu.oe.nik.szfmv.automatedcar.bus.powertrain.ReadOnlyPowertrainTestPacket;
import hu.oe.nik.szfmv.automatedcar.input.enums.GearEnum;

public class PowertrainTestPacket implements ReadOnlyPowertrainTestPacket {
    private int gaspedalPosition;
    private int brakepedalPosition;
    private GearEnum gearState;

    /**
     * PowertrainTestPacket constructor
     */
    public PowertrainTestPacket() {
        gaspedalPosition = 0;
        brakepedalPosition = 0;
        this.gearState = GearEnum.P;
    }

    public int getGaspedalPosition() {
        return this.gaspedalPosition;
    }

    public void setGaspedalPosition(int gaspedalPosition) {
        this.gaspedalPosition = gaspedalPosition;
    }

    @Override
    public int getBrakepedalPosition() {
        return brakepedalPosition;
    }

    public void setBrakepedalPosition(int brakepedalPosition) {
        this.brakepedalPosition = brakepedalPosition;
    }

    @Override
    public GearEnum getGearState() {
        return gearState;
    }

    public void setGearState(GearEnum gearState) {
        this.gearState = gearState;
    }
}
