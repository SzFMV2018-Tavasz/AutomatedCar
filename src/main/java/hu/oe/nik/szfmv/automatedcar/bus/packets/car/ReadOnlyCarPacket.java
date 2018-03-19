package hu.oe.nik.szfmv.automatedcar.bus.packets.car;

import java.awt.*;

public interface ReadOnlyCarPacket {
    int getX();
    int getY();
    double getRotation();
    void setX(int p);
    void setY(int p);
    void setRotation(double p);
}
