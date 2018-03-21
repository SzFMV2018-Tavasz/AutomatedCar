package hu.oe.nik.szfmv.automatedcar.bus.packets.input;

import hu.oe.nik.szfmv.automatedcar.input.enums.GearEnum;

public class InputPacket implements ReadOnlyInputPacket {

    private static InputPacket instance = null;

    private double steeringWheelPosition;

    private int gaspedalposition;

    private int brakepedalvalue;

    private GearEnum gearEnum = GearEnum.P;

    private boolean leftIndexOn;

    private boolean rightIndexOn;

    private boolean laneKeepingOn;

    private boolean parkingPilote;

    private double accDistanceValue;

    private int accSpeedValue;

    /**
     * Inpuutpacket
     *
     * @return inputpacket
     */
    public static InputPacket getInstance() {
        if (instance == null) {
            instance = new InputPacket();
        }

        return instance;
    }

    @Override
    public int getGasPedalPosition() {
        return gaspedalposition;
    }

    @Override
    public int getBreakPedalPosition() {
        return brakepedalvalue;
    }

    @Override
    public double getSteeringWheelPosition() {
        return steeringWheelPosition;
    }

    public void setSteeringWheelPosition(double steeringWheelPosition) {
        this.steeringWheelPosition = steeringWheelPosition;
    }

    public void setGaspeadalposition(int value) {
        this.gaspedalposition = value;
    }

    public void setBrakepedalvalue(int brakepedalvalue) {
        this.brakepedalvalue = brakepedalvalue;
    }

    public void setParkingPiloteStatus(boolean value) {
        parkingPilote = value;
    }

    public void setAccDistanceValue(double value) {
        accDistanceValue = value;
    }

    public void setAccSpeedValue(int value) {
        accSpeedValue = value;
    }

    @Override
    public int getACCTargetSpeed() {
        return accSpeedValue;
    }

    @Override
    public double getACCTargetDistance() {
        return accDistanceValue;
    }

    @Override
    public boolean getLaneKeepingStatus() {
        return laneKeepingOn;
    }

    public void setLaneKeepingStatus(boolean value) {
        this.laneKeepingOn = value;
    }

    @Override
    public boolean getParkingPilotStatus() {
        return parkingPilote;
    }

    @Override
    public GearEnum getGearState() {
        return gearEnum;
    }

    public void setGearSate(GearEnum gearEnum) {
        this.gearEnum = gearEnum;

    }

    @Override
    public boolean getLeftTurnSignalStatus() {
        return leftIndexOn;
    }

    public void setLeftTurnSignalStatus(boolean leftIndexOn) {
        this.leftIndexOn = leftIndexOn;
    }

    @Override
    public boolean getRightTurnSignalStatus() {
        return rightIndexOn;
    }

    public void setRightTurnSignalStatus(boolean rightIndexOn) {
        this.rightIndexOn = rightIndexOn;
    }
}
