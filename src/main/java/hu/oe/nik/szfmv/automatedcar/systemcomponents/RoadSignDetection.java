package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.exception.MissingPacketException;
import hu.oe.nik.szfmv.automatedcar.bus.packets.car.CarPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.roadsigndetection.RoadSignDetectionPacket;
import hu.oe.nik.szfmv.detector.classes.Detector;
import hu.oe.nik.szfmv.detector.classes.Triangle;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.environment.models.RoadSign;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

/**
 * Detects the closest roadsign to the car
 */
public class RoadSignDetection extends SystemComponent {

    private static final double CAMERAANGLEOFVIEW = 60; // degree
    private static final double CAMERARANGE = 80 * 50; // m * pixels/m
    private final CarPacket carPacket;
    private RoadSignDetectionPacket roadSignDetectionPacket;

    /**
     * @param virtualFunctionBus VirtualFunctionBus parameter
     */
    public RoadSignDetection(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        carPacket = virtualFunctionBus.carPacket;
    }

    /** makes a triangle representing the camera view on the car, gets the objects which are in the
     *  right half in the view of the camera, and returns the closest roadsign object to the position
     *  of the camera
     *
     * @return roadsign to show on dashboard
     */
    public RoadSign setRoadSign() {
        Point cameraPosition = new Point(0, 0);
        cameraPosition.x = carPacket.getX();
        cameraPosition.y = carPacket.getY();
        double cameraRotation = carPacket.getRotation();

        Triangle triangle = new Triangle();
        Point[] trianglePoints = triangle.trianglePoints(cameraPosition, CAMERARANGE, CAMERAANGLEOFVIEW, cameraRotation);
        trianglePoints[1] = calculateMiddlePoint(trianglePoints[1], trianglePoints[2]);

        List<WorldObject> worldObjects = new ArrayList<>();
        Detector detector = new Detector(worldObjects);
        worldObjects = detector.getWorldObjects(trianglePoints[0], trianglePoints[1], trianglePoints[2]);

        List<RoadSign> roadSigns = new ArrayList<>();
        for (int i = 0; i < worldObjects.size(); i++) {
            if (worldObjects.get(i) instanceof RoadSign) {
                roadSigns.add((RoadSign)worldObjects.get(i));
            }
        }
        return findClosestRoadSign(roadSigns, cameraPosition);
    }

    /* ASCII representation

                       /A
                      / |
                     /  |
         camera:    <---C   <== the point we need
                     \  |
                      \ |
                       \B
     */

    /** calculates the middle point between two points
     *
     * @param a one point
     * @param b another point
     * @return the point between the two points
     */
    private Point calculateMiddlePoint(Point a, Point b) {
        Point c = new Point(0, 0);
        double x = (a.getX() + b.getX()) / 2;
        double y = (a.getY() + b.getY()) / 2;
        c.setLocation(x, y);
        return c;
    }

    /** finds the closest roadsign to a specific point from a list of roadsigns
     *
     * @param rs list of roadsigns
     * @param cp a point which is the position of the camera
     * @return roadsign closest to the camera
     */
    private RoadSign findClosestRoadSign(List<RoadSign> rs, Point cp) {
        double actualDistance;
        double minDistance = Integer.MAX_VALUE;
        int pos = 0;
        for (int j = 0; j < rs.size(); j++) {
            actualDistance = cp.distance(rs.get(j).getOffsetVector());
            if (minDistance > actualDistance) {
                minDistance = actualDistance;
                pos = j;
            }
        }
        return rs.get(pos);
    }

    @Override
    public void loop() throws MissingPacketException {
        RoadSign roadSign = setRoadSign();
        roadSignDetectionPacket.setRoadSignToShowOnDashboard(roadSign);
    }
}
