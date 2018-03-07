package hu.oe.nik.szfmv.automatedcar.bus.packets.input;

import hu.oe.nik.szfmv.automatedcar.input.enums.GearEnum;

public class InputPacket implements ReadOnlyInputPacket {

    private double steeringWheelPosition;

    @Override
    public int getGasPedalPosition() {
        return 0;
    }

    @Override
    public int getBreakPedalPosition() {
        return 0;
    }

    @Override
    public double getSteeringWheelPosition() {
        return steeringWheelPosition;
    }

    public void setSteeringWheelPosition(double steeringWheelPosition){
        this.steeringWheelPosition = steeringWheelPosition;
    }

    @Override
    public int getACCTargetSpeed() {
        return 0;
    }

    @Override
    public double getACCTargetDistance() {
        return 0;
    }

    @Override
    public boolean getLaneKeepingStatus() {
        return false;
    }

    @Override
    public boolean getParkingPilotStatus() {
        return false;
    }

    @Override
    public GearEnum getGearState() {
        return null;
    }

    @Override
    public boolean getLeftTurnSignalStatus() {
        return false;
    }

    @Override
    public boolean getRightTurnSignalStatus() {
        return false;
    }
}
