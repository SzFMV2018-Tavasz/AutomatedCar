package hu.oe.nik.szfmv.automatedcar.bus.packets.ParkingPilot;

public class PPCoordinatesPacket implements ReadOnlyPPCoordinatesPacket {

    private double frontX = 0;
    private double frontY = 0;
    private double backX = 0;
    private double backY = 0;

    /**
     * ParkingPilotCoordinates packet constructor
     */
    public PPCoordinatesPacket() { }

    public void setFrontX(double frontX) {
        this.frontX = frontX;
    }

    public void setFrontY(double frontY) {
        this.frontY = frontY;
    }

    public void setBackX(double backX) {
        this.backX = backX;
    }

    public void setBackY(double backY) {
        this.backY = backY;
    }

    @Override
    public double getFrontX() {
        return frontX;
    }

    @Override
    public double getFrontY() {
        return frontY;
    }

    @Override
    public double getBackX() {
        return backX;
    }

    @Override
    public double getBackY() {
        return backY;
    }
}
