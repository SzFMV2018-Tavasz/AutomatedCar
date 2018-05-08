package hu.oe.nik.szfmv.automatedcar.bus.packets.LKA;

import java.awt.*;

public interface ReadOnlyLKAPointsPacket {
    Point getLeftPoint();

    Point getRightPoint();
}
