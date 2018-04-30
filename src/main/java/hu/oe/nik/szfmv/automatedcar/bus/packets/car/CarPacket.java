package hu.oe.nik.szfmv.automatedcar.bus.packets.car;

public class CarPacket implements ReadOnlyCarPacket {
    private double x;
    private double y;
    private double rotation;

    /**
     * Car packet constructor
     */
    public CarPacket() {
        x = 0;
        y = 0;
        rotation = 0;
    }

    /***
     * Constructor for the car packet
     *
     * @param x Carposition x coordinate
     * @param y Carposition y coordinate
     * @param rotation Carposition rotation
     */
    public CarPacket(double x, double y, double rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
