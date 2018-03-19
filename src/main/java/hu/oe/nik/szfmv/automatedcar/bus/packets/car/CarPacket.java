package hu.oe.nik.szfmv.automatedcar.bus.packets.car;

public class CarPacket implements ReadOnlyCarPacket {
    private int x;
    private int y;
    private double rotation;

    public CarPacket() {
        x = 0;
        y = 0;
        rotation = 0;
    }

    public CarPacket(int x, int y, double rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
