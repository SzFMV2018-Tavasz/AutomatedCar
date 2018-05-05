package hu.oe.nik.szfmv.automatedcar.bus.packets.LKA;

import java.awt.*;

public class LKAPointsPacket implements ReadOnlyLKAPointsPacket {
    private static LKAPointsPacket instance;

    public static LKAPointsPacket getInstance() {
        if (instance == null) {
            instance = new LKAPointsPacket();
        }
        return instance;
    }

    Point rightPoint;
    Point leftPoint;

    @Override
    public Point getRightPoint() {
        return rightPoint;
    }

    public void setRightPoint(Point rightPoint) {
        this.rightPoint = rightPoint;
    }

    @Override
    public Point getLeftPoint() {
        return leftPoint;
    }

    public void setLeftPoint(Point leftPoint) {
        this.leftPoint = leftPoint;
    }
}
