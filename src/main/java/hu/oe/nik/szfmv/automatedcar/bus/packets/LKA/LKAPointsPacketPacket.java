package hu.oe.nik.szfmv.automatedcar.bus.packets.LKA;

import java.awt.*;

public class LKAPointsPacketPacket implements IReadonlyLKAPointsPacket {
    private static LKAPointsPacketPacket instance;

    public static LKAPointsPacketPacket getInstance() {
        if (instance == null){
            instance = new LKAPointsPacketPacket();
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
