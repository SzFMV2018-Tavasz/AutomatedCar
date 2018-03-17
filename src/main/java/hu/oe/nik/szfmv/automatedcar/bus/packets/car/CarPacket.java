package hu.oe.nik.szfmv.automatedcar.bus.packets.car;

import java.awt.*;

public class CarPacket implements ReadOnlyCarPacket {
    private int x;
    private int y;
    private Point rotationPoint;
    private double rotation;

    public CarPacket() {
        x = 0;
        y = 0;
        rotationPoint = new Point();
        rotation = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getRotationPoint() {
        return rotationPoint;
    }

    public double getRotation() {
        return rotation;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setRotationPoint(Point point) {
        this.rotationPoint = point;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
