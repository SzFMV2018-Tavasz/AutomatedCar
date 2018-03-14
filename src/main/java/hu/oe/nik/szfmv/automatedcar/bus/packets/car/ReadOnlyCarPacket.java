package hu.oe.nik.szfmv.automatedcar.bus.packets.car;

import java.awt.*;

public interface ReadOnlyCarPacket {
    int getX();
    int getY();
    Point getRotationPoint();
    double getRotation();
    int getDisplayX();
    int getDisplayY();
    void setX(int p);
    void setY(int p);
    void setRotationPoint(Point p);
    void setRotation(double p);
    void setDisplayX(int p);
    void setDisplayY(int p);

}
