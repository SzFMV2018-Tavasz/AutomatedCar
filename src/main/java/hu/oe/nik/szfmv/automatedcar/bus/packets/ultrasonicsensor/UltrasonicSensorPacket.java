package hu.oe.nik.szfmv.automatedcar.bus.packets.ultrasonicsensor;

import java.awt.Point;
import java.util.ArrayList;

public class UltrasonicSensorPacket implements ReadOnlyUltrasonicSensorPacket {

    private ArrayList<Point[]> ultrasonicSensorTriangles;

    @Override
    public ArrayList<Point[]> getUltrasonicSensorTriangles() {
        return ultrasonicSensorTriangles;
    }

    public UltrasonicSensorPacket() {
        ultrasonicSensorTriangles = new ArrayList<>();
    }
}
