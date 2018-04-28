package hu.oe.nik.szfmv.automatedcar.bus.packets.ultrasonicsensor;

import java.awt.Point;
import java.util.ArrayList;

public interface ReadOnlyUltrasonicSensorPacket {

    /**
     * Gets the list of ultrasonic sensor triangles.
     * @return a list containing the triangles for all of the ultrasonic sensors
     */
    ArrayList<Point[]> getUltrasonicSensorTriangles();
}
