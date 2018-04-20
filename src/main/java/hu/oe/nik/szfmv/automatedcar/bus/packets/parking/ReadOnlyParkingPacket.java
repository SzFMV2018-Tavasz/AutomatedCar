package hu.oe.nik.szfmv.automatedcar.bus.packets.parking;


import java.awt.*;

public interface ReadOnlyParkingPacket {
    boolean getPlaceIsAvailable();

    Point getStartPoint();

    Point getEndPoint();
}
