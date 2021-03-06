package hu.oe.nik.szfmv.automatedcar.bus.packets.parkingpilot;

public class PPCoordinatesPacket implements ReadOnlyPPCoordinatesPacket {

    private double frontX = 0;
    private double frontY = 0;
    private double backX = 0;
    private double backY = 0;
    private boolean placeIsAvailable;

    /**
     * ParkingPilotCoordinates packet constructor
     */
    //public PPCoordinatesPacket() { }

    public void setPlaceIsAvailable(boolean state) { this.placeIsAvailable = state; }

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

    @Override
    public boolean getPlaceIsAvailable() { return placeIsAvailable; }
}
