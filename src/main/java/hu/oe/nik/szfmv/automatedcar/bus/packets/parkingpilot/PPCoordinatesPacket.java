package hu.oe.nik.szfmv.automatedcar.bus.packets.ParkingPilot;

public class PPCoordinatesPacket implements ReadOnlyPPCoordinatesPacket {

    private boolean placeIsAvailable = false;
    private double frontX = 0;
    private double frontY = 0;
    private double backX = 0;
    private double backY = 0;

    @Override
    public boolean getPlaceIsAvailable() {
        return placeIsAvailable;
    }

    /**
     * ParkingPilotCoordinates packet constructor
     */
    //public PPCoordinatesPacket() { }
    public void setPlaceIsAvailable(boolean placeIsAvailable) {
        this.placeIsAvailable = placeIsAvailable;
    }

    @Override
    public double getFrontX() {
        return frontX;
    }

    public void setFrontX(double frontX) {
        this.frontX = frontX;
    }

    @Override
    public double getFrontY() {
        return frontY;
    }

    public void setFrontY(double frontY) {
        this.frontY = frontY;
    }

    @Override
    public double getBackX() {
        return backX;
    }

    public void setBackX(double backX) {
        this.backX = backX;
    }

    @Override
    public double getBackY() {
        return backY;
    }

    public void setBackY(double backY) {
        this.backY = backY;
    }
}
