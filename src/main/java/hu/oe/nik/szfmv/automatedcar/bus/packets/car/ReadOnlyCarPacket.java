package hu.oe.nik.szfmv.automatedcar.bus.packets.car;

import java.awt.*;

public interface ReadOnlyCarPacket {
    int getX();
    int getY();
    Point getRotationPoint();
    double getRotation();
    void setX(int p);
    void setY(int p);
    void setRotationPoint(Point p);
    void setRotation(double p);
}
